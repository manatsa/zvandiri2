/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tasu
 */
public interface TbIptRepo extends AbstractRepo<TbIpt, String> {

    @Query("from TbIpt s left join fetch s.patient left join fetch s.modifiedBy left join fetch s.createdBy where s.patient=:patient")
    public List<TbIpt> findByPatient(@Param("patient") Patient patient);
    
    @Query("Select count(s) from TbIpt s where s.patient=:patient and s.tbIdentificationOutcome=:tbIdentificationOutcome")
    public int existsByPatientAndTbIdentificationOutcome(@Param("patient") Patient patient, @Param("tbIdentificationOutcome") TbIdentificationOutcome tbIdentificationOutcome);

    @Query("from TbIpt c left join fetch c.patient where c.patient=:patient and (c.dateCreated between :start_date and :end_date)")
    public List<TbIpt> findByPatientAndDateCreated(@Param("patient") Patient patient, @Param("start_date") Date start_date, @Param("end_date") Date end_date);


}
