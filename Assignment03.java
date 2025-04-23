package lab09;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.Desktop;
import java.net.URI;

public class Assignment03{
    private static final Scanner inputScanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_INPUT_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    
    public static void main(String[] args) {
        
        EmergencyAlertSystem alertSystem = new EmergencyAlertSystem();
        ChatSystem chatSystem = new ChatSystem();
        VideoCallSystem videoSystem = new VideoCallSystem();
        AppointmentSystem appointmentSystem = new AppointmentSystem();
        PatientManagementSystem patientSystem = new PatientManagementSystem();
        
        boolean shouldExit = false;
        
        while (!shouldExit) {
            displayMainMenu();
            int menuSelection = getUserChoice(1, 10);
            
            switch (menuSelection) {
                case 1:
                    patientSystem.managePatients();
                    break;
                case 2:
                    alertSystem.checkVitals();
                    break;
                case 3:
                    alertSystem.activatePanicButton();
                    break;
                case 4:
                    chatSystem.startChatSession();
                    break;
                case 5:
                    videoSystem.initiateVideoCall();
                    break;
                case 6:
                    appointmentSystem.addAppointment();
                    break;
                case 7:
                    appointmentSystem.viewAppointments();
                    break;
                case 8:
                    appointmentSystem.editAppointment();
                    break;
                case 9:
                    appointmentSystem.sendReminders();
                    break;
                case 10:
                    shouldExit = true;
                    System.out.println("Exiting Remote Health Monitoring System. Goodbye!");
                    break;
            }
        }
        inputScanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== Remote Health Monitoring System ===");
        System.out.println("1. Manage Patients");
        System.out.println("2. Check Vitals and Trigger Emergency Alert");
        System.out.println("3. Press Panic Button");
        System.out.println("4. Chat between Doctor and Patient");
        System.out.println("5. Start a Video Call");
        System.out.println("6. Add Appointment");
        System.out.println("7. View All Appointments");
        System.out.println("8. Edit an Appointment");
        System.out.println("9. Send Appointment Reminders");
        System.out.println("10. Exit");
        System.out.print("Choice: ");
    }
    
    // Input handling utility methods
    public static int getUserChoice(int minValue, int maxValue) {
        while (true) {
            try {
                int userChoice = Integer.parseInt(inputScanner.nextLine().trim());
                if (userChoice >= minValue && userChoice <= maxValue) {
                    return userChoice;
                } else {
                    System.out.println("Please enter a number between " + minValue + " and " + maxValue);
                    System.out.print("Choice: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Choice: ");
            }
        }
    }
    
    public static double getDoubleInput(String promptMessage, double minAllowed, double maxAllowed) {
        while (true) {
            System.out.print(promptMessage);
            try {
                double userValue = Double.parseDouble(inputScanner.nextLine().trim());
                if (userValue >= minAllowed && userValue <= maxAllowed) {
                    return userValue;
                } else {
                    System.out.println("Please enter a value between " + minAllowed + " and " + maxAllowed);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    public static String getStringInput(String promptMessage) {
        System.out.print(promptMessage);
        String input = inputScanner.nextLine().trim();
        return input;
    }
    
    public static LocalDateTime getDateTimeInput() {
        while (true) {
            try {
                System.out.print("Enter date (dd/MM/yyyy): ");
                String dateString = inputScanner.nextLine().trim();
                LocalDate appointmentDate = LocalDate.parse(dateString, DATE_INPUT_FORMAT);
                
                System.out.print("Enter time (HH:mm): ");
                String timeString = inputScanner.nextLine().trim();
                LocalTime appointmentTime = LocalTime.parse(timeString, TIME_INPUT_FORMAT);
                
                return LocalDateTime.of(appointmentDate, appointmentTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time format. Please try again.");
            }
        }
    }
    
    //System for managing patients
    static class PatientManagementSystem {
        private List<Patient> patientsList = new ArrayList<>();
        
        public PatientManagementSystem() {
            // Adding some default patients as an example
            patientsList.add(new Patient("P001", "John Smith", 45, "Male", "123-456-7890", "john.smith@email.com"));
            patientsList.add(new Patient("P002", "Jane Doe", 32, "Female", "234-567-8901", "jane.doe@email.com"));
        }
        
        public void managePatients() {
            boolean exitPatientMenu = false;
            
            while (!exitPatientMenu) {
                System.out.println("\n=== Patient Management Menu ===");
                System.out.println("1. Add New Patient");
                System.out.println("2. View All Patients");
                System.out.println("3. Search Patient by ID");
                System.out.println("4. Update Patient Details");
                System.out.println("5. Back to Main Menu");
                System.out.print("Choice: ");
                
                int choice = getUserChoice(1, 5);
                
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        viewAllPatients();
                        break;
                    case 3:
                        searchPatient();
                        break;
                    case 4:
                        updatePatient();
                        break;
                    case 5:
                        exitPatientMenu = true;
                        break;
                }
            }
        }
        
        private void addPatient() {
            System.out.println("\n=== Add New Patient ===");
            
            String patientId = getStringInput("Enter Patient ID (e.g., P003): ");
            
            // Check if patient ID already exists
            for (Patient p : patientsList) {
                if (p.getPatientId().equals(patientId)) {
                    System.out.println("❌ Patient ID already exists. Please use a different ID.");
                    return;
                }
            }
            
            String name = getStringInput("Enter Patient Name: ");
            if (name.isEmpty()) {
                System.out.println("❌ Patient name cannot be empty.");
                return;
            }
            
            int age;
            try {
                age = (int) getDoubleInput("Enter Patient Age: ", 0, 120);
            } catch (Exception e) {
                System.out.println("❌ Invalid age input.");
                return;
            }
            
            String gender = getStringInput("Enter Patient Gender: ");
            String phone = getStringInput("Enter Phone Number: ");
            if (!isValidPhone(phone)) {
                System.out.println("❌ Invalid phone format.");
                return;
            }
            
            String email = getStringInput("Enter Email: ");
            if (!isValidEmail(email)) {
                System.out.println("❌ Invalid email format.");
                return;
            }
            
            Patient newPatient = new Patient(patientId, name, age, gender, phone, email);
            patientsList.add(newPatient);
            System.out.println("✅ Patient added successfully!");
        }
        
        private void viewAllPatients() {
            System.out.println("\n=== All Patients ===");
            if (patientsList.isEmpty()) {
                System.out.println("No patients registered in the system.");
                return;
            }
            
            for (Patient patient : patientsList) {
                System.out.println(patient);
                System.out.println("----------------------------");
            }
        }
        
        private void searchPatient() {
            String searchId = getStringInput("\nEnter Patient ID to search: ");
            
            for (Patient patient : patientsList) {
                if (patient.getPatientId().equals(searchId)) {
                    System.out.println("\n=== Patient Found ===");
                    System.out.println(patient);
                    return;
                }
            }
            
            System.out.println("❌ Patient not found with ID: " + searchId);
        }
        
        private void updatePatient() {
            String patientId = getStringInput("\nEnter Patient ID to update: ");
            
            for (int i = 0; i < patientsList.size(); i++) {
                if (patientsList.get(i).getPatientId().equals(patientId)) {
                    Patient patient = patientsList.get(i);
                    
                    System.out.println("\n=== Update Patient Details ===");
                    System.out.println("Current details: \n" + patient);
                    
                    String name = getStringInput("Enter new Name (or press Enter to keep current): ");
                    if (!name.isEmpty()) {
                        patient.setName(name);
                    }
                    
                    String ageStr = getStringInput("Enter new Age (or press Enter to keep current): ");
                    if (!ageStr.isEmpty()) {
                        try {
                            int age = Integer.parseInt(ageStr);
                            if (age > 0 && age < 120) {
                                patient.setAge(age);
                            } else {
                                System.out.println("Invalid age. Keeping current age.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid age format. Keeping current age.");
                        }
                    }
                    
                    String gender = getStringInput("Enter new Gender (or press Enter to keep current): ");
                    if (!gender.isEmpty()) {
                        patient.setGender(gender);
                    }
                    
                    String phone = getStringInput("Enter new Phone (or press Enter to keep current): ");
                    if (!phone.isEmpty()) {
                        if (isValidPhone(phone)) {
                            patient.setPhone(phone);
                        } else {
                            System.out.println("Invalid phone format. Keeping current phone.");
                        }
                    }
                    
                    String email = getStringInput("Enter new Email (or press Enter to keep current): ");
                    if (!email.isEmpty()) {
                        if (isValidEmail(email)) {
                            patient.setEmail(email);
                        } else {
                            System.out.println("Invalid email format. Keeping current email.");
                        }
                    }
                    
                    patientsList.set(i, patient);
                    System.out.println("✅ Patient updated successfully!");
                    return;
                }
            }
            
            System.out.println("❌ Patient not found with ID: " + patientId);
        }
        
        public Patient getPatientById(String patientId) {
            for (Patient patient : patientsList) {
                if (patient.getPatientId().equals(patientId)) {
                    return patient;
                }
            }
            return null;
        }
        
        private boolean isValidEmail(String email) {
            // Minimal check: contains @ with something before and after it
            if (email == null) {
                return false;
            }
            
            int atIndex = email.indexOf('@');
            return atIndex > 0 && atIndex < email.length() - 1;
        }
        
        private boolean isValidPhone(String phone) {
            // Simple check: contains at least 7 digits
            if (phone == null) {
                return false;
            }
            
            int digitCount = 0;
            for (char c : phone.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                }
            }
            
            return digitCount >= 7;
        }
    }
    
    /**
     * System for handling emergency alerts and vitals monitoring
     */
    static class EmergencyAlertSystem {
        private static final double CRITICAL_HEART_RATE_THRESHOLD_HIGH = 120.0;
        private static final double CRITICAL_HEART_RATE_THRESHOLD_LOW = 40.0;
        private static final double CRITICAL_BLOOD_PRESSURE_THRESHOLD_HIGH = 180.0;
        private static final double CRITICAL_BLOOD_PRESSURE_THRESHOLD_LOW = 90.0;
        private static final double CRITICAL_TEMPERATURE_THRESHOLD_HIGH = 39.0;
        private static final double CRITICAL_TEMPERATURE_THRESHOLD_LOW = 35.0;
        private static final double MIN_VALID_HEART_RATE = 30.0;
        private static final double MAX_VALID_HEART_RATE = 220.0;
        private static final double MIN_VALID_BLOOD_PRESSURE = 70.0;
        private static final double MAX_VALID_BLOOD_PRESSURE = 250.0;
        private static final double MIN_VALID_TEMPERATURE = 30.0;
        private static final double MAX_VALID_TEMPERATURE = 43.0;
        
        public void checkVitals() {
            System.out.println("\n[Emergency Alert System]");
            String patientId = getStringInput("Enter Patient ID: ");
            
            double patientHeartRate = getDoubleInput("Enter Heart Rate (bpm): ", MIN_VALID_HEART_RATE, MAX_VALID_HEART_RATE);
            double patientBloodPressure = getDoubleInput("Enter Blood Pressure (systolic): ", MIN_VALID_BLOOD_PRESSURE, MAX_VALID_BLOOD_PRESSURE);
            double patientTemperature = getDoubleInput("Enter Body Temperature (°C): ", MIN_VALID_TEMPERATURE, MAX_VALID_TEMPERATURE);
            
            EmergencyAlert vitalAlert = new EmergencyAlert(patientId, patientHeartRate, patientBloodPressure, patientTemperature);
            vitalAlert.checkAndTriggerAlert();
        }
        
        public void activatePanicButton() {
            System.out.println("\n[Panic Button Activated!]");
            String patientId = getStringInput("Enter Patient ID: ");
            
            double patientHeartRate = getDoubleInput("Enter Heart Rate (bpm): ", MIN_VALID_HEART_RATE, MAX_VALID_HEART_RATE);
            double patientBloodPressure = getDoubleInput("Enter Blood Pressure (systolic): ", MIN_VALID_BLOOD_PRESSURE, MAX_VALID_BLOOD_PRESSURE);
            double patientTemperature = getDoubleInput("Enter Body Temperature (°C): ", MIN_VALID_TEMPERATURE, MAX_VALID_TEMPERATURE);
            
            PanicButton emergencyButton = new PanicButton();
            emergencyButton.pressButton(patientId, patientHeartRate, patientBloodPressure, patientTemperature);
        }
    }
    
    
    static class ChatSystem {
        private ChatServer messageServer = new ChatServer();
        private ChatClient doctorClient = new ChatClient("Dr. Smith", messageServer);
        private ChatClient patientClient = new ChatClient("Patient", messageServer);
        
        public void startChatSession() {
            boolean exitChatSession = false;
            
            while (!exitChatSession) {
                System.out.println("\n[Chat Module]");
                System.out.println("1. Patient sends message");
                System.out.println("2. Doctor sends message");
                System.out.println("3. View all messages");
                System.out.println("4. Back to main menu");
                System.out.print("Choice: ");
                
                int chatMenuChoice = getUserChoice(1, 4);
                
                switch (chatMenuChoice) {
                    case 1:
                        System.out.print("Patient: ");
                        String patientMessage = inputScanner.nextLine();
                        patientClient.sendMessage(patientMessage);
                        break;
                    case 2:
                        System.out.print("Doctor: ");
                        String doctorMessage = inputScanner.nextLine();
                        doctorClient.sendMessage(doctorMessage);
                        break;
                    case 3:
                        System.out.println("\nChat History:");
                        patientClient.receiveMessages();
                        break;
                    case 4:
                        exitChatSession = true;
                        break;
                }
            }
        }
    }
    
    /**
     * System for managing video calls
     */
    static class VideoCallSystem {
        public void initiateVideoCall() {
            System.out.println("\n[Video Call]");
            String patientId = getStringInput("Enter Patient ID: ");
            
            
            
             String meetingURL = "https://meet.google.com/landing"; // google meet 

            try {
                Desktop.getDesktop().browse(new URI(meetingURL));
                System.out.println("Opening browser...");
            } catch (Exception ex) {
                System.out.println("Failed to open browser: " + ex.getMessage());
            }
        }
    }
    
    /**
     * System for managing appointments and reminders
     */
    static class AppointmentSystem {
        private ReminderService appointmentReminders = new ReminderService();
        
        public void addAppointment() {
            System.out.println("\n[Add Appointment]");
            
            String patientId = getStringInput("Enter patient ID: ");
            System.out.print("Enter patient name: ");
            String patientName = inputScanner.nextLine().trim();
            if (patientName.isEmpty()) {
                System.out.println("Patient name cannot be empty. Operation cancelled.");
                return;
            }
            
            LocalDateTime appointmentDateTime = getDateTimeInput();
            
            System.out.print("Enter doctor's name: ");
            String doctorName = inputScanner.nextLine().trim();
            
            System.out.print("Enter appointment note: ");
            String appointmentNotes = inputScanner.nextLine().trim();
            
            System.out.print("Enter email: ");
            String contactEmail = inputScanner.nextLine().trim();
            if (!isValidEmail(contactEmail)) {
                System.out.println("Invalid email format. Operation cancelled.");
                return;
            }
            
            System.out.print("Enter phone: ");
            String contactPhone = inputScanner.nextLine().trim();
            if (!isValidPhone(contactPhone)) {
                System.out.println("Invalid phone number format. Operation cancelled.");
                return;
            }
            
            Appointment newAppointment = new Appointment(patientId, patientName, doctorName, appointmentDateTime, appointmentNotes, contactEmail, contactPhone);
            appointmentReminders.addAppointment(newAppointment);
            System.out.println("✅ Appointment added successfully!");
        }
        
        public void viewAppointments() {
            System.out.println("\n[Appointments List]");
            appointmentReminders.listAppointments();
        }
        
        public void editAppointment() {
            System.out.println("\n[Edit Appointment]");
            List<Appointment> scheduledAppointments = appointmentReminders.getAppointments();
            
            if (scheduledAppointments.isEmpty()) {
                System.out.println("No appointments to edit.");
                return;
            }
            
            appointmentReminders.listAppointments();
            System.out.print("Enter appointment number to edit (0 to cancel): ");
            int appointmentIndex = getUserChoice(0, scheduledAppointments.size());
            
            if (appointmentIndex == 0) {
                System.out.println("Edit operation cancelled.");
                return;
            }
            
            LocalDateTime updatedDateTime = getDateTimeInput();
            
            System.out.print("Enter new doctor's name (or press Enter to keep current): ");
            String updatedDoctor = inputScanner.nextLine().trim();
            
            System.out.print("Enter new note (or press Enter to keep current): ");
            String updatedNotes = inputScanner.nextLine().trim();
            
            appointmentReminders.editAppointment(appointmentIndex - 1, updatedDateTime, updatedDoctor, updatedNotes);
        }
        
        public void sendReminders() {
            System.out.println("\n[Send Appointment Reminders]");
            List<Appointment> scheduledAppointments = appointmentReminders.getAppointments();
            
            if (scheduledAppointments.isEmpty()) {
                System.out.println("No appointments to send reminders for.");
                return;
            }
            
            System.out.println("Select an appointment to send a reminder:");
            for (int i = 0; i < scheduledAppointments.size(); i++) {
                System.out.println((i + 1) + ". " + scheduledAppointments.get(i).getFormattedDetails());
                System.out.println("-----------------------------------");
            }
            System.out.println((scheduledAppointments.size() + 1) + ". Send reminders for all appointments");
            System.out.println("0. Cancel");
            
            int reminderChoice = getUserChoice(0, scheduledAppointments.size() + 1);
            
            if (reminderChoice == 0) {
                System.out.println("Operation cancelled.");
                return;
            } else if (reminderChoice == scheduledAppointments.size() + 1) {
                for (Appointment appt : scheduledAppointments) {
                    appointmentReminders.sendAppointmentReminder(appt);
                }
                System.out.println("✅ Reminders sent for all appointments.");
            } else {
                Appointment selectedAppointment = scheduledAppointments.get(reminderChoice - 1);
                appointmentReminders.sendAppointmentReminder(selectedAppointment);
                System.out.println("✅ Reminder sent for selected appointment.");
            }
        }
        
        private boolean isValidEmail(String email) {
            return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        }
        
        private boolean isValidPhone(String phone) {
            return phone != null && phone.matches("\\+?[0-9-]{10,15}");
        }
    }
}

/**
 * Represents a patient in the system
 */
class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    
    public Patient(String patientId, String name, int age, String gender, String phone, String email) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Patient ID: " + patientId +
               "\nName: " + name +
               "\nAge: " + age +
               "\nGender: " + gender +
               "\nPhone: " + phone +
               "\nEmail: " + email;
    }
}

/**
 * Represents an emergency alert triggered by abnormal vital signs
 */
class EmergencyAlert {
    private String patientId;
    private double patientHeartRate;
    private double patientBloodPressure;
    private double patientTemperature;
    private static final double CRITICAL_HEART_RATE_THRESHOLD_HIGH = 120.0;
    private static final double CRITICAL_HEART_RATE_THRESHOLD_LOW = 40.0;
    private static final double CRITICAL_BLOOD_PRESSURE_THRESHOLD_HIGH = 180.0;
    private static final double CRITICAL_BLOOD_PRESSURE_THRESHOLD_LOW = 90.0;
    private static final double CRITICAL_TEMPERATURE_THRESHOLD_HIGH = 39.0;
    private static final double CRITICAL_TEMPERATURE_THRESHOLD_LOW = 35.0;

    public EmergencyAlert(String patientId, double heartRate, double bloodPressure, double temperature) {
        this.patientId = patientId;
        this.patientHeartRate = heartRate;
        this.patientBloodPressure = bloodPressure;
        this.patientTemperature = temperature;
    }

    public void checkAndTriggerAlert() {
        try {
            boolean isCritical = false;
            StringBuilder alertDetails = new StringBuilder();
            
            // Check heart rate
            if (patientHeartRate > CRITICAL_HEART_RATE_THRESHOLD_HIGH) {
                isCritical = true;
                alertDetails.append("High heart rate: ").append(patientHeartRate).append(" bpm\n");
            } else if (patientHeartRate < CRITICAL_HEART_RATE_THRESHOLD_LOW) {
                isCritical = true;
                alertDetails.append("Low heart rate: ").append(patientHeartRate).append(" bpm\n");
            }
            
            // Check blood pressure
            if (patientBloodPressure > CRITICAL_BLOOD_PRESSURE_THRESHOLD_HIGH) {
                isCritical = true;
                alertDetails.append("High blood pressure: ").append(patientBloodPressure).append(" mmHg\n");
            } else if (patientBloodPressure < CRITICAL_BLOOD_PRESSURE_THRESHOLD_LOW) {
                isCritical = true;
                alertDetails.append("Low blood pressure: ").append(patientBloodPressure).append(" mmHg\n");
            }
            
            // Check temperature
            if (patientTemperature > CRITICAL_TEMPERATURE_THRESHOLD_HIGH) {
                isCritical = true;
                alertDetails.append("High temperature: ").append(patientTemperature).append(" °C\n");
            } else if (patientTemperature < CRITICAL_TEMPERATURE_THRESHOLD_LOW) {
                isCritical = true;
                alertDetails.append("Low temperature: ").append(patientTemperature).append(" °C\n");
            }
            
            if (isCritical) {
                System.out.println("⚠️ CRITICAL VITALS DETECTED! Triggering emergency alert.");
                String alertMessage = "EMERGENCY ALERT for Patient " + patientId + ":\n" + alertDetails.toString()
                        + "Heart Rate: " + patientHeartRate + " bpm\n"
                        + "Blood Pressure: " + patientBloodPressure + " mmHg\n"
                        + "Temperature: " + patientTemperature + " °C";
                NotificationService.sendAlert(alertMessage);
            } else {
                System.out.println("✅ Vitals are within normal ranges.");
                System.out.println("Heart Rate: " + patientHeartRate + " bpm");
                System.out.println("Blood Pressure: " + patientBloodPressure + " mmHg");
                System.out.println("Temperature: " + patientTemperature + " °C");
            }
        } catch (Exception e) {
            System.err.println("❌ Error triggering emergency alert: " + e.getMessage());
        }
    }
}

/**
 * Service for sending notifications through multiple channels
 */
class NotificationService {
    public static void sendAlert(String alertMessage) {
        List<Notifiable> notificationChannels = new ArrayList<>();
        notificationChannels.add(new EmailNotification("doctor@example.com"));
        notificationChannels.add(new SMSNotification("+1234567890"));
        notificationChannels.add(new EmailNotification("emergencystaff@hospital.com"));

        System.out.println("\n📢 Sending notifications through all available channels:");
        for (Notifiable channel : notificationChannels) {
            channel.notifyUser(alertMessage);
        }
    }
    
    public static void sendNotification(String recipientEmail, String recipientPhone, String message) {
        List<Notifiable> notificationChannels = new ArrayList<>();
        notificationChannels.add(new EmailNotification(recipientEmail));
        notificationChannels.add(new SMSNotification(recipientPhone));

        System.out.println("\n📢 Sending notification:");
        for (Notifiable channel : notificationChannels) {
            channel.notifyUser(message);
        }
    }
}

class PanicButton {
    // Make sure the method signature exactly matches what's expected:
    // void pressButton(String, double, double, double)
    public void pressButton(String patientId, double heartRate, double bloodPressure, double temperature) {
        System.out.println("🚨 Panic button activated by patient " + patientId + "!");
        EmergencyAlert emergencyAlert = new EmergencyAlert(patientId, heartRate, bloodPressure, temperature);
        emergencyAlert.checkAndTriggerAlert();
        
        // Additional urgent notification
        String urgentMessage = "URGENT: Patient " + patientId + " has activated the panic button!\n" +
                              "Please check on the patient immediately.";
        NotificationService.sendAlert(urgentMessage);
    }
}

/**
 * Represents a message in the chat system
 */
class Message {
    private String senderName;
    private String messageContent;
    private LocalDateTime sentTimestamp;

    public Message(String sender, String content) {
        this.senderName = sender;
        this.messageContent = content;
        this.sentTimestamp = LocalDateTime.now();
    }

    public String getSender() {
        return senderName;
    }

    public String getContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return sentTimestamp;
    }

   @Override
    public String toString() {
        String formattedTime = sentTimestamp.format(DateTimeFormatter.ofPattern("hh:mm a"));
        return "[" + formattedTime + "] " + senderName + ": " + messageContent;
    }
}

/**
 * Server for storing and managing chat messages
 */
class ChatServer {
    private List<Message> messageHistory;

    public ChatServer() {
        messageHistory = new ArrayList<>();
    }

    public void addMessage(Message newMessage) {
        messageHistory.add(newMessage);
        System.out.println("✉️ New message received.");
    }

    public List<Message> getMessages() {
        return messageHistory;
    }
}

/**
 * Client for interacting with the chat server
 */
class ChatClient {
    private String userIdentity;
    private ChatServer messageServer;

    public ChatClient(String username, ChatServer server) {
        this.userIdentity = username;
        this.messageServer = server;
    }

    public void sendMessage(String messageContent) {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            System.out.println("❌ Cannot send empty message.");
            return;
        }
        
        Message newMessage = new Message(userIdentity, messageContent);
        messageServer.addMessage(newMessage);
    }

    public void receiveMessages() {
        List<Message> availableMessages = messageServer.getMessages();
        if (availableMessages.isEmpty()) {
            System.out.println("No messages yet.");
            return;
        }
        
        for (Message msg : availableMessages) {
            System.out.println(msg);
        }
    }
}

/**
 * Represents a video call session
 */
class VideoCall {
    private String patientId;
    private String doctorName;
    private String callMeetingLink;

    public VideoCall(String patientId, String doctorName, String meetingLink) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.callMeetingLink = meetingLink;
    }

    public void startCall() {
        System.out.println("📹 Starting video call between Dr. " + doctorName + " and Patient " + patientId);
        System.out.println("Please join the meeting using this link: " + callMeetingLink);
        System.out.println("Waiting for participants to join...");
        
        // Simulate call progress
        try {
            System.out.println("Connecting...");
            Thread.sleep(1500);
            System.out.println("Connection established.");
            Thread.sleep(800);
            System.out.println("Video call in progress...");
        } catch (InterruptedException e) {
            System.out.println("Video call interrupted.");
        }
    }
}

/**
 * Interface for notification channels
 */
interface Notifiable {
    void notifyUser(String message);
}

/**
 * Email notification implementation
 */
class EmailNotification implements Notifiable {
    private String recipientEmail;

    public EmailNotification(String email) {
        this.recipientEmail = email;
    }

    @Override
    public void notifyUser(String messageContent) {
        System.out.println("📧 Sending Email to " + recipientEmail + ":\n" + messageContent);
    }
}

/**
 * SMS notification implementation
 */
class SMSNotification implements Notifiable {
    private String recipientPhoneNumber;

    public SMSNotification(String phoneNumber) {
        this.recipientPhoneNumber = phoneNumber;
    }

    @Override
    public void notifyUser(String messageContent) {
        System.out.println("📱 Sending SMS to " + recipientPhoneNumber + ":\n" + messageContent);
    }
}

/**
 * Service for managing and sending appointment reminders
 */
class ReminderService {
    private List<Appointment> scheduledAppointments;

    public ReminderService() {
        scheduledAppointments = new ArrayList<>();
        // Add a default appointment for testing
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(14).withMinute(30);
        Appointment defaultAppointment = new Appointment(
            "P001", "John Smith", "Dr. Williams", 
            tomorrow,
            "Regular checkup", 
            "john.smith@email.com", 
            "123-456-7890"
        );
        scheduledAppointments.add(defaultAppointment);
    }

    public void addAppointment(Appointment newAppointment) {
        scheduledAppointments.add(newAppointment);
        // Send confirmation
        sendAppointmentConfirmation(newAppointment);
    }
    
    public void sendAppointmentConfirmation(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm a");
        String formattedDateTime = appointment.getDateTime().format(formatter);

        String confirmationMessage = "📅 Appointment Confirmation for " + appointment.getPatientName() + ":\n" +
                "Date: " + formattedDateTime + "\n" +
                "Doctor: " + appointment.getDoctorName() + "\n" +
                "Note: " + appointment.getNote() + "\n\n" +
                "This appointment has been confirmed. Please arrive 15 minutes early.";

        NotificationService.sendNotification(appointment.getEmail(), appointment.getPhone(), confirmationMessage);
    }

    public void sendAppointmentReminder(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm a");
        String formattedDateTime = appointment.getDateTime().format(formatter);

        String reminderMessage = "📅 Appointment Reminder for " + appointment.getPatientName() + ":\n" +
                "Date: " + formattedDateTime + "\n" +
                "Doctor: " + appointment.getDoctorName() + "\n" +
                "Note: " + appointment.getNote() + "\n\n" +
                "Please don't forget your upcoming appointment.";

        NotificationService.sendNotification(appointment.getEmail(), appointment.getPhone(), reminderMessage);
    }

    public void listAppointments() {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("=== Scheduled Appointments ===");
            int index = 1;
            for (Appointment appt : scheduledAppointments) {
                System.out.println(index + ". " + appt.getFormattedDetails());
                System.out.println("-----------------------------------");
                index++;
            }
        }
    }

    public void editAppointment(int appointmentIndex, LocalDateTime newDateTime, String newDoctor, String newNote) {
        if (appointmentIndex >= 0 && appointmentIndex < scheduledAppointments.size()) {
            Appointment appointment = scheduledAppointments.get(appointmentIndex);
            appointment.setDateTime(newDateTime);
            
            if (!newDoctor.isEmpty()) {
                appointment.setDoctorName(newDoctor);
            }
            
            if (!newNote.isEmpty()) {
                appointment.setNote(newNote);
            }
            
            scheduledAppointments.set(appointmentIndex, appointment);
            System.out.println("✅ Appointment updated successfully.");
            
            // Send update notification
            sendAppointmentUpdateNotification(appointment);
        } else {
            System.out.println("❌ Invalid appointment index.");
        }
    }
    
    public void sendAppointmentUpdateNotification(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm a");
        String formattedDateTime = appointment.getDateTime().format(formatter);

        String updateMessage = "📅 Appointment Update for " + appointment.getPatientName() + ":\n" +
                "Updated Date: " + formattedDateTime + "\n" +
                "Doctor: " + appointment.getDoctorName() + "\n" +
                "Note: " + appointment.getNote() + "\n\n" +
                "Your appointment has been updated. Please confirm if this works for you.";

        NotificationService.sendNotification(appointment.getEmail(), appointment.getPhone(), updateMessage);
    }

    public List<Appointment> getAppointments() {
        return scheduledAppointments;
    }
}

/**
 * Represents a medical appointment
 */
class Appointment {
    private String patientId;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDateTime;
    private String appointmentNote;
    private String contactEmail;
    private String contactPhone;

    public Appointment(String patientId, String patientName, String doctorName, LocalDateTime dateTime, String note, String email, String phone) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDateTime = dateTime;
        this.appointmentNote = note;
        this.contactEmail = email;
        this.contactPhone = phone;
    }

    public String getPatientId() {
        return patientId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getDateTime() {
        return appointmentDateTime;
    }

    public String getNote() {
        return appointmentNote;
    }

    public String getEmail() {
        return contactEmail;
    }

    public String getPhone() {
        return contactPhone;
    }

    public void setDateTime(LocalDateTime newDateTime) {
        this.appointmentDateTime = newDateTime;
    }

    public void setNote(String newNote) {
        this.appointmentNote = newNote;
    }

    public String getFormattedDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm a");
        return "📅 " + patientName + " (ID: " + patientId + ") | " + appointmentDateTime.format(formatter) + "\n" +
                "Doctor: " + doctorName + "\n" +
                "Note: " + appointmentNote + "\nEmail: " + contactEmail + " | Phone: " + contactPhone;
    }
}