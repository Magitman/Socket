package serv;

import obj.Studente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerSocket serverSocket;
    private Socket socket;
    private final int port;


    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Created ServerSocket");

            while (true) {
                this.run();
            }
        } catch (IOException e) {
            System.out.println("Exception in Class Server, unable to create serverSocket: " + e.getMessage());
        } finally {
            try {
                if (this.socket != null)
                    this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openStreams() {
        System.out.println("Connecting streams...");
        try {
            this.socket = this.serverSocket.accept();

            this.in = new ObjectInputStream(this.socket.getInputStream());
            this.out = new ObjectOutputStream(this.socket.getOutputStream());

            this.out.flush();
        } catch (IOException e) {
            System.out.println("Exception in Class Server, unable to open streams: " + e.getMessage());
        }
        System.out.println("Streams connected!");
    }

    private void closeStreams() {
        try {
            if (this.in != null)
                this.in.close();
            if (this.out != null)
                this.out.close();
        } catch (IOException e) {
            System.out.println("Exception in Class Server, unable to close the stream: " + e.getMessage());
        }
    }

    private void run() {
        try {
            this.openStreams();

            List<Studente> students;
            students = ((List<Studente>) in.readObject());

            for (Studente student : students) {
                System.out.println(student.toString());
            }

            this.updateDescriptions(students);

            System.out.println("Writing streams...");
            this.out.writeObject(students);
            this.out.flush();
            System.out.println("Streams written!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in Class Server, unable to read from the stream: " + e.getMessage());
        } finally {
            this.closeStreams();
        }
    }

    private void updateDescriptions(List<Studente> students) {
        for (Studente student : students) {
            student.setDescription("Edited with success!");
        }
    }
}
