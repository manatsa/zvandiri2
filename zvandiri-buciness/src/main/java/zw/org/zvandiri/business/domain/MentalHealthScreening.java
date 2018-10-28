/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.codehaus.jackson.annotate.JsonIgnore;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.MentalScreenResult;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity
public class MentalHealthScreening extends BaseEntity{
    
    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @Enumerated
    private YesNo screenedForMentalHealth;
    @Enumerated
    private IdentifiedRisk identifiedRisk;
    @ManyToOne
    private ActionTaken actionTaken;
    private String actionTakenText;
    @Enumerated
    private MentalScreenResult mentalScreenResult;
    @ManyToOne
    private ActionTaken rescreenActionTaken;
    @Enumerated
    private IdentifiedRisk rescreenIdentifiedRisk;
    
    public MentalHealthScreening(Patient patient){
        this.patient = patient;
    }

    public MentalHealthScreening() {
    }
    

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public YesNo getScreenedForMentalHealth() {
        return screenedForMentalHealth;
    }

    public void setScreenedForMentalHealth(YesNo screenedForMentalHealth) {
        this.screenedForMentalHealth = screenedForMentalHealth;
    }

    public IdentifiedRisk getIdentifiedRisk() {
        return identifiedRisk;
    }

    public void setIdentifiedRisk(IdentifiedRisk identifiedRisk) {
        this.identifiedRisk = identifiedRisk;
    }

    public ActionTaken getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(ActionTaken actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getActionTakenText() {
        return actionTakenText;
    }

    public void setActionTakenText(String actionTakenText) {
        this.actionTakenText = actionTakenText;
    }

    public MentalScreenResult getMentalScreenResult() {
        return mentalScreenResult;
    }

    public void setMentalScreenResult(MentalScreenResult mentalScreenResult) {
        this.mentalScreenResult = mentalScreenResult;
    }

    public ActionTaken getRescreenActionTaken() {
        return rescreenActionTaken;
    }

    public void setRescreenActionTaken(ActionTaken rescreenActionTaken) {
        this.rescreenActionTaken = rescreenActionTaken;
    }

    public IdentifiedRisk getRescreenIdentifiedRisk() {
        return rescreenIdentifiedRisk;
    }

    public void setRescreenIdentifiedRisk(IdentifiedRisk rescreenIdentifiedRisk) {
        this.rescreenIdentifiedRisk = rescreenIdentifiedRisk;
    }
}
