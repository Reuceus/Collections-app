package city.client;

import city.manager.InputManager;

public class ClientMain {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 12345;

        if (args.length >= 1) {
            host = args[0];
        }

        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный порт");
            }
        }

        InputManager inputManager =
                new InputManager();

        Client client =
                new Client(host, port, inputManager);

        client.start();
    }
}