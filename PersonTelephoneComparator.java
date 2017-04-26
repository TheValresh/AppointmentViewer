import java.util.Comparator;

/**
 * Created by Ahnaf on 4/13/2017.
 */
public class PersonTelephoneComparator implements Comparator<Person> {

    public int compare(Person firstPerson, Person secondPerson) {
        int firstNumber = Integer.parseInt(firstPerson.getTelephone());
        int secondNumber = Integer.parseInt(secondPerson.getTelephone());

        return firstNumber - secondNumber;
    }
}
