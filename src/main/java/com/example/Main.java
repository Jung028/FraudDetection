package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// Main class
public class Main {

    public static void main(String[] args) throws IOException {
        // Create a BufferedReader for user input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Creating an instance of InputValidator to handle input validation
        InputValidator inputValidator = new InputValidator(reader);

        // Collecting user input with validation
        int age = inputValidator.getAge();
        String gender = inputValidator.getGender();
        String policyType = inputValidator.getPolicyType();
        String incidentType = inputValidator.getIncidentType();
        String damageSeverity = inputValidator.getDamageSeverity();
        int claimAmount = inputValidator.getClaimAmount();
        int settlementTime = inputValidator.getSettlementTime();
        int supportingDocs = inputValidator.getSupportingDocs();
        int timeToReport = inputValidator.getTimeToReport();

        // Create an instance of FraudInput to store the data
        FraudInput input = new FraudInput(age, gender, policyType, incidentType, damageSeverity, claimAmount, settlementTime, supportingDocs, timeToReport);

        // Create an instance of APICommunicator to handle sending the data and receiving the response
        APICommunicator apiCommunicator = new APICommunicator();
        String response = apiCommunicator.sendRequest(input);

        // Print the response (prediction result)
        System.out.println("Response from API: " + response);
    }
}

// Class to hold user input data
class FraudInput {
    private int PolicyholderAge;
    private String PolicyholderGender;
    private String PolicyType;
    private String IncidentType;
    private String DamageSeverity;
    private int ClaimAmount;
    private int SettlementTime;
    private int SupportingDocsProvided;
    private int TimeToReport;

    // Constructor
    public FraudInput(int PolicyholderAge, String PolicyholderGender, String PolicyType, String IncidentType, String DamageSeverity,
                      int ClaimAmount, int SettlementTime, int SupportingDocsProvided, int TimeToReport) {
        this.PolicyholderAge = PolicyholderAge;
        this.PolicyholderGender = PolicyholderGender;
        this.PolicyType = PolicyType;
        this.IncidentType = IncidentType;
        this.DamageSeverity = DamageSeverity;
        this.ClaimAmount = ClaimAmount;
        this.SettlementTime = SettlementTime;
        this.SupportingDocsProvided = SupportingDocsProvided;
        this.TimeToReport = TimeToReport;
    }

    // Getters for each field
    public int getPolicyholderAge() { return PolicyholderAge; }
    public String getPolicyholderGender() { return PolicyholderGender; }
    public String getPolicyType() { return PolicyType; }
    public String getIncidentType() { return IncidentType; }
    public String getDamageSeverity() { return DamageSeverity; }
    public int getClaimAmount() { return ClaimAmount; }
    public int getSettlementTime() { return SettlementTime; }
    public int getSupportingDocsProvided() { return SupportingDocsProvided; }
    public int getTimeToReport() { return TimeToReport; }
}

// Abstract class for input validators (Abstraction)
abstract class InputValidatorBase {
    protected BufferedReader reader;

    public InputValidatorBase(BufferedReader reader) {
        this.reader = reader;
    }

    public abstract int getAge() throws IOException;
    public abstract String getGender();
    public abstract String getPolicyType();
    public abstract String getIncidentType();
    public abstract String getDamageSeverity();
    public abstract int getClaimAmount() throws IOException;
    public abstract int getSettlementTime() throws IOException;
    public abstract int getSupportingDocs() throws IOException;
    public abstract int getTimeToReport() throws IOException;

    // Helper method for reading input
    protected String readInput() {
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
            return "";
        }
    }
}

// Class for handling input validation logic (Encapsulation & Inheritance)
class InputValidator extends InputValidatorBase {

    public InputValidator(BufferedReader reader) {
        super(reader);
    }

    @Override
    public int getAge() throws IOException {
        int age = 0;
        while (true) {
            try {
                System.out.println("Enter Policyholder Age (e.g. 34): ");
                age = Integer.parseInt(reader.readLine());
                if (age <= 0) {
                    System.out.println("Age must be a positive integer.");
                    continue;
                }
                break; // exit the loop if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for age.");
            }
        }
        return age;
    }

    @Override
    public String getGender() {
        String gender = "";
        while (true) {
            System.out.println("Enter Policyholder Gender (Male/Female): ");
            gender = readInput();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
            }
        }
        return gender;
    }

    @Override
    public String getPolicyType() {
        String policyType = "";
        while (true) {
            System.out.println("Enter Policy Type (Auto/Health/Home): ");
            policyType = readInput();
            if (policyType.equalsIgnoreCase("Auto") || policyType.equalsIgnoreCase("Health") || policyType.equalsIgnoreCase("Home")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Auto', 'Health', or 'Home'.");
            }
        }
        return policyType;
    }

    @Override
    public String getIncidentType() {
        String incidentType = "";
        String[] validCategories = {"Accident", "Illness", "Fire", "Theft"};
        
        while (true) {
            System.out.println("Enter Incident Type (Accident/Illness/Fire/Theft): ");
            incidentType = readInput();
            
            // Check if the entered category is valid
            boolean valid = false;
            for (String validCategory : validCategories) {
                if (incidentType.equalsIgnoreCase(validCategory)) {
                    valid = true;
                    break;
                }
            }
            
            if (valid) {
                break;
            } else {
                System.out.println("Invalid input. Please enter one of the following: Accident, Illness, Fire, Theft.");
            }
        }
        return incidentType;
    }

    @Override
    public String getDamageSeverity() {
        String damageSeverity = "";
        while (true) {
            System.out.println("Enter Damage Severity (High/Medium/Low): ");
            damageSeverity = readInput();
            if (damageSeverity.equalsIgnoreCase("High") || damageSeverity.equalsIgnoreCase("Medium") || damageSeverity.equalsIgnoreCase("Low")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'High', 'Medium', or 'Low'.");
            }
        }
        return damageSeverity;
    }

    @Override
    public int getClaimAmount() throws IOException {
        int claimAmount = 0;
        while (true) {
            try {
                System.out.println("Enter Claim Amount (e.g. 5000): ");
                claimAmount = Integer.parseInt(reader.readLine());
                if (claimAmount <= 0) {
                    System.out.println("Claim Amount must be a positive integer.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Claim Amount.");
            }
        }
        return claimAmount;
    }

    @Override
    public int getSettlementTime() throws IOException {
        int settlementTime = 0;
        while (true) {
            try {
                System.out.println("Enter Settlement Time (e.g. 15): ");
                settlementTime = Integer.parseInt(reader.readLine());
                if (settlementTime <= 0) {
                    System.out.println("Settlement Time must be a positive integer.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Settlement Time.");
            }
        }
        return settlementTime;
    }

    @Override
    public int getSupportingDocs() throws IOException {
        int supportingDocs = 0;
        while (true) {
            try {
                System.out.println("Enter Supporting Docs Provided (1 for Yes, 0 for No): ");
                supportingDocs = Integer.parseInt(reader.readLine());
                if (supportingDocs != 0 && supportingDocs != 1) {
                    System.out.println("Invalid input. Please enter '1' for Yes or '0' for No.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter '1' for Yes or '0' for No.");
            }
        }
        return supportingDocs;
    }

    @Override
    public int getTimeToReport() throws IOException {
        int timeToReport = 0;
        while (true) {
            try {
                System.out.println("Enter Time to Report the Incident (in days): ");
                timeToReport = Integer.parseInt(reader.readLine());
                if (timeToReport <= 0) {
                    System.out.println("Time to Report must be a positive integer.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Time to Report.");
            }
        }
        return timeToReport;
    }
}

// Class for making an API call to detect fraud (Encapsulation, Inheritance, Polymorphism)
class APICommunicator {
    private final String apiUrl = "https://api.example.com/fraud-detection";

    public String sendRequest(FraudInput input) {
        try {
            // Set up the HTTP client and request
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(apiUrl);
            
            // Create JSON object from FraudInput
            Gson gson = new Gson();
            String jsonInput = gson.toJson(input);

            // Set request entity
            StringEntity entity = new StringEntity(jsonInput);
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");

            // Send the request and get the response
            HttpResponse response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);

            return responseString;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error sending API request";
        }
    }
}
