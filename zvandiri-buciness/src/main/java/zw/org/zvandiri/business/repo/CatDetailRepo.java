/*
 * Copyright 2016 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author Judge Muzinda
 */
public interface CatDetailRepo extends AbstractRepo<CatDetail, String> {
    
    @Query("from CatDetail c left join fetch c.patient left join fetch c.primaryClinic where c.patient=:patient")
    public CatDetail findByPatient(@Param("patient") Patient patient);
   
    @Query("from CatDetail c left join fetch c.patient left join fetch c.primaryClinic left join fetch c.createdBy left join c.modifiedBy where c.email=:email")
    public CatDetail findByEmail(@Param("email") String email);
    
    @Query("from CatDetail c left join fetch c.patient left join fetch c.primaryClinic where c.id=:id")
    public CatDetail findById(@Param("id") String id);
    
    @Query("from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.primaryClinic =:primaryClinic and p <> :patient order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByPrimaryClinic(@Param("primaryClinic") Facility primaryClinic, @Param("patient") Patient patient);
    
    List<Patient> findByPrimaryClinic(Facility primaryClinic);
}