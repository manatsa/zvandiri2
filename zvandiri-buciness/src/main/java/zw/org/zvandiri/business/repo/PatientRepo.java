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

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.util.PatientInnerJoin;

/**
 *
 * @author Judge Muzinda
 */
public interface PatientRepo extends AbstractRepo<Patient, String> {
    
    @Query("from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup  left join fetch p.disabilityCategorys left join fetch p.relationship left join fetch p.mobileOwnerRelation left join fetch p.secondaryMobileownerRelation left join fetch p.motherOfHei where p.active=:active order by p.lastName, p.firstName, p.middleName ASC")
    @Override
    public List<Patient> findByActive(@Param("active") Boolean active);
    
    @Query("from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.cat=:cat and p.active=:active order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByCatAndActive(@Param("cat") Boolean cat, @Param("active") Boolean active);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.active=:active and (p.firstName Like :name% or p.lastName Like :name%) order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameOrLastName(@Param("name") String name, @Param("active") Boolean active);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.active=:active and (p.firstName Like :name% or p.lastName Like :name%) and p.primaryClinic.district.province=:province order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameOrLastNameAndProvince(@Param("name") String name, @Param("active") Boolean active, @Param("province") Province province);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.active=:active and (p.firstName Like :name% or p.lastName Like :name%) and p.primaryClinic.district=:district order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameOrLastNameAndDistrict(@Param("name") String name, @Param("active") Boolean active, @Param("district") District district);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup  where p.active=:active and ((p.firstName Like :first% or p.firstName Like :last%) and (p.lastName Like :last% or p.lastName Like :first%) ) order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameAndLastName(@Param("first") String first, @Param("last") String last, @Param("active") Boolean active);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup  where p.active=:active and ((p.firstName Like :first% or p.firstName Like :last%) and (p.lastName Like :last% or p.lastName Like :first%) ) and p.primaryClinic.district.province=:province order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameAndLastNameAndProvince(@Param("first") String first, @Param("last") String last, @Param("active") Boolean active, @Param("province") Province province);
    
    @Query("Select Distinct p from Patient p left join fetch p.createdBy left join fetch p.modifiedBy left join fetch p.disabilityCategorys left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup  where p.active=:active and ((p.firstName Like :first% or p.firstName Like :last%) and (p.lastName Like :last% or p.lastName Like :first%) ) and p.primaryClinic.district=:district order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findByFirstNameAndLastNameAndDistrict(@Param("first") String first, @Param("last") String last, @Param("active") Boolean active, @Param("district") District district);
    
    @Query("from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where p.firstName=:firstName and p.lastName=:lastName and p.dateOfBirth=:dateOfBirth and p.gender=:gender")
    public Patient findByFirstNameANdLastNameAndDateOfBirthAndGender(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("dateOfBirth") Date dateOfBirth, @Param("gender") Gender gender);
    
    @Query("Select Distinct p from Patient p where (p.firstName like :firstName% and p.lastName like :lastName%) and (p.dateOfBirth between :start and :end) and p.primaryClinic=:primaryClinic and (p.firstName like %:firstNameLastPart and p.lastName like %:lastNameLastPart)")
    public List<Patient> checkPatientDuplicate(
            @Param("firstName") String firstName, 
            @Param("lastName") String lastName, 
            @Param("start") Date start, @Param("end") Date end, 
            @Param("primaryClinic") Facility primaryClinic,
            @Param("firstNameLastPart") String firstNameLastPart, 
            @Param("lastNameLastPart") String lastNameLastPart);
    
    @Query("Select Distinct p from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where (p.dateOfBirth between :start and :end) and p.primaryClinic.district=:district and p.gender=:gender and (p.firstName Like :name% or p.lastName Like :name%) order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findYoungMothersByName(
            @Param("start") Date start, @Param("end") Date end, 
            @Param("district") District district, 
            @Param("gender") Gender gender,
            @Param("name") String name);
    
    @Query("Select Distinct p from Patient p left join fetch p.education left join fetch p.educationLevel left join fetch p.referer left join fetch p.primaryClinic left join fetch p.supportGroup where (p.dateOfBirth between :start and :end) and p.primaryClinic.district=:district and p.gender=:gender and ((p.firstName Like :first% or p.firstName Like :last%) and (p.lastName Like :last% or p.lastName Like :first%) ) order by p.lastName, p.firstName, p.middleName ASC")
    public List<Patient> findYoungMothersByBoth(
            @Param("start") Date start, @Param("end") Date end, 
            @Param("district") District district, 
            @Param("gender") Gender gender,
            @Param("first") String first, @Param("last") String last);
    
    @Query("Select Distinct p from Patient p "+PatientInnerJoin.PATIENT_FULL_ASSOC_FETCH+" where p.id=:id")
    public Patient getPatient(@Param("id") String id);
}