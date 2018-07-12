/*
 * Copyright 2017 jmuzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.report.api;

/**
 *
 * @author jmuzinda
 */
public interface DatabaseHeader {
    
    public final String [] PATIENT_HEADER = {
     "ID","Name", "OI/ Art Number", "Date of Birth","Date Joined", "Gender", "Address", "Mobile Number", "Email", 
        "Consent To Photo", "Consent To M-Health","Education", "Highest Education",
        "Refer", "Region", "District","Primary Clinic", "Support Group", "Date Tested","HIV Disclosure Location",
        "Has Disability", "IS CATS", "Is In Young Mum Group", "HIV Transmission Mode",
        "HIV Status Known", "Patient Status", "Date Status Changed"
    };
    
    public final String [] DEPENDANT_HEADER = {
        "ID", "Patient Name", "Name", "Region", "District","Primary Clinic", "Gender", "Date Of Birth", "HIV Status"
    };
    
    public final String [] OPPORTUNISTIC_INFECTION_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Infection", "Date Diagnosed", "Medication", "Current Status"
    };
    
    public final String [] HIV_CO_INFECTION_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Infection", "Date Diagnosed", "Medication", "Resolution"
    };
    
    public final String [] MENTAL_HIST_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Mental Health", "Past", "Current", 
        "Received Professional Help", "Professional Help Start Date",
        "Professional Help End Date", "Medication", "Medication Start Date",
        "Medication End Date", "Been Hospitalized", "Description"
    };
    
    public final String [] OBSTERIC_HIST_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Is Pregnant", "Currently Breast Feeding", 
        "Currently Pregnant", "Number of ANC Visits", "Gestational Age of First Pregnancy", "Art Started", "Number of Children"
    };
    
    public final String [] CONTACT_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Contact Date", "Care Level", "Location", "Position",
        "Reason", "Followup", "Subjective", "Objective", "Assessment", "Plan", "Action Taken",
        "Last Clinic Appointmet Date", "Attended Clinic Appointment"
    };
    
    public final String [] SOCIAL_HISTORY_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Live With", "Relationship", "Abused", "Disclosed Abuse",
        "Feel Safe Now", "Abuse Type", "Abuse Outcome"
    };
    
    public final String [] SUBSTANCE_USE_HEADER = {
        "ID", "Patient Name","Region", "District","Primary Clinic", "Substance", "Current", "Past", "Start Date", "End Date",
        "Drug Intervention", "Duration"
    };
    
    public final String [] FAMILY_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Orphan Status", "Number of Siblings"
    };
    
    public final String [] REFERRAL_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Referral Date", "Expected Visit Date", "Organisation", 
        "Designation", "Attending Officer", "Date Attended", "Action Taken", 
        "HIV Services Referred", "HIV Services Provided"
            , "HIV & STI Services Referred", "HIV & STI Services Provided", "OI/ ART Services Referred", "OI/ ART Services Provided", "SRH Services Referred", "SRH Services Provided", "Laboratory Services Referred", "Laboratory Services Provided", "TB Services Referred", "TB Services Provided", "Psych Services Referred", "Psych Services Provided", "Legal Services Referred", "Legal Services Provided"
    };
    
    public final String [] CD4_COUNT_HEADER =  {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "Test Type", "Date Taken", 
        "Count", "Source", "Next Lab Due"
    };
    
    public final String [] ARV_HISTORY_HEADER = {
        "ID", "Patient Name", "Region", "District","Primary Clinic", "ARV Medicine", 
        "Start Date", "End Date"
    };
}