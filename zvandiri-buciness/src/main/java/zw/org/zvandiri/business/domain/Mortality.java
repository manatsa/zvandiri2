/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import java.util.Date;
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
public class Mortality extends BaseEntity{
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfDeath;
    @Enumerated
    private CauseOfDeath causeOfDeath;
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
    private String zm;
    private String others;
    private String learningPoints;
    private String actionPlan;
    @ManyToOne
    private Patient patient;

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

    public String getZm() {
        return zm;
    }

    public void setZm(String zm) {
        this.zm = zm;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
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
