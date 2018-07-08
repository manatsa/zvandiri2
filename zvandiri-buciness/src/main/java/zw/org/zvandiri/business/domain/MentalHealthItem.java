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
package zw.org.zvandiri.business.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author Judge Muzinda
 */
@Entity
public class MentalHealthItem extends BaseEntity {
    
    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private MentalHealth mentalHealth;
    private String past;
    private String current;
    @Enumerated
    private YesNo receivedProfessionalHelp;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private Date profHelpStart;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private Date profHelpEnd;
    private String medication;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private Date endDate;
    @Enumerated
    private YesNo beenHospitalized;
    @Column(columnDefinition = "text")
    private String mentalHistText;

    public MentalHealthItem() {
    }

    public MentalHealthItem(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public MentalHealth getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(MentalHealth mentalHealth) {
        this.mentalHealth = mentalHealth;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public YesNo getReceivedProfessionalHelp() {
        return receivedProfessionalHelp;
    }

    public void setReceivedProfessionalHelp(YesNo receivedProfessionalHelp) {
        this.receivedProfessionalHelp = receivedProfessionalHelp;
    }

    public Date getProfHelpStart() {
        return profHelpStart;
    }

    public void setProfHelpStart(Date profHelpStart) {
        this.profHelpStart = profHelpStart;
    }

    public Date getProfHelpEnd() {
        return profHelpEnd;
    }

    public void setProfHelpEnd(Date profHelpEnd) {
        this.profHelpEnd = profHelpEnd;
    }

    public YesNo getBeenHospitalized() {
        return beenHospitalized;
    }

    public void setBeenHospitalized(YesNo beenHospitalized) {
        this.beenHospitalized = beenHospitalized;
    }

    public String getMentalHistText() {
        return mentalHistText;
    }

    public void setMentalHistText(String mentalHistText) {
        this.mentalHistText = mentalHistText;
    }
    
}