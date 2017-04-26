import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Ahnaf Mir STUDENT ID: 500570401 on 4/13/2017.
 */
public class AppointmentFrame extends JFrame {
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 800;

    Calendar currentDate;
    SimpleDateFormat currentDateSDF;
    SimpleDateFormat currentDateAndTimeAppointment;
    ArrayList<Appointment> appointments;
    JLabel currentDateLabel;
    JTextArea appointmentText;
    JScrollPane scrollPane;
    JPanel controlPanel;
    JPanel descriptionPanel;
    JLabel descriptionLabel;
    JTextField descriptionTextField;
    JPanel dateControlPanel;
    JButton previousDay;
    JButton nextDay;
    JLabel dayRequestLabel;
    JTextField requestedDay;
    JLabel monthRequestLabel;
    JTextField requestedMonth;
    JLabel yearRequestLabel;
    JTextField requestedYear;
    JButton showAppointmentButton;
    JPanel actionPanel;
    JLabel makeHour;
    JTextField requestedHour;
    JLabel makeMinutes;
    JTextField requestedMinute;
    JButton makeAppointmentButton;
    JButton removeAppointmentButton;
    JLabel contactLastNameLabel;
    JTextField contactLastNameField;
    JLabel contactFirstName;
    JTextField contactFirstNameField;
    JLabel contactTelephoneLabel;
    JTextField contactTelephoneField;
    JLabel contactAddressLabel;
    JTextField contactAddressField;
    JLabel contactEmailLabel;
    JTextField contactEmailField;
    JButton findPerson;
    JButton clearPersonInfo;
    JButton recallButton;
    JButton currentDayButton;
    Calendar dayInRealTime;
    Contacts contactList;
    Stack<Appointment> appointmentStack;

    public AppointmentFrame() {
        contactList = new Contacts();
        appointmentStack = new Stack<Appointment>();
        try {
            contactList.readContactFiles();
        } catch (FileNotFoundException exception) {
            descriptionTextField.setText("Cannot read file");
        } catch (NumberFormatException exception) {
            descriptionTextField.setText("File imported did not contain a number in the first line.");
        }

        createAppointmentPanel();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void createAppointmentPanel() {
        JPanel appointmentViewerWindow = createAppointmentViewerWindow();
        JPanel appointmentDateControlsPanel = createAppointmentDateControlsPanel();
        JPanel appointmentControlsPanel = createActionPanel();
        JPanel description = createDescriptionPanel();
        JPanel contactPanel = createContactPanel();


        JPanel appointmentPanel = new JPanel();
        appointmentPanel.setLayout(new GridLayout(4, 2));
        appointmentPanel.add(appointmentViewerWindow);
        appointmentPanel.add(appointmentDateControlsPanel);
        appointmentPanel.add(appointmentControlsPanel);
        appointmentPanel.add(description);
        appointmentPanel.add(contactPanel);

        add(appointmentPanel);
    }

    public JPanel createAppointmentViewerWindow() {

        JPanel appointmentViewerWindow = new JPanel();

        appointments = new ArrayList<Appointment>();
        currentDate = new GregorianCalendar();
        currentDate = currentDate.getInstance(); // Set currentDate to the time the program is run.
        dayInRealTime = new GregorianCalendar();
        dayInRealTime = dayInRealTime.getInstance();
        currentDateSDF = new SimpleDateFormat("EEE, dd, MMM, yyyy");

        currentDateLabel = new JLabel();
        currentDateLabel.setText("" + currentDateSDF.format(currentDate.getTime()));
        currentDateAndTimeAppointment = new SimpleDateFormat("HH : mm EEE, dd, MMM, yyyy");
        appointmentViewerWindow.add(currentDateLabel, BorderLayout.NORTH);

        appointmentText = new JTextArea(80, 50);
        appointmentViewerWindow.add(appointmentText);
        scrollPane = new JScrollPane(appointmentText);
        appointmentViewerWindow.add(scrollPane);

        return appointmentViewerWindow;

    }


    public JPanel createAppointmentDateControlsPanel() {
        dateControlPanel = new JPanel();

        previousDay = new JButton("<");
        dateControlPanel.add(previousDay, BorderLayout.NORTH);
        previousDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.add(currentDate.DAY_OF_MONTH, -1);
                appointmentText.setText(printAppointments(appointments, currentDate));
                currentDateLabel.setText(currentDateSDF.format(currentDate.getTime()));
            }
        });

        nextDay = new JButton(">");
        dateControlPanel.add(nextDay, BorderLayout.NORTH);
        nextDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.add(currentDate.DAY_OF_MONTH, 1);
                appointmentText.setText(printAppointments(appointments, currentDate));
                currentDateLabel.setText(currentDateSDF.format(currentDate.getTime()));
            }
        });

        currentDayButton = new JButton("Current Day");
        dateControlPanel.add(currentDayButton);
        currentDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.setTime(dayInRealTime.getTime());
                appointmentText.setText(printAppointments(appointments, dayInRealTime));
                currentDateLabel.setText(currentDateSDF.format(dayInRealTime.getTime()));
            }
        });

        dayRequestLabel = new JLabel("Day");
        dateControlPanel.add(dayRequestLabel, BorderLayout.CENTER);
        requestedDay = new JTextField(2);
        dateControlPanel.add(requestedDay, BorderLayout.CENTER);

        monthRequestLabel = new JLabel("Month");
        dateControlPanel.add(monthRequestLabel, BorderLayout.CENTER);
        requestedMonth = new JTextField(2);
        dateControlPanel.add(requestedMonth, BorderLayout.CENTER);

        yearRequestLabel = new JLabel("Year");
        dateControlPanel.add(yearRequestLabel, BorderLayout.CENTER);
        requestedYear = new JTextField(4);
        dateControlPanel.add(requestedYear, BorderLayout.CENTER);

        showAppointmentButton = new JButton("Show");
        dateControlPanel.add(showAppointmentButton, BorderLayout.SOUTH);
        showAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dayReq = Integer.parseInt(requestedDay.getText());
                int monthReq = Integer.parseInt(requestedMonth.getText());
                int yearReq = Integer.parseInt(requestedYear.getText());
                // Error checking for wrong dates
                if (monthReq > 12) {
                    descriptionTextField.setText("Months are invalid");
                    throw new IllegalArgumentException("Months are invalid");
                }

                if ((dayReq > 29) && monthReq == 2) {
                    descriptionTextField.setText("Days are invalid");
                    throw new IllegalArgumentException("Days is invalid");
                }

                if (((dayReq == 29) && (monthReq == 2)) && ((yearReq % 4) != 0)) {
                    descriptionTextField.setText("Days are invalid");
                    throw new IllegalArgumentException("Days is invalid");
                }

                if ((dayReq > 30) && ((monthReq != 1) || (monthReq != 3) || (monthReq != 5) || (monthReq != 7) || (monthReq != 8) || (monthReq != 10) || (monthReq != 12))) {
                    descriptionTextField.setText("Days are invalid");
                    throw new IllegalArgumentException("Days is invalid");
                }

                currentDate.set(yearReq + 0, monthReq - 1, dayReq + 0);
                appointmentText.setText(printAppointments(appointments, currentDate));
                currentDateLabel.setText(currentDateSDF.format(currentDate.getTime()));
            }
        });

        return dateControlPanel;
    }

    public JPanel createDescriptionPanel() {


        descriptionPanel = new JPanel();

        descriptionLabel = new JLabel("Description");
        descriptionPanel.add(descriptionLabel);

        descriptionTextField = new JTextField(40);
        descriptionPanel.add(descriptionTextField);

        return descriptionPanel;

    }

    // Action Control Panel functions

    public JPanel createActionPanel() {
        actionPanel = new JPanel();

        makeHour = new JLabel("Hour");
        actionPanel.add(makeHour);
        requestedHour = new JTextField(2);
        actionPanel.add(requestedHour);

        makeMinutes = new JLabel("Minutes");
        actionPanel.add(makeMinutes);
        requestedMinute = new JTextField(2);
        actionPanel.add(requestedMinute);

        makeAppointmentButton = new JButton("Create");
        actionPanel.add(makeAppointmentButton);
        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year, month, day, hourReq, minuteReq;


                year = currentDate.get(Calendar.YEAR);
                month = currentDate.get(Calendar.MONTH);
                day = currentDate.get(Calendar.DAY_OF_MONTH);
                hourReq = Integer.parseInt(requestedHour.getText());
                minuteReq = Integer.parseInt(requestedMinute.getText());
                // Error checking for invalid values here
                if (hourReq > 24) {
                    descriptionTextField.setText("Hours are invalid");
                    throw new IllegalArgumentException("Hours is invalid.");
                }

                if (minuteReq > 59) {
                    descriptionTextField.setText("Months are invalid");
                    throw new IllegalArgumentException("Minutes are invalid");
                }


                Calendar requestedAppointmentTime = new GregorianCalendar(year, month, day, hourReq, minuteReq, 0);
                String requestedAppointmentDescription = descriptionTextField.getText();
                Person requestedPerson = new Person();
                requestedPerson.setLastName(contactLastNameField.getText());
                requestedPerson.setFirstName(contactFirstNameField.getText());
                requestedPerson.setTelephone(contactTelephoneField.getText());
                requestedPerson.setAddress(contactAddressField.getText());
                requestedPerson.setEmail(contactEmailField.getText());
                Appointment requestedAppointment = new Appointment(requestedAppointmentTime, requestedAppointmentDescription, requestedPerson);

                if (!findAppointment(appointments, requestedAppointment)) {
                    appointments.add(requestedAppointment);
                    appointmentStack.push(requestedAppointment);
                    appointmentText.setText(printAppointments(appointments, currentDate));
                } else
                    descriptionTextField.setText("CONFLICT!!");
            }
        });

        removeAppointmentButton = new JButton("Cancel");
        actionPanel.add(removeAppointmentButton);
        removeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                int hourReq = Integer.parseInt(requestedHour.getText());
                int minuteReq = Integer.parseInt(requestedMinute.getText());

                Calendar requestedAppointmentTime = new GregorianCalendar(year, month, day, hourReq, minuteReq, 0);
                String requestedAppointmentDescription = descriptionTextField.getText();
                Person personToRemove = new Person();
                personToRemove.setLastName(contactLastNameField.getText());
                personToRemove.setFirstName(contactFirstNameField.getText());
                personToRemove.setEmail(contactEmailField.getText());
                personToRemove.setAddress(contactAddressField.getText());
                personToRemove.setTelephone(contactTelephoneField.getText());
                Appointment requestedAppointment = new Appointment(requestedAppointmentTime, requestedAppointmentDescription, personToRemove);

                if (findAppointment(appointments, requestedAppointment)) {
                    for (int i = 0; i < appointments.size(); i++) {
                        Appointment checkingThisAppointment = appointments.get(i);
                        if (requestedAppointment.getDate().equals(checkingThisAppointment.getDate())) {
                            appointments.remove(i);
                            appointmentStack.pop();
                        }
                    }
                }

                appointmentText.setText(printAppointments(appointments, currentDate));
            }
        });

        recallButton = new JButton("Recall");
        actionPanel.add(recallButton);
        recallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Appointment appointmentToSetFieldsTo = appointmentStack.peek();
                Person personDetails = appointmentToSetFieldsTo.getPerson();
                Calendar dateDetails = appointmentToSetFieldsTo.getDate();


                contactAddressField.setText(personDetails.getAddress());
                contactEmailField.setText(personDetails.getEmail());
                contactTelephoneField.setText(personDetails.getTelephone());
                contactFirstNameField.setText(personDetails.getFirstName());
                contactLastNameField.setText(personDetails.getLastName());

                currentDate = dateDetails;
                requestedHour.setText(currentDate.get(Calendar.HOUR_OF_DAY) + "");
                requestedMinute.setText(currentDate.get(Calendar.MINUTE) + "");
                appointmentText.setText(printAppointments(appointments, currentDate));
                currentDateLabel.setText(currentDateSDF.format(currentDate.getTime()));
            }
        });

        return actionPanel;
    }

    public JPanel createContactPanel() {
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new GridLayout(6, 2));

        contactLastNameLabel = new JLabel("Last Name");
        contactPanel.add(contactLastNameLabel);
        contactFirstName = new JLabel("First Name");
        contactPanel.add(contactFirstName);
        contactLastNameField = new JTextField(20);
        contactPanel.add(contactLastNameField);
        contactFirstNameField = new JTextField(20);
        contactPanel.add(contactFirstNameField);
        contactEmailLabel = new JLabel("Email");
        contactPanel.add(contactEmailLabel);
        contactTelephoneLabel = new JLabel("Telephone");
        contactPanel.add(contactTelephoneLabel);
        contactEmailField = new JTextField(20);
        contactPanel.add(contactEmailField);
        contactTelephoneField = new JTextField(20);
        contactPanel.add(contactTelephoneField);
        contactAddressLabel = new JLabel("Address");
        contactPanel.add(contactAddressLabel);
        contactAddressField = new JTextField(20);
        contactPanel.add(contactAddressField);

        findPerson = new JButton("Find");
        contactPanel.add(findPerson);
        findPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lastName, firstName, address, telephone, email;
                lastName = contactLastNameField.getText();
                firstName = contactFirstNameField.getText();
                address = contactAddressField.getText();
                telephone = contactTelephoneField.getText();
                email = contactEmailField.getText();
                Person personFound = new Person();

                try {

                    if (!email.isEmpty())
                        personFound = contactList.FindPersonWithEmail(email);

                    if (!telephone.isEmpty() && email.isEmpty())
                        personFound = contactList.FindPersonWithNumber(telephone);

                    if (email.isEmpty() && telephone.isEmpty())
                        personFound = contactList.FindPersonWithName(lastName, firstName);

                    contactLastNameField.setText(personFound.getLastName());
                    contactFirstNameField.setText(personFound.getFirstName());
                    contactAddressField.setText(personFound.getAddress());
                    contactTelephoneField.setText(personFound.getTelephone());
                    contactEmailField.setText(personFound.getEmail());
                } catch (NullPointerException exception) {
                    descriptionTextField.setText("Unable to find person, please check the fields."); // Catches wrong input or nullpointers.
                }
            }
        });

        clearPersonInfo = new JButton("Clear");
        contactPanel.add(clearPersonInfo);
        clearPersonInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactLastNameField.setText("");
                contactFirstNameField.setText("");
                contactTelephoneField.setText("");
                contactEmailField.setText("");
                contactAddressField.setText("");
            }
        });


        return contactPanel;
    }

    public boolean findAppointment(ArrayList<Appointment> listOfAppointments, Appointment appointmentToFind) {
        for (int i = 0; i < listOfAppointments.size(); i++) {
            Appointment appointmentToCheck = listOfAppointments.get(i);
            if ((appointmentToFind.occursOn(appointmentToCheck))) {
                return true;
            }
        }
        return false;
    }

    public String printAppointments(ArrayList<Appointment> listOfAppointments, Calendar inputDate) {
        Collections.sort(listOfAppointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment app1, Appointment app2) {
                return app1.getDate().compareTo(app2.getDate());
            }
        });

        Collections.sort(listOfAppointments);

        String relevantAppointments = "";

        for (int i = 0; i < listOfAppointments.size(); i++) {
            Appointment appointmentBeingChecked = listOfAppointments.get(i);
            Calendar checkedDate = appointmentBeingChecked.getDate();
            boolean sameYear, sameMonth, sameDay;
            sameYear = checkedDate.get(Calendar.YEAR) == inputDate.get(Calendar.YEAR);
            sameMonth = checkedDate.get(Calendar.MONTH) == inputDate.get(Calendar.MONTH);
            sameDay = checkedDate.get(Calendar.DAY_OF_MONTH) == inputDate.get(Calendar.DAY_OF_MONTH);
            if (sameYear && sameMonth && sameDay) {
                relevantAppointments = relevantAppointments + currentDateAndTimeAppointment.format(checkedDate.getTime()) + " "
                        + appointmentBeingChecked.getDescription() + " " + appointmentBeingChecked.getPerson() + "\n";
            }
        }
        return relevantAppointments;
    }
}
