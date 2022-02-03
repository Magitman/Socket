package cli;

import obj.Studente;

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
