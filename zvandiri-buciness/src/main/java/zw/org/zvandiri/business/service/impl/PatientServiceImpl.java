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
package zw.org.zvandiri.business.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.repo.PatientRepo;
import zw.org.zvandiri.business.service.CatDetailService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;
import zw.org.zvandiri.business.util.dto.PatientDuplicateDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author Judge Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PatientServiceImpl implements PatientService {

    @Resource
    private PatientRepo patientRepo;
    @Resource
    private UserService userService;
    @Resource
    private CatDetailService catDetailService;

    @Override
    public List<Patient> getAll() {
        return patientRepo.findByActive(Boolean.TRUE);
    }

    @Override
    public Patient get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item to be does not exist :" + id);
        }
        return patientRepo.findOne(id);
    }

    @Override
    public void delete(Patient t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        patientRepo.delete(t);
    }

    @Override
    public List<Patient> getPageable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public Patient save(Patient t) {
        if (t.getId() == null || StringUtils.isBlank(t.getId())) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return patientRepo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return patientRepo.save(t);

    }

    @Override
    public Boolean checkDuplicate(Patient current, Patient old) {
        List<Patient> patients = checkPatientDuplicate(current);
        return checkItemDuplicate(current, patients);
    }

    private Boolean checkItemDuplicate(Patient current, List<Patient> patients) {
        if (current.getId() == null) {
            if (patients != null && !patients.isEmpty()) {
                return true;
            }
        } else if (current.getId() != null) {
            if ((patients != null && !patients.isEmpty()) && patients.size() > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Patient> checkPatientDuplicate(Patient patient) {
        String firstName = getSubString(patient.getFirstName());
        String latName = getSubString(patient.getLastName());
        Date start = DateUtil.getDateDiffMonth(patient.getDateOfBirth(), -6);
        Date end = DateUtil.getDateDiffMonth(patient.getDateOfBirth(), 6);
        return patientRepo.checkPatientDuplicate(firstName, latName, start, end, patient.getPrimaryClinic());
    }

    private String getSubString(String name) {
        return name.length() > 4 ? name.substring(0, 3) : name;
    }

    @Override
    public List<Patient> getByCat(Boolean cat) {
        return patientRepo.findByCatAndActive(cat, Boolean.TRUE);
    }

    @Override
    public List<Patient> search(String... exp) {
        if (exp == null) {
            throw new IllegalArgumentException("Provide parameter for search");
        } else if (exp.length == 1) {
            return patientRepo.findByFirstNameOrLastName(exp[0], Boolean.TRUE);
        }
        return patientRepo.findByFirstNameAndLastName(exp[0], exp[1], Boolean.TRUE);
    }

    @Override
    public List<Patient> getByEmail(Patient patient) {
        return patientRepo.findByEmail(patient.getEmail());
    }

    @Override
    public Boolean checkDuplicateEmail(Patient current, Patient old) {
        List<Patient> patients = getByEmail(current);
        return checkItemDuplicate(current, patients);
    }

    @Override
    public Boolean hasCatDetailRecord(Patient patient) {
        if (patient.getCat() != null && patient.getCat().equals(YesNo.YES) && catDetailService.getByPatient(patient) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<Patient> getYoungMothers(SearchDTO dto, String... exp) {
        if (exp == null) {
            throw new IllegalArgumentException("Provide parameter for search");
        } else if (exp.length == 1) {
            return patientRepo.findYoungMothersByName(DateUtil.getDateFromAge(40), DateUtil.getDateFromAge(8), dto.getDistrict(), dto.getGender(), exp[0]);
        }
        return patientRepo.findYoungMothersByBoth(DateUtil.getDateFromAge(40), DateUtil.getDateFromAge(8), dto.getDistrict(), dto.getGender(), exp[0], exp[1]);
    }

    @Override
    public Set<PatientDuplicateDTO> getAllPossibleDuplicates(List<Patient> patients) {

        Set<PatientDuplicateDTO> patientsWithPossibleDuplicates = new HashSet<>();
        for (Iterator<Patient> pi = patients.iterator(); pi.hasNext();) {
            Patient currentPatient = pi.next();
            Set<Patient> estDuplicates = new HashSet<>(checkPatientDuplicate(currentPatient));
            estDuplicates.remove(currentPatient);
            if (!estDuplicates.isEmpty()) {
                PatientDuplicateDTO patientWithDuplicates = PatientDuplicateDTO.getInstance(currentPatient);
                patientWithDuplicates.setMatches(PatientDuplicateDTO.getRelatedPatients(estDuplicates));
                patientsWithPossibleDuplicates.add(patientWithDuplicates);
                
            }
            pi.remove();
        }
        return patientsWithPossibleDuplicates;
    }

    @Override
    @Transactional
    public void mergePatients(String patientId, String patientToBeMergedId) {
        // ignore merging fields only merge associated data
        // its assummed that the associated data will not be duplicated, otherwise tough luck
        Patient patient = patientRepo.getPatient(patientId);
        Patient patientToBeMerged = patientRepo.getPatient(patientToBeMergedId);
        patient.getDisabilityCategorys().addAll(patientToBeMerged.getDisabilityCategorys());
        patient.getDependents().addAll(patientToBeMerged.getDependents());
        patient.getMedicalHists().addAll(patientToBeMerged.getMedicalHists());
        patient.getChronicInfectionItems().addAll(patientToBeMerged.getChronicInfectionItems());
        patient.getHivConInfectionItems().addAll(patientToBeMerged.getHivConInfectionItems());
        patient.getArvHists().addAll(patientToBeMerged.getArvHists());
        patient.getMentalHealthItems().addAll(patientToBeMerged.getMentalHealthItems());
        patient.getSrhHists().addAll(patientToBeMerged.getSrhHists());
        patient.getObstercHists().addAll(patientToBeMerged.getObstercHists());
        patient.getSocialHists().addAll(patientToBeMerged.getSocialHists());
        patient.getSubstanceItems().addAll(patientToBeMerged.getSubstanceItems());
        patient.getFamilys().addAll(patientToBeMerged.getFamilys());
        patient.getcD4Counts().addAll(patientToBeMerged.getcD4Counts());
        patient.getContacts().addAll(patientToBeMerged.getContacts());
        patient.getViralLoads().addAll(patientToBeMerged.getViralLoads());
        patient.getEidTests().addAll(patientToBeMerged.getEidTests());
        save(patient);
        // remove patient history
        delete(patientToBeMerged);
    }

}