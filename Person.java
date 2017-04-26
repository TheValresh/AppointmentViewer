/**
 * Created by Ahnaf on 4/13/2017.
 */
public class Person implements Comparable<Person>{
    String lastName, firstName, telephone, address, email;

    public Person(String inputLastName, String inputFirstName, String inputTelephone, String inputAddress, String inputEmail) {
        this.lastName = inputLastName;
        this.firstName = inputFirstName;
        this.telephone = inputTelephone;
        this.address = inputAddress;
        this.email = inputEmail;
    }

    public Person(String inputLastName, String inputFirstName, String inputTelephone, String inputAddress) {
        this.lastName = inputLastName;
        this.firstName = inputFirstName;
        this.telephone = inputTelephone;
        this.address = inputAddress;
        this.email = "";
    }

    public Person(String inputLastName, String inputFirstName, String inputTelephone) {
        this.lastName = inputLastName;
        this.firstName = inputFirstName;
        this.telephone = inputTelephone;
        this.address = "";
        this.email = "";
    }

    public Person(String inputLastName, String inputFirstName) {
        this.lastName = inputLastName;
        this.firstName = inputFirstName;
        this.telephone = "";
        this.address = "";
        this.email = "";
    }

    public Person(String inputLastName) {
        this.lastName = inputLastName;
        this.firstName = "";
        this.telephone = "";
        this.address = "";
        this.email = "";
    }

    public Person() {
        this.lastName = "";
        this.firstName = "";
        this.telephone = "";
        this.address = "";
        this.email = "";
    }

    public void setLastName(String inputLastName) {
        this.lastName = inputLastName;
    }

    public void setFirstName(String inputFirstName) {
        this.firstName = inputFirstName;
    }

    public void setTelephone(String inputTelephone) {
        this.telephone = inputTelephone;
    }

    public void setAddress(String inputAddress) {
        this.address = inputAddress;
    }

    public void setEmail(String inputEmail) {
        this.email = inputEmail;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return lastName + " " + firstName + " " + telephone + " " + address + " " + email;
    }

    public int compareTo(Person secondPerson) {
        if ((this.lastName.compareToIgnoreCase(secondPerson.getLastName())) == 0) {
            if ((this.firstName.compareToIgnoreCase(secondPerson.getFirstName())) == 0)
                return 0;
            else
                return this.firstName.compareToIgnoreCase(secondPerson.getFirstName());
        }
        else return this.lastName.compareToIgnoreCase(secondPerson.getLastName());

        }
}
