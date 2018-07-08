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
package zw.org.zvandiri.portal.web.controller;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.CD4Count;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.ViralLoad;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.ArvHistService;
import zw.org.zvandiri.business.service.CD4CountService;
import zw.org.zvandiri.business.service.InvestigationTestService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.ViralLoadService;
import zw.org.zvandiri.report.api.service.LocalityImporterService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/import")
public class ImportLocalitiesController {

    @Resource
    private LocalityImporterService localityImporterService;

    @Resource
    private PatientService patientService;

    @Resource
    private ArvHistService arvHistService;
    
    @Resource
    private InvestigationTestService investigationTestService;
    
    @Resource
    private CD4CountService cd4CountService;
    
    @Resource
    private ViralLoadService viralLoadService;

    @RequestMapping("/update")
    public void updateLocalities() throws FileNotFoundException, ParseException {
        localityImporterService.importData("/home/jmuzinda/localities.csv");
    }

    @RequestMapping("/update-arv")
    public void updatePatientArvHist() {

        for (Patient patient : patientService.getAll()) {
            List<ArvHist> hists = arvHistService.getByPatient(patient);
            if (hists.isEmpty()) {
                continue;
            }
            int count = 0;
            ArvHist firstRecord = null;
            for (Iterator<ArvHist> arvHistIterator = hists.iterator(); arvHistIterator.hasNext();) {
                if (count == 0) {
                    firstRecord = arvHistIterator.next();
                } else if (count == 1 && firstRecord != null) {
                    ArvHist secondRecord = arvHistIterator.next();
                    firstRecord.setArvMedicine2(secondRecord.getArvMedicine());
                    arvHistService.delete(secondRecord);
                } else if (count == 2 && firstRecord != null) {
                    ArvHist thirdRecord = arvHistIterator.next();
                    firstRecord.setArvMedicine3(thirdRecord.getArvMedicine());
                    arvHistService.delete(thirdRecord);
                }
                count++;
            }
            arvHistService.save(firstRecord);
        }
    }
    
    @RequestMapping("/update-patient-tests")
    public void updatePatientTests() {

        for (CD4Count item : cd4CountService.getAll()) {
        	InvestigationTest test = new InvestigationTest();
        	test.setActive(item.getActive());
        	test.setCreatedBy(item.getCreatedBy());
        	test.setDateCreated(item.getDateCreated());
        	test.setDateModified(item.getDateModified());
        	test.setDateTaken(item.getDateTaken());
        	test.setDeleted(item.getDeleted());
        	test.setId(item.getId());
        	test.setModifiedBy(item.getModifiedBy());
        	test.setNextTestDate(item.getNextTestDate());
        	test.setPatient(item.getPatient());
        	test.setResult(item.getResult());
        	test.setSource(item.getSource());
        	test.setTestType(TestType.CD4_COUNT);
        	test.setTnd(item.getTnd());
        	test.setUuid(item.getUuid());
        	test.setVersion(item.getVersion());
        	investigationTestService.save(test);
        }
        
        for (ViralLoad item : viralLoadService.getAll()) {
        	InvestigationTest test = new InvestigationTest();
        	test.setActive(item.getActive());
        	test.setCreatedBy(item.getCreatedBy());
        	test.setDateCreated(item.getDateCreated());
        	test.setDateModified(item.getDateModified());
        	test.setDateTaken(item.getDateTaken());
        	test.setDeleted(item.getDeleted());
        	test.setId(item.getId());
        	test.setModifiedBy(item.getModifiedBy());
        	test.setNextTestDate(item.getNextTestDate());
        	test.setPatient(item.getPatient());
        	test.setResult(item.getResult());
        	test.setSource(item.getSource());
        	test.setTestType(TestType.VITRAL_LOAD);
        	test.setTnd(item.getTnd());
        	test.setUuid(item.getUuid());
        	test.setVersion(item.getVersion());
        	investigationTestService.save(test);
        }
    }
}
