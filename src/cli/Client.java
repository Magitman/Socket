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
    private final int idClient;
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
            System.out.println("Client " + this.idClient + " connecting to server...");
            this.socket = new Socket(this.address, this.port);
            System.out.println("Client " + this.idClient + " connected to server!");
            int index = 0;
            boolean go = true;

            while (index < this.MAX_ATTEMPTS && go) {
                this.openStreams();
                List<Studente> students = this.createStudents(4);
                this.sendStudents(students);

                List<Studente> editedStudents = this.receiveStudents();
                go = false;
                if (editedStudents == null) {
                    System.out.println("The list received via stream is empty");
                } else {
                    for (Studente student : editedStudents) {
                        System.out.println(student.toString());
                    }
                }

                index++;
            }

        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to set the socket right: " + e.getMessage());
        } finally {
            this.closeStreams();
        }
    }

    private void openStreams() {
        System.out.println("Client " + this.idClient + " connecting streams...");
        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.out.flush();

            this.in = new ObjectInputStream(this.socket.getInputStream());
            System.out.println("Client " + this.idClient + " streams correctly opened!");
        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to open streams: " + e.getMessage());
        }
    }

    private void closeStreams() {
        if (this.socket != null) {
            try {
                this.in.close();
                this.out.close();
                this.socket.close();
            } catch (IOException e) {
                System.out.println("Exception in class Client. Unable to close streams: " + e.getMessage());
            }
        }
    }

    private void sendStudents(List<Studente> students) {
        System.out.println("Client " + this.idClient + " writing on stream...");
        try {
            this.out.writeObject(students);
            this.out.flush();
            System.out.println("Client " + this.idClient + " streams correctly written!");
        } catch (IOException e) {
            System.out.println("Exception in class Client. Unable to write object into stream: " + e.getMessage());
        }
    }

    private List<Studente> receiveStudents() {
        System.out.println("Client " + this.idClient + " reading from stream...");
        List<Studente> editedStudents = null;

        try {
            editedStudents = (List<Studente>) this.in.readObject();
            System.out.println("Client " + this.idClient + " streams correctly read!");
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
            students.add(new Studente(names[r.nextInt(names.length)], surnames[r.nextInt(surnames.length)], "Original description"));
        }

        return students;
    }
}
