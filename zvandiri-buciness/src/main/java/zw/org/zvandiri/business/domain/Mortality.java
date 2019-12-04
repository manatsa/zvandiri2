/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.CauseOfDeath;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity
public class Mortality extends BaseEntity {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfDeath;
    @Enumerated
    private CauseOfDeath causeOfDeath;
    private String causeOfDeathDetails;
    @Enumerated
    private YesNo receivingEnhancedCare;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date datePutOnEnhancedCare;
    private String caseBackground;
    private String careProvided;
    private String home;
    private String beneficiary;
    private String facility;
    private String cats;
    private String other;
    private YesNo contactWithZM;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfContactWithZim;
    @Column(columnDefinition = "text")
    private String descriptionOfcase;
    private String learningPoints;
    private String actionPlan;
    @ManyToOne
    private Patient patient;

    public Mortality() {
    }

    public Mortality(Patient patient) {
        this.patient = patient;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(CauseOfDeath causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getCauseOfDeathDetails() {
        return causeOfDeathDetails;
    }

    public void setCauseOfDeathDetails(String causeOfDeathDetails) {
        this.causeOfDeathDetails = causeOfDeathDetails;
    }

    public YesNo getReceivingEnhancedCare() {
        return receivingEnhancedCare;
    }

    public void setReceivingEnhancedCare(YesNo receivingEnhancedCare) {
        this.receivingEnhancedCare = receivingEnhancedCare;
    }

    public Date getDatePutOnEnhancedCare() {
        return datePutOnEnhancedCare;
    }

    public void setDatePutOnEnhancedCare(Date datePutOnEnhancedCare) {
        this.datePutOnEnhancedCare = datePutOnEnhancedCare;
    }

    public String getCaseBackground() {
        return caseBackground;
    }

    public void setCaseBackground(String caseBackground) {
        this.caseBackground = caseBackground;
    }

    public String getCareProvided() {
        return careProvided;
    }

    public void setCareProvided(String careProvided) {
        this.careProvided = careProvided;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getCats() {
        return cats;
    }

    public void setCats(String cats) {
        this.cats = cats;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public YesNo getContactWithZM() {
        return contactWithZM;
    }

    public void setContactWithZM(YesNo contactWithZM) {
        this.contactWithZM = contactWithZM;
    }

    public Date getDateOfContactWithZim() {
        return dateOfContactWithZim;
    }

    public void setDateOfContactWithZim(Date dateOfContactWithZim) {
        this.dateOfContactWithZim = dateOfContactWithZim;
    }

    public String getDescriptionOfcase() {
        return descriptionOfcase;
    }

    public void setDescriptionOfcase(String descriptionOfcase) {
        this.descriptionOfcase = descriptionOfcase;
    }

    public String getLearningPoints() {
        return learningPoints;
    }

    public void setLearningPoints(String learningPoints) {
        this.learningPoints = learningPoints;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
