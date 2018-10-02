/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.codehaus.jackson.annotate.JsonIgnore;

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
    
}
