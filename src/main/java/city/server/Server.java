package city.server;

import city.manager.CommandManager;
import city.network.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private final int port;
    private final CommandManager commandManager;

    public Server(int port, CommandManager commandManager){
        this.port = port;
        this.commandManager = commandManager;
    }

    public void start() {
        try (ServerSocketChannel server = ServerSocketChannel.open()) {

            server.bind(new InetSocketAddress(port));
            server.configureBlocking(true);
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                try (SocketChannel client = server.accept()) {
                    ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
                    while (sizeBuffer.hasRemaining()) {
                        if (client.read(sizeBuffer) == -1) {
                            throw new IOException("Клиент закрыл соединение");
                        }
                    }
                    sizeBuffer.flip();
                    int requestSize = sizeBuffer.getInt();
                    ByteBuffer dataBuffer = ByteBuffer.allocate(requestSize);
                    while (dataBuffer.hasRemaining()) {
                        if (client.read(dataBuffer) == -1) {
                            throw new IOException("Клиент закрыл соединение");
                        }
                    }

                    dataBuffer.flip();
                    byte[] data = new byte[dataBuffer.remaining()];
                    dataBuffer.get(data);

                    ObjectInputStream in =
                            new ObjectInputStream(new ByteArrayInputStream(data));

                    Request req = (Request) in.readObject();
                    System.out.println("Получено: " + req.getCommandName());

                    String result = commandManager.execute(
                            req.getCommandName(),
                            req.getObjectArg(),
                            req.getArgs()
                    );

                    Response res = new Response(result);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(res);
                    oos.flush();

                    byte[] responseBytes = bos.toByteArray();

                    ByteBuffer responseBuffer =
                            ByteBuffer.allocate(4 + responseBytes.length);

                    responseBuffer.putInt(responseBytes.length);
                    responseBuffer.put(responseBytes);
                    responseBuffer.flip();
                    while (responseBuffer.hasRemaining()) {
                        client.write(responseBuffer);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Сервер недоступен, попробуйте позже");
        }
    }
}
