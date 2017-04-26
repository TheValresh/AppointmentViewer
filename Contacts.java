import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Created by Ahnaf Mir on 4/13/2017.
 */
public class Contacts {

    LinkedList<Person> listOfContacts = new LinkedList<Person>();

    public Person FindPerson(String lastName, String firstName, String telephone, String address, String email) {
        ListIterator<Person> listIterator = listOfContacts.listIterator();
        Person compareMe;
        int lastNameCheck;
        int firstNameCheck;
        int telephoneCheck;
        int addressCheck;
        int emailCheck;

        while (listIterator.hasNext()) {
            compareMe = listIterator.next();
            lastNameCheck = lastName.compareTo(compareMe.getLastName());
            firstNameCheck = firstName.compareTo(compareMe.getFirstName());
            telephoneCheck = telephone.compareToIgnoreCase(compareMe.getTelephone());
            addressCheck = address.compareTo(compareMe.getAddress());
            emailCheck = email.compareTo(compareMe.getEmail());

            if (lastName.isEmpty()) {
                if ((firstNameCheck == 0) && (telephoneCheck == 0) && (addressCheck == 0) && (emailCheck == 0))
                    return compareMe;
            }

            if (firstName.isEmpty()) {
                if ((lastNameCheck == 0) && (telephoneCheck == 0) && (addressCheck == 0) && (emailCheck == 0))
                    return compareMe;
            }

            if (telephone.isEmpty()) {
                if ((lastNameCheck == 0) && (firstNameCheck == 0) && (addressCheck == 0) && (emailCheck == 0))
                    return compareMe;
            }

            if (address.isEmpty()) {
                if ((lastNameCheck == 0) && (firstNameCheck == 0) && (telephoneCheck == 0) && (emailCheck == 0))
                    return compareMe;
            }

            if (email.isEmpty()) {
                if ((lastNameCheck == 0) && (firstNameCheck == 0) && (telephoneCheck == 0) && (addressCheck == 0))
                    return compareMe;
            }

            if ((lastNameCheck == 0) && (firstNameCheck == 0) && (telephoneCheck == 0) && (addressCheck == 0) && (emailCheck == 0))
                return compareMe;
        }

        return null;
    }

    public Person FindPersonWithNumber(String number) {
        ListIterator<Person> listIterator = listOfContacts.listIterator();
        Person compareMe;
        int numberCheck;

        while (listIterator.hasNext()) {
            compareMe = listIterator.next();
            numberCheck = number.compareToIgnoreCase(compareMe.getTelephone());

            if (numberCheck == 0)
                return compareMe;
        }

        return null;
    }

    public Person FindPersonWithName(String lastName, String firstName) {
        ListIterator<Person> listIterator = listOfContacts.listIterator();
        Person compareMe;
        int lastNameCheck;
        int firstNameCheck;

        while (listIterator.hasNext()) {
            compareMe = listIterator.next();
            lastNameCheck = lastName.compareToIgnoreCase(compareMe.getLastName());
            firstNameCheck = firstName.compareToIgnoreCase(compareMe.getFirstName());

            if (lastName.isEmpty()) {
                if (firstNameCheck == 0)
                    return compareMe;
            }

            if (firstName.isEmpty()) {
                if (lastNameCheck == 0)
                    return compareMe;
            }

            if ((lastNameCheck == 0) && (firstNameCheck == 0))
                return compareMe;
        }

        return null;
    }

    public Person FindPersonWithEmail(String emailGiven) {
        ListIterator<Person> listIterator = listOfContacts.listIterator();
        Person compareMe;
        int emailCheck;

        while (listIterator.hasNext()) {
            compareMe = listIterator.next();
            emailCheck = emailGiven.compareTo(compareMe.getEmail());

            if (emailCheck == 0)
                return compareMe;
        }

        return null;
    }


    public void readContactFiles() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("contacts.txt"));
        int limit = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < limit; i++) {
            Person newPerson = new Person();
            newPerson.setLastName(scanner.nextLine());
            newPerson.setFirstName(scanner.nextLine());
            newPerson.setAddress(scanner.nextLine());
            newPerson.setTelephone(scanner.nextLine());
            newPerson.setEmail(scanner.nextLine());
            listOfContacts.add(newPerson);

        }

    }
}
