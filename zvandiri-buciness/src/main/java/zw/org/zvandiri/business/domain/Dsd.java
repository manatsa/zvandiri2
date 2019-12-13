/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity
public class Dsd extends BaseEntity{
    
    @ManyToOne
    private Patient patient;
    @Enumerated
    private YesNo fargs;
    @Enumerated
    private YesNo cargs;
    @Enumerated
    private YesNo fastTrack;
    @Enumerated
    private YesNo artRefillClub;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public YesNo getFargs() {
        return fargs;
    }

    public void setFargs(YesNo fargs) {
        this.fargs = fargs;
    }

    public YesNo getCargs() {
        return cargs;
    }

    public void setCargs(YesNo cargs) {
        this.cargs = cargs;
    }

    public YesNo getFastTrack() {
        return fastTrack;
    }

    public void setFastTrack(YesNo fastTrack) {
        this.fastTrack = fastTrack;
    }

    public YesNo getArtRefillClub() {
        return artRefillClub;
    }

    public void setArtRefillClub(YesNo artRefillClub) {
        this.artRefillClub = artRefillClub;
    }
    
}
