/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbScreening;

/**
 *
 * @author tasu
 */
public interface TbScreeningRepo extends AbstractRepo<TbScreening, String>{
    
    public List<TbScreening> findByPatientOrderByDateCreatedDesc(Patient patient);
}
