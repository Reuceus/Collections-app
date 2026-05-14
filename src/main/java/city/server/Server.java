package city.server;

import city.client.ClientMain;
import city.manager.CommandManager;
import city.network.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Server {
    private final int port;
    private final CommandManager commandManager;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int port, CommandManager commandManager){
        this.port = port;
        this.commandManager = commandManager;
    }

    private Request readRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        ClientSession session = (ClientSession) key.attachment();

        if (session.isReadingLength()) {
            int bytesRead = client.read(session.getLengthBuffer());

            if (bytesRead == -1) {
                logger.info("Клиент отключился: " + client.getRemoteAddress());
                client.close();
                key.cancel();
                return null;
            }

            if (session.getLengthBuffer().position() < 4) {
                return null;
            }

            session.getLengthBuffer().flip();
            int objectSize = session.getLengthBuffer().getInt();

            if (objectSize <= 0 || objectSize > 10000000) {
                throw new IOException("Некорректный размер объекта: " + objectSize);
            }

            session.setDataBuffer(ByteBuffer.allocate(objectSize));
            session.setReadingLength(false);
        }

        int bytesRead = client.read(session.getDataBuffer());

        if (bytesRead == -1) {
            client.close();
            key.cancel();
            return null;
        }

        if (session.getDataBuffer().position() < session.getDataBuffer().capacity()) {
            return null;
        }

        session.getDataBuffer().flip();

        byte[] data = new byte[session.getDataBuffer().capacity()];
        session.getDataBuffer().get(data);
        session.resetForNextRequest();

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Object object = ois.readObject();

            if (!(object instanceof Request)) {
                throw new IOException("Получен объект неверного типа: " + object.getClass().getName());
            }

            return (Request) object;
        }
    }

    private ByteBuffer serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(response);
        }

        byte[] data = baos.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    public void start() {
        try (ServerSocketChannel server = ServerSocketChannel.open();
             Selector selector = Selector.open();) {

            server.bind(new InetSocketAddress(port));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("Сервер запущен на порту " + port);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();

                        if (client == null) {
                            continue;
                        }
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ, new ClientSession());
                        logger.info("Новый клиент запущен");
                    }

                    if (key.isReadable()) {
                        Request request = readRequest(key);

                        if (request == null) {
                            continue;
                        }

                        logger.info("Получен запрос: " + request);
                        Response response = commandManager.execute(request);
                        ByteBuffer responseBuffer = serializeResponse(response);
                        ClientSession session = (ClientSession) key.attachment();
                        session.setResponseBuffer(responseBuffer);

                        key.interestOps(SelectionKey.OP_WRITE);
                    }

                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ClientSession session = (ClientSession) key.attachment();

                        ByteBuffer buffer = session.getResponseBuffer();
                        client.write(buffer);

                        if (buffer.hasRemaining()) {
                            continue;
                        }

                        session.setResponseBuffer(null);
                        key.interestOps(SelectionKey.OP_READ);

                        logger.info("Ответ отправлен клиенту");
                    }
                }
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Сервер недоступен, попробуйте позже", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Непредвиденная ошибка сервера", e);
        }
    }
}
