import java.util.Comparator;

/**
 * Created by Ahnaf on 4/13/2017.
 */
public class PersonEmailComparator implements Comparator<Person> {
    public int compare(Person firstPerson, Person secondPerson) {
        return firstPerson.getEmail().compareTo(secondPerson.getEmail());
    }

}
