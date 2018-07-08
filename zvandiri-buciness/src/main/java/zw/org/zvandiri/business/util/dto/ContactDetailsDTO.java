/*
 * Copyright 2016 Judge Muzinda.
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
package zw.org.zvandiri.business.util.dto;

import java.io.Serializable;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author Judge Muzinda
 */
public class ContactDetailsDTO implements Serializable {
    
    private Patient patient;
    private String address;
    private String mobileNumber;
    private String email;

    public ContactDetailsDTO() {
    }

    public ContactDetailsDTO(Patient patient) {
        this.patient = patient;
        this.address = patient.getAddress();
        this.mobileNumber = patient.getMobileNumber();
        this.email = patient.getEmail();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Patient getInstance(ContactDetailsDTO dto){
        Patient p = dto.getPatient();
        p.setEmail(email);
        p.setAddress(address);
        p.setMobileNumber(mobileNumber);
        return p;
    }
}