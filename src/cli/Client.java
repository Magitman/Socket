package cli;

import obj.Studente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int idClient;
    private final int MAX_ATTEMPTS;
    private final int port;
    private final String address;

    public Client(int idClient, int port, String address) {
        this.idClient = idClient;
        this.port = port;
        this.address = address;
        this.MAX_ATTEMPTS = 8;
    }

    private void start() {
        try {
            this.socket = new Socket(this.address, this.port);
        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to set the socket right: " + e.getMessage());
        }
    }

    private void openStreams() {
        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
            this.out = new ObjectOutputStream(this.socket.getOutputStream());

            this.out.flush();
        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to open streams: " + e.getMessage());
        }
    }

    private List<Studente> createStudents() {
        List<Studente> students = new ArrayList<Studente>();

        String[] names = {"Pippo", "Luca", "Gabriel", "Antonio", "Andrea", "Alessia", "Maria"};
        String[] surnames = {"Cocaina", "Broggiato", "Gaviria", "Digrankio", "Ioana", "Frittata", "Stuarda"};

        for (int index = 0; index < names.length; index++) {
            students.add(new Studente(names[index], surnames[index], "Original description"));
        }

        return students;
    }
}
