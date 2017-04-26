import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Ahnaf Mir STUDENT ID: 500570401 on 3/21/2017.
 */
public class Appointment implements Comparable<Appointment> {
    Calendar date = new GregorianCalendar();
    String description;
    Person person;


    public Appointment(Calendar date, String description, Person person) {
        this.date = date;
        this.description = description;
        this.person = person;
    }

    public Appointment(Calendar date) {
        this.date = date;
    }

    public Appointment(String description) {
        this.description = description;
    }

    public Appointment(Person person) {
        this.person = person;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String print() {
        return this.getDate() + " " + this.getDescription() + " " + this.person;
    }

    public boolean occursOn(Appointment other) {
        if (this.getDate().equals(other.getDate())) {
            return true;
        }
        else
            return false;
    }

    public int compareTo(Appointment other) {
        if (this.getDate().before(other.getDate())) {
            return -1;
        }
        else if (this.getDate().after(other.getDate())) {
            return 1;
        }
        else
            return 0;
    }


}
