/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.service;

import java.util.List;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author tasu
 */
public interface MentalHealthScreeningService extends GenericPatientHistoryService<MentalHealthScreening>{
    List<MentalHealthScreening> findByPatient(Patient patient);
}
