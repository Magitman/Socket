package obj;

import java.io.Serializable;
import java.util.Objects;

public class Studente implements Serializable {
    private String name, surname, description;

    public Studente(String name, String surname, String description) {
        this.name = name;
        this.surname = surname;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studente studente = (Studente) o;
        return name.equals(studente.name) && surname.equals(studente.surname) && Objects.equals(description, studente.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, description);
    }

    @Override
    public String toString() {
        return "Studente:\n" +
                "\tNome\t\"" + this.name + "\"\n" +
                "\tCognome\t\"" + this.surname + "\"\n" +
                "\tDescrizione\t\"" + this.description + "\"";
    }
}
