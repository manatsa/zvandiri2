/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author tasu
 */
public interface MentalHealthScreeningRepo extends AbstractRepo<MentalHealthScreening, String>{
    @Query("from MentalHealthScreening s left join fetch s.patient left join fetch s.modifiedBy left join fetch s.createdBy where s.patient=:patient")
    //public MentalHealthScreening findByPatient(@Param("patient") Patient patient);
    List<MentalHealthScreening> findByPatient(@Param("patient") Patient patient);

    @Query("from MentalHealthScreening c left join fetch c.patient where c.patient=:patient and (c.dateCreated between :start_date and :end_date)")
    public List<MentalHealthScreening> findByPatientAndDateCreated(@Param("patient") Patient patient, @Param("start_date") Date start_date, @Param("end_date") Date end_date);

}
