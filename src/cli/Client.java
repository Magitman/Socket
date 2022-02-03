package cli;

import obj.Studente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void start() {
        try {
            this.socket = new Socket(this.address, this.port);
            int index = 0;

            while (index < this.MAX_ATTEMPTS) {
                this.openStreams();
                List<Studente> students = this.createStudents(4);
                this.sendStudents(students);

                List<Studente> editedStudents = this.receiveStudents();
                if (editedStudents == null) {
                    System.out.println("The list received via stream is empty");
                } else {
                    for (Studente student : editedStudents) {
                        System.out.println(student.toString());
                    }
                }

                index--;
            }

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

    private void sendStudents(List<Studente> students) {
        try {
            this.out.writeObject(students);
            this.out.flush();
        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to write object into stream: " + e.getMessage());
        }
    }

    private List<Studente> receiveStudents() {
        List<Studente> editedStudents = null;

        try {
            editedStudents = (List<Studente>) this.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in class Client. Unable to read object from stream: " + e.getMessage());
        }

        return editedStudents;
    }

    private List<Studente> createStudents(int numStudents) {
        List<Studente> students = new ArrayList<Studente>();
        Random r = new Random(System.currentTimeMillis());

        String[] names = {"Pippo", "Luca", "Gabriel", "Antonio", "Andrea", "Alessia", "Maria", "Paola"};
        String[] surnames = {"Cocaina", "Broggiato", "Gaviria", "Digrankio", "Ioana", "Frittata", "Stuarda", "Puta"};

        for (int index = 0; index < numStudents; index++) {
            students.add(new Studente(names[r.nextInt()], surnames[r.nextInt()], "Original description"));
        }

        return students;
    }
}
