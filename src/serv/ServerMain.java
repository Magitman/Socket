package serv;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server(8181);
        server.start();
    }
}
