package city.client;

import city.manager.InputManager;
import city.network.Request;
import city.network.Response;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private final String host;
    private final int port;

    private final Scanner scanner = new Scanner(System.in);
    private final InputManager inputManager;

    public Client(String host, int port, InputManager inputManager) {
        this.host = host;
        this.port = port;
        this.inputManager = inputManager;
    }

    public void start() {
        System.out.println("Клиент запущен");
        System.out.println("Подключение к серверу: " + host + ":" + port);
        System.out.println("Введите help для списка команд");
        while (true) {

            try {

                System.out.print("> ");

                String line = scanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                String commandName = parts[0];

                if (commandName.equals("exit")) {
                    System.out.println("Завершение клиента");
                    break;
                }

                if (commandName.equals("save")) {
                    System.out.println("Команда save недоступна на клиенте");
                    continue;
                }

                String[] args =
                        Arrays.copyOfRange(parts, 1, parts.length);

                Request request =
                        buildRequest(commandName, args);

                Response response =
                        sendRequest(request);

                System.out.println(response.getMessage());

            } catch (ConnectException e) {

                System.out.println(
                        "Сервер временно недоступен"
                );

            } catch (SocketTimeoutException e) {

                System.out.println(
                        "Сервер не отвечает"
                );

            } catch (EOFException e) {

                System.out.println(
                        "Сервер неожиданно закрыл соединение"
                );

            } catch (IOException e) {

                System.out.println("Ошибка ввода-вывода: " + e.getMessage());

            } catch (ClassNotFoundException e) {

                System.out.println("Ошибка десериализации ответа");

            } catch (Exception e) {

                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private Request buildRequest(
            String commandName,
            String[] args
    ) throws IOException {

        Object object = null;

        switch (commandName) {

            case "add":

                object = inputManager.readCity();
                break;

            case "update":

                if (args.length == 0) {
                    throw new IllegalArgumentException(
                            "Для команды update необходимо указать id"
                    );
                }

                object = inputManager.readCity();
                break;
        }

        return new Request(commandName, args, object);
    }

    private Response sendRequest(Request request)
            throws IOException, ClassNotFoundException {

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(10000);

            OutputStream outputStream =
                    socket.getOutputStream();

            InputStream inputStream =
                    socket.getInputStream();

            writeObject(outputStream, request);

            Object object =
                    readObject(inputStream);

            if (!(object instanceof Response)) {

                throw new IOException(
                        "Получен объект неверного типа: "
                                + object.getClass().getName()
                );
            }

            return (Response) object;
        }
    }

    private void writeObject(
            OutputStream outputStream,
            Object object
    ) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();

        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(
                             byteArrayOutputStream
                     )) {

            objectOutputStream.writeObject(object);
        }

        byte[] data =
                byteArrayOutputStream.toByteArray();

        DataOutputStream dataOutputStream =
                new DataOutputStream(outputStream);

        dataOutputStream.writeInt(data.length);

        dataOutputStream.write(data);

        dataOutputStream.flush();
    }

    private Object readObject(InputStream inputStream)
            throws IOException, ClassNotFoundException {

        DataInputStream dataInputStream =
                new DataInputStream(inputStream);

        int objectSize =
                dataInputStream.readInt();

        if (objectSize <= 0 || objectSize > 10_000_000) {

            throw new IOException(
                    "Некорректный размер объекта: "
                            + objectSize
            );
        }

        byte[] data =
                new byte[objectSize];

        dataInputStream.readFully(data);

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(
                             new ByteArrayInputStream(data)
                     )) {

            return objectInputStream.readObject();
        }
    }
}