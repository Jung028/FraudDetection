package com.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Claim {
    private String claimId;
    private String policyNumber;
    private int policyholderAge;
    private String policyholderGender;
    private String policyType;
    private String incidentType;
    private LocalDate incidentDate;
    private LocalDate reportedDate;
    private String location;
    private String damageSeverity;
    private double claimAmount;
    private int settlementTime;
    private boolean supportingDocsProvided;

    // Constructor
    public Claim(String claimId, String policyNumber, int policyholderAge, String policyholderGender,
                 String policyType, String incidentType, LocalDate incidentDate, LocalDate reportedDate,
                 String location, String damageSeverity, double claimAmount, int settlementTime, boolean supportingDocsProvided) {
        this.claimId = claimId;
        this.policyNumber = policyNumber;
        this.policyholderAge = policyholderAge;
        this.policyholderGender = policyholderGender;
        this.policyType = policyType;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.reportedDate = reportedDate;
        this.location = location;
        this.damageSeverity = damageSeverity;
        this.claimAmount = claimAmount;
        this.settlementTime = settlementTime;
        this.supportingDocsProvided = supportingDocsProvided;
    }

    // Encapsulated Getters
    public String getClaimId() {
        return claimId;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public int getPolicyholderAge() {
        return policyholderAge;
    }

    public long getTimeToReport() {
        return ChronoUnit.DAYS.between(incidentDate, reportedDate);
    }

    public String getPolicyType() {
        return policyType;
    }

    public String getPolicyholderGender() {
        return policyholderGender;
    }

    public String getDamageSeverity() {
        return damageSeverity;
    }

    public int getSettlementTime() {
        return settlementTime;
    }

    public boolean isSupportingDocsProvided() {
        return supportingDocsProvided;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public LocalDate getReportedDate() {
        return reportedDate;
    }

    public String getLocation() {
        return location;
    }

    // Updated toString method to include all fields
    @Override
    public String toString() {
        return "Claim{" +
                "claimId='" + claimId + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", policyholderAge=" + policyholderAge +
                ", policyholderGender='" + policyholderGender + '\'' +
                ", policyType='" + policyType + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", incidentDate=" + incidentDate +
                ", reportedDate=" + reportedDate +
                ", location='" + location + '\'' +
                ", damageSeverity='" + damageSeverity + '\'' +
                ", claimAmount=" + claimAmount +
                ", settlementTime=" + settlementTime +
                ", supportingDocsProvided=" + supportingDocsProvided +
                '}';
    }
}
