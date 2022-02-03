package cli;

import java.util.ArrayList;
import java.util.List;

public class ClientMain {
    public static void main(String[] args) {
        runClients("127.0.0.1", 8181, 3);
    }

    private static void runClients(String address, int port, int numClients) {
        List<Runnable> clients = new ArrayList<>();

        for (int index = 0; index < numClients; index++) {
            Client client = new Client(index, port, address);
            clients.add(() -> client.start());
        }

        for (Runnable client : clients) {
            Thread thread = new Thread(client);
            thread.start();
        }
    }
}
