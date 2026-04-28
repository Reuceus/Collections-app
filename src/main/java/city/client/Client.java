package city.client;

import city.model.*;
import city.network.Request;
import city.network.Response;
import city.manager.InputManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private City readCity(InputManager inputManager) throws Exception {
        String name = inputManager.readString("Название: ", s -> !s.trim().isEmpty(), "Ошибка");

        float x = inputManager.readFloat("X: ", p -> p < 960, "Ошибка");
        int y = inputManager.readInt("Y: ", p -> p < 333, "Ошибка");
        Coordinates c = new Coordinates(x, y);

        float area = inputManager.readFloat("Area: ", p -> p > 0, "Ошибка");
        int population = inputManager.readInt("Population: ", p -> p > 0, "Ошибка");

        double meters = inputManager.readDouble("Meters: ", p -> true, "Ошибка");
        double agglomeration = inputManager.readDouble("Agglomeration: ", p -> true, "Ошибка");

        Climate climate = Climate.valueOf(
                inputManager.readString("Climate: ", s -> true, "Ошибка")
        );

        Government government = Government.valueOf(
                inputManager.readString("Government: ", s -> true, "Ошибка")
        );

        int age = inputManager.readInt("Age: ", p -> p > 0, "Ошибка");
        int height = inputManager.readInt("Height: ", p -> p > 0, "Ошибка");

        Human governor = new Human(age, height);

        return new City(name, c, area, population, meters, agglomeration, climate, government, governor);
    }

    public void start() {
        InputManager inputManager = new InputManager();

        while (true) {
            try {
                System.out.print("> ");
                String input = inputManager.readLine();

                if (input == null || input.trim().isEmpty()) {
                    continue;
                }

                String[] parts = input.trim().split("\\s+");
                String commandName = parts[0];

                String[] args = new String[Math.max(0, parts.length - 1)];
                System.arraycopy(parts, 1, args, 0, args.length);

                Request request;

                if (commandName.equals("add") || commandName.equals("update")) {
                    City city = null;

                    while (city == null) {
                        try {
                            city = readCity(inputManager);
                        } catch (Exception e) {
                            System.out.println("Ошибка ввода, попробуйте еще раз");
                        }
                    }

                    request = new Request(commandName, args, city);
                } else {
                    request = new Request(commandName, args, null);
                }

                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress("localhost", 12345));

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(request);
                oos.flush();

                byte[] requestBytes = bos.toByteArray();
                ByteBuffer requestBuffer = ByteBuffer.allocate(4 + requestBytes.length);
                requestBuffer.putInt(requestBytes.length);
                requestBuffer.put(requestBytes);
                requestBuffer.flip();

                while (requestBuffer.hasRemaining()) {
                    channel.write(requestBuffer);
                }

                ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
                while (sizeBuffer.hasRemaining()) {
                    if (channel.read(sizeBuffer) == -1) {
                        throw new IOException("Сервер закрыл соединение");
                    }
                }
                sizeBuffer.flip();
                int responseSize = sizeBuffer.getInt();

                ByteBuffer responseBuffer = ByteBuffer.allocate(responseSize);
                while (responseBuffer.hasRemaining()) {
                    if (channel.read(responseBuffer) == -1) {
                        throw new IOException("Сервер закрыл соединение");
                    }
                }

                responseBuffer.flip();
                byte[] data = new byte[responseBuffer.remaining()];
                responseBuffer.get(data);

                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                Response response = (Response) ois.readObject();

                System.out.println(response.getMessage());

                channel.close();

            } catch (IOException e) {
                System.out.println("Ошибка сети: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

