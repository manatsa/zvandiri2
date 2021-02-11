/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.PatientDisability;

/**
 *
 * @author jmuzinda
 */
public interface DisabilityRepo  extends AbstractRepo<PatientDisability, String> {
    
    // disabilityCategory
    
    @Query("from PatientDisability d left join fetch d.patient left join fetch d.disabilityCategory left join fetch d.modifiedBy left join fetch d.createdBy where d.patient=:patient")
    public List<PatientDisability> findByPatient(@Param("patient") Patient patient);
    
    @Query("from PatientDisability d left join fetch d.patient left join fetch d.disabilityCategory left join fetch d.modifiedBy left join fetch d.createdBy where d.patient=:patient and d.disabilityCategory=:disabilityCategory")
    public PatientDisability findByPatientAndDisabilityCategory(@Param("patient") Patient patient,@Param("disabilityCategory") DisabilityCategory disabilityCategory);   
}
