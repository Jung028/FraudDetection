// package com.example;


// import org.apache.http.HttpEntity;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.client.HttpClient;
// import org.apache.http.entity.StringEntity;
// import org.apache.http.util.EntityUtils;
// import com.google.gson.Gson;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.IOException;


// public class Main2 {
//     public static void main(String[] args) throws IOException{
//         // Buffered reader for user input 
//         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//         // Creating an instance of InputValidator to handle input validation 
//         InputValidator inputValidator = new InputValidator(reader);

//         // Collecting user input with validation 
//         int age = inputValidator.getAge();
//         String gender = inputValidator.getGender();
//         String policyType = inputValidator.getPolicyType();
//         String incidentType = inputValidator.getIncidentType();
//         String damageSeverity = inputValidator.getDamageSeverity();
//         int claimAmount = inputValidator.getClaimAmount();
//         int settlementTime = inputValidator.getSettlementTime();
//         int supportingDocs = inputValidator.getSupportingDocs();
//         int timeToReport = inputValidator.getTimeToReport();

//         // Create an instance of FraudInput to store the date 
//         FraudInput input = new FraudInput(age, gender, policyType, incidentType, damageSeverity, claimAmount, settlementTime, supportingDocs, timeToReport );

//         // Create an instance of APICommunicator to handle sending the date and receiving the response
//         APICommunicator apiCommunicator = new APICommunicator();
//         String response = apiCommunicator.sendRequest(input);

//         // Print the response (prediction result)
//         System.out.println("Response from API: " + response);

//     }
// }

// // class to hold user input data 

// class FraudInput {
//     private int PolicyholderAge;
//     private String PolicyholderGender;
//     private String PolicyType;
//     private String IncidentType;
//     private String DamageSeverity;
//     private int ClaimAmount;
//     private int SettlementTime;
//     private int SupportingDocsProvided;
//     private int TimeToReport;

//     // Constructor
//     public FraudInput(int PolicyholderAge, String PolicyholderGender, String PolicyType, 
//                       String IncidentType, String DamageSeverity, int ClaimAmount, int SettlementTime, 
//                       int SupportingDocsProvided, int TimeToReport) {

//         this.PolicyholderAge = PolicyholderAge;
//         this.PolicyholderGender = PolicyholderGender;
//         this.PolicyType = PolicyType;
//         this.IncidentType = IncidentType; 
//         this.DamageSeverity = DamageSeverity;
//         this.ClaimAmount = ClaimAmount;
//         this.SettlementTime = SettlementTime;
//         this.SupportingDocsProvided = SupportingDocsProvided;
//         this.TimeToReport = TimeToReport;



//     }

//     public int PolicyholderAge() { 
//         return PolicyholderAge;
//     }

//     public String PolicyholderGender() {
//         return PolicyholderGender;
//     }
//     public String getPolicyType() { return PolicyType; }
//     public String getIncidentType() { return IncidentType; }
//     public String getDamageSeverity() { return DamageSeverity; }
//     public int getClaimAmount() { return ClaimAmount; }
//     public int getSettlementTime() { return SettlementTime; }
//     public int getSupportingDocsProvided() { return SupportingDocsProvided; }
//     public int getTimeToReport() { return TimeToReport; }
// }


// abstract class InputValidatorBase { 
//     protected BufferedReader reader;

//     public InputValidatorBase(BufferedReader reader) { 
//         this.reader = reader;
//     }

//     public abstract int getAge() throws IOException;
//     public abstract String getGender();
//     public abstract String getPolicyType();
//     public abstract String getIncidentType();
//     public abstract String getDamageSeverity();
//     public abstract int getClaimAmount() throws IOException;
//     public abstract int getSettlementTime() throws IOException;
//     public abstract int getSupportingDocs() throws IOException;
//     public abstract int getTimeToReport() throws IOException;

//     // helper method for reading input 
//     protected String readInput() {
//         try { 
//             return reader.readLine().trim();
//         } catch (IOException e){ 
//             System.out.println("Error reading output : " + e.getMessage());
//             return "";
//         }
//     }
// }


// // class to handle input validation logic 
// class InputValidator extends InputValidatorBase { 
//     public InputValidator(BufferedReader reader) {
//         super(reader);
//     }
//     @Override 
//     public int getAge() throws IOException { 
//         int age = 0 ; 
//         while(true){ 
//             try{ 
//                 System.out.println("Enter age : ");
//                 age = Integer.parseInt(reader.readLine()); // replaces age with the user's valid input 
//                 if (age <=0){
//                     System.out.println("Age must be a positive integer");
//                     continue;
//                 }
//                 break;
//             } catch (NumberFormatException e){ 
//                 System.out.println("Invalid input. Please enter a valid integer for age");
//             }
        
//         }
//         return age;
//     }

//     @Override 
//     public String getGender() {
//         String gender = "";
//         while(true) {
//             System.out.println("Enter gender : ");
//             gender = readInput();
//             if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
//                 break;
//             } else { 
//                 System.out.println("Please enter a valid gender Male or Female");
//             }  
//         }
//         return gender;
//     }

//     @Override
//     public String getPolicyType() {
//         String policyType = "";
//         while(true) { 
//             System.out.println("Enter policy type : ");
//             policyType = readInput();
//             if (policyType.equalsIgnoreCase("Auto") || policyType.equalsIgnoreCase("Health") || policyType.equalsIgnoreCase("Home")){
//                 break;
//             } else { 
//                 System.out.println("Please enter a valid policy type :");
//             }
//         }
//         return policyType;
//     }

//     @Override 
//     public String getIncidentType() { 
//         String incidentType = "";
//         String[] validCategories = {"Accident", "Illness", "Fire", "Theft"};

//         while(true) { 
//             System.out.println("Enter incident type : ");
//             incidentType = readInput();
            
//             boolean valid = false;
//             for (String validCategory : validCategories) {
//                 if (incidentType.equalsIgnoreCase(validCategory)){
//                     valid = true;
//                     break;
//                 }
//             }

//             if (valid) { 
//                 break;
//             } else { 
//                 System.out.println("Enter a valid input ");
//             }
//         }
//         return incidentType;
//     }

//     public String getDamageSeverity() { 
//         String damageSeverity = "";
//         while (true) { 
//             System.out.println("Enter the damage severity (High/Medium/Low) : ");
//             damageSeverity = readInput();
//             if (damageSeverity.equalsIgnoreCase("High") || damageSeverity.equalsIgnoreCase("Medium") || damageSeverity.equalsIgnoreCase("Low")){
//                 break;
//             } else { 
//                 System.out.println("Please enter a valid input");
//             }

//         }
//         return damageSeverity;
//     }


//     public int getClaimAmount() throws IOException { 
//         int claimAmount = 0;
//         while (true) { 
//             try { 
//                 System.out.println("Please enter the claim amount : ");
//                 claimAmount = Integer.parseInt(reader.readLine());
//                 if (claimAmount <= 0 ) { 
//                     System.out.println("Enter a positive value");
//                     continue;
//                 }
//                 break;

//             } catch(NumberFormatException e) {
//                 System.out.println("Please enter a valid integer value");

//             }
//         }
//         return claimAmount;
//     }

//     public int getSettlementTime() throws IOException { 
//         int settlementTime = 0 ;
//         while(true) { 
//             try { 
//                 System.out.println("Enter the settlement time : ");
//                 settlementTime = Integer.parseInt(reader.readLine());
//                 if (settlementTime <= 0 ) { 
//                     System.out.println("please enter a time more than 0 ");
//                     continue;
//                 }
//                 break;
//             } catch(NumberFormatException e) { 
//                 System.out.println("Please enter a valid input ");

//             }
//         }
//         return settlementTime;
//     }
//     public int getSupportingDocs() throws IOException { 
//         int supportingDocs = 0 ; 
//         while (true) { 
//             try { 
//                 System.out.println("Enter 1 or 0 if the supporting docs are provided or not: ");
//                 supportingDocs = Integer.parseInt(reader.readLine());
//                 if (supportingDocs <= 0 && supportingDocs != 1) { 
//                     System.out.println("Please enter a valid integer");
//                     continue;
//                 }
//                 break;
//             } catch(NumberFormatException e ) { 
//                 System.out.println("Please enter an integer");
//             }
//         }
//         return supportingDocs;
//     }

//     public int getTimeToReport() throws IOException { 
//         int timeToReport = 0 ; 
//         while (true) { 
//             try { 
//                 System.out.println("Enter time the report is submitted in days: ");
//                 timeToReport = Integer.parseInt(reader.readLine());
//                 if ( timeToReport <= 0 ) {
//                     System.out.println("Please enter a positve time");
//                     continue;
//                 }
//                 break;

//             } catch (NumberFormatException e){ 
//                 System.out.println("Please enter a valid time");
//             }
//         }
//         return timeToReport;
//     }
// }



