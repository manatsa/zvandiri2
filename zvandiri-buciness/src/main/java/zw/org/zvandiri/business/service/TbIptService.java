/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.service;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;

/**
 *
 * @author tasu
 */
public interface TbIptService extends GenericPatientHistoryService<TbIpt>{
    
    public boolean existsOnTbTreatment(Patient patient, TbIdentificationOutcome yesNo);
    
}
