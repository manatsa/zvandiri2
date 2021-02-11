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
package zw.org.zvandiri.report.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.DetailedReportService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class DetailedReportServiceImpl implements DetailedReportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private ContactReportService contactReportService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        String[] headers = {"Name", "Age", "Date of Birth", "Date Joined", "Gender",  "Viral Load", "CD4 Count",
            "Province", "District", "Primary Clinic", "Support Group", "Mobile Number", "Referer"};
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : detailedPatientReportService.get(dto.getInstance(dto))) {
            String[] inner = {
                item.getName(),
                item.getAge() + "",
                DateUtil.getStringFromDate(item.getDateOfBirth()),
                item.getDateJoin(),
                item.getGender().getName(),
                item.getViralLoad() != null ? item.getViralLoad() + "" : "",
                item.getCd4Count() != null ? item.getCd4Count() + "" : "",
                item.getPrimaryClinic().getDistrict().getProvince().getName(),
                item.getPrimaryClinic().getDistrict().getName(),
                item.getPrimaryClinic().getName(),
                item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                item.getMobileNumber(),
                item.getReferer() != null ? item.getReferer().getName() : ""
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> get(List<Patient> patients) {
        String[] headers = {"Name", "OI/ ART Number", "Age", "Date of Birth","Date Joined", "Gender", "Mode of Transimission", "Disability Status",
            "Current Drug Regimen","Date Started Regimen", "Region", "District", "Primary Clinic", "Support Group", "Referer"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : patients) {
            String[] inner = {
                item.getName(),
                item.getoINumber(),
                item.getAge() + "",
                DateUtil.getStringFromDate(item.getDateOfBirth()),
                    item.getDateJoin(),
                item.getGender().getName(),
                item.getTransmissionMode() != null ? item.getTransmissionMode().getName() : "",
                item.getDisabilityStatus() != null ? item.getDisabilityStatus().getName() : "",
                item.getCurrentArvRegimen(),
                item.getDateStartedRegimen(),
                item.getPrimaryClinic().getDistrict().getProvince().getName(),
                item.getPrimaryClinic().getDistrict().getName(),
                item.getPrimaryClinic().getName(),
                item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                item.getReferer() != null ? item.getReferer().getName() : ""
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDeceased(List<Patient> patients) {
        String[] headers = {"Name", "OI/ ART Number", "Age", "Date of Birth", "Gender"
                , "Region", "District", "Primary Clinic", "Support Group", "Referer", "Date Modified"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : patients) {
            String[] inner = {
                    item.getName(),
                    item.getoINumber(),
                    item.getAge() + "",
                    DateUtil.getStringFromDate(item.getDateOfBirth()),
                    item.getGender().getName(),
                    item.getPrimaryClinic().getDistrict().getProvince().getName(),
                    item.getPrimaryClinic().getDistrict().getName(),
                    item.getPrimaryClinic().getName(),
                    item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                    item.getReferer() != null ? item.getReferer().getName() : "",
                    DateUtil.getStringFromDate(item.getDateModified())
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        String[] headers = {"Name", "Age", "Gender", "Phone No.",
            "District", "Clinic", "Current Care Level", "Contact Date", "Follow Up", "Place of Contact", "New Level of Care", "Contacted By"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Contact item : contactReportService.get(dto.getInstance(dto))) {
            String[] inner = {
                item.getPatient().getName(),
                item.getPatient().getAge() + "",
                item.getPatient().getGender().getName(),
                item.getPatient().getMobileNumber(),
                item.getPatient().getPrimaryClinic().getDistrict().getName(),
                item.getPatient().getPrimaryClinic().getName(),
                item.getCareLevel().getName(),
                DateUtil.getStringFromDate(item.getContactDate()),
                item.getFollowUp().getName(),
                item.getLocation().getName(),
                item.getCareLevel().getName(),
                item.getPosition().getName()
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getCatsDetailExcel(List<CatDetail> list) {
        
        String[] headers = {"Name", "D.O.B", "Age", "Sex", "Certificate Number",
            "Phone Number", "Date Joined", "Facility", "Graduation Date",
            "Bled For VL", "VL Date", "VL Result", "Regimen",
            "Date Started Regimen", "Sexually Active", "Screened for TB", "TB Screening Date", 
        "TB Outcome", "Received Treatment for TB", "TB Treatment Outcome", "Has Children", "Current Status", "Issued Bicycle", 
        "Issued Phone", "Phone Model"};
        
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (CatDetail item : list) {
            String inner[] = {
                item.getPatient().getName(),
                DateUtil.formatDate(item.getPatient().getDateOfBirth()),
                String.valueOf(item.getPatient().getAge()),
                item.getPatient().getGender().getName(),
                "",
                item.getPatient().getMobileNumber(),
                DateUtil.formatDate(item.getDateAsCat()),
                item.getPrimaryClinic().getName(),
                item.getGraduationDate(),
                item.getVlResultTaken() != null ? item.getVlResultTaken().getName() : "",
                item.getVlDate() != null ? DateUtil.formatDate(item.getVlDate()) : "",
                String.valueOf(item.getPatient().getViralLoad()),
                item.getPatient().getCurrentArvRegimen(),
                item.getRegimenDate() != null ? DateUtil.formatDate(item.getRegimenDate()) : "",
                item.getSexuallyActive() != null ? item.getSexuallyActive().getName() : "", 
                item.getTbScreening() != null ? item.getTbScreening().getName() : "",
                item.getTbScreeningDate() != null ? DateUtil.formatDate(item.getTbScreeningDate()) : "", 
                item.getOutcome(), item.getReceivedTreatment(), item.getTreatmentOutcome(), item.getHaveChildren(),
                item.getCurrentStatus(), item.getBicycle(), item.getPhone(), item.getPhoneMode()
                
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

}
