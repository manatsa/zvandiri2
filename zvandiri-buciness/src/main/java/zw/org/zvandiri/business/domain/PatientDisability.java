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
import zw.org.zvandiri.business.domain.util.DisabilitySeverity;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author jmuzinda
 */
@Entity
public class PatientDisability extends BaseEntity {
    
    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private DisabilityCategory disabilityCategory;
    @Enumerated
    private DisabilitySeverity severity;
    @Enumerated
    private YesNo screened;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DisabilityCategory getDisabilityCategory() {
        return disabilityCategory;
    }

    public void setDisabilityCategory(DisabilityCategory disabilityCategory) {
        this.disabilityCategory = disabilityCategory;
    }

    public DisabilitySeverity getSeverity() {
        return severity;
    }

    public void setSeverity(DisabilitySeverity severity) {
        this.severity = severity;
    }

    public YesNo getScreened() {
        return screened;
    }

    public void setScreened(YesNo screened) {
        this.screened = screened;
    }
    
    
}
