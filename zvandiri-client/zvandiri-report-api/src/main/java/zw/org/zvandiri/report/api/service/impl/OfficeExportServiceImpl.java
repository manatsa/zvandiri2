/*
 * Copyright 2015 Judge Muzinda.
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Assessment;
import zw.org.zvandiri.business.domain.ChronicInfectionItem;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Dependent;
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.MentalHealthItem;
import zw.org.zvandiri.business.domain.ObstercHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.SocialHist;
import zw.org.zvandiri.business.domain.SubstanceItem;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class OfficeExportServiceImpl implements OfficeExportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;

    @Override
    public Workbook exportExcelFile(List<GenericReportModel> rows, String name) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(name);
        int rowNum = 0;
        for (GenericReportModel model : rows) {
            HSSFRow row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (String item : model.getRow()) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(item);
            }
        }
        return workbook;
    }

    @Override
    public XWPFDocument exportWordFile(List<GenericReportModel> rows, String name) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraphOne = document.createParagraph();
        XWPFTable table = document.createTable();
        int rowNum = 0;
//        for (GenericReportModel model : rows) {
//            XWPFTableRow tableRowOne = table.getRow(rowNum++);
//            int cellNum = 0;
//            for (String item : model.getRow()) {
//                XWPFRun paragraphOneRunOne = paragraphOne.createRun();
//                paragraphOneRunOne.setText(item);
//            }
//
//        }
        return document;
    }

    @Override
    public Workbook exportDatabase(String name, SearchDTO dto) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // Patient details sheet
        HSSFSheet patientDetails = workbook.createSheet("Patient_Details");
        int rowNum = 0;
        HSSFRow header = patientDetails.createRow(rowNum++);
        int cellNum = 0;
        for (String title : DatabaseHeader.PATIENT_HEADER) {
            Cell cell = header.createCell(cellNum++);
            cell.setCellValue(title);
        }
        List<Patient> patients = detailedPatientReportService.get(dto.getInstance(dto));
        Set<Referral> referrals = new HashSet<>();
        Set<Contact> contacts = new HashSet<>();
        Set<Dependent> dependents = new HashSet<>();
        Set<ChronicInfectionItem> chronicInfectionItems = new HashSet<>();
        Set<MentalHealthItem> mentalHealthItems = new HashSet<>();
        Set<ObstercHist> obstercHists = new HashSet<>();
        Set<SocialHist> socialHists = new HashSet<>();
        Set<SubstanceItem> substanceItems = new HashSet<>();
        Set<InvestigationTest> investigationTests = new HashSet<>();
        Set<ArvHist> arvHists = new HashSet<>();
        Set<HivConInfectionItem> hivConInfectionItems = new HashSet<>();
        int numPatient = 0;
        for (Patient patient : patients) {
            int count = 0;

            contacts.addAll(patient.getContacts());
            referrals.addAll(patient.getReferrals());
            dependents.addAll(patient.getDependents());
            chronicInfectionItems.addAll(patient.getChronicInfectionItems());
            hivConInfectionItems.addAll(patient.getHivConInfectionItems());
            mentalHealthItems.addAll(patient.getMentalHealthItems());
            obstercHists.addAll(patient.getObstercHists());
            socialHists.addAll(patient.getSocialHists());
            substanceItems.addAll(patient.getSubstanceItems());
            investigationTests.addAll(patient.getInvestigationTests());
            arvHists.addAll(patient.getArvHists());

            header = patientDetails.createRow(rowNum++);
            Cell id = header.createCell(count);
            id.setCellValue(patient.getId());
            Cell patientName = header.createCell(++count);
            patientName.setCellValue(patient.getName());
            Cell artNumber = header.createCell(++count);
            artNumber.setCellValue(patient.getoINumber());
            Cell dateOfBirth = header.createCell(++count);
            dateOfBirth.setCellValue(patient.getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            Cell dateJoined = header.createCell(++count);
            if (patient.getDateJoined() != null) {
                dateJoined.setCellValue(patient.getDateJoined());
                dateJoined.setCellStyle(cellStyle);
            } else {
                dateJoined.setCellValue("");
            }
            dateJoined.setCellStyle(cellStyle);
            Cell gender = header.createCell(++count);
            gender.setCellValue(patient.getGender().getName());
            String addressDetails = patient.getAddress() + " , " + patient.getAddress1();
            Cell address = header.createCell(++count);
            address.setCellValue(addressDetails);
            Cell mobileNumber = header.createCell(++count);
            mobileNumber.setCellValue(patient.getMobileNumber());
            Cell email = header.createCell(++count);
            Cell consetToMHelaath = header.createCell(++count);
            consetToMHelaath.setCellValue(patient.getConsentToMHealth() != null
                    ? patient.getConsentToMHealth().getName() : null);
            Cell education = header.createCell(++count);
            education.setCellValue(patient.getEducation() != null
                    ? patient.getEducation().getName() : null);
            Cell highestEducation = header.createCell(++count);
            highestEducation.setCellValue(patient.getEducationLevel() != null
                    ? patient.getEducationLevel().getName() : null);
            Cell refer = header.createCell(++count);
            refer.setCellValue(patient.getReferer() != null
                    ? patient.getReferer().getName() : null);
            Cell province = header.createCell(++count);
            province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = header.createCell(++count);
            district.setCellValue(patient.getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = header.createCell(++count);
            primaryClinic.setCellValue(patient.getPrimaryClinic().getName());
            Cell supportGroup = header.createCell(++count);
            supportGroup.setCellValue(patient.getSupportGroup() != null
                    ? patient.getSupportGroup().getName() : null);
            Cell dateTested = header.createCell(++count);
            if (patient.getDateTested() != null) {
                dateTested.setCellValue(patient.getDateTested());
                dateTested.setCellStyle(cellStyle);
            } else {
                dateTested.setCellValue("");
            }
            Cell hivDisclosureLoc = header.createCell(++count);
            hivDisclosureLoc.setCellValue(
                    patient.gethIVDisclosureLocation() != null
                    ? patient.gethIVDisclosureLocation().getName() : null
            );
            Cell hasDisability = header.createCell(++count);
            hasDisability.setCellValue(
                    patient.getDisability() != null ? patient.getDisability().getName() : null
            );
            Cell isCats = header.createCell(++count);
            isCats.setCellValue(
                    patient.getCat() != null ? patient.getCat().getName() : null
            );
            Cell youngMumGroup = header.createCell(++count);
            youngMumGroup.setCellValue(
                    patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null
            );
            Cell transMode = header.createCell(++count);
            transMode.setCellValue(
                    patient.getTransmissionMode() != null
                    ? patient.getTransmissionMode().getName() : null);
            Cell hivStatusKnown = header.createCell(++count);
            hivStatusKnown.setCellValue(
                    patient.getHivStatusKnown() != null
                    ? patient.getHivStatusKnown().getName() : null);
            Cell patientStatus = header.createCell(++count);
            patientStatus.setCellValue(
                    patient.getStatus() != null
                    ? patient.getStatus().getName() : null);
            Cell dateStatusChanged = header.createCell(++count);
            if (patient.getStatusChangeDate() != null) {
                dateStatusChanged.setCellValue(patient.getStatusChangeDate());
                dateStatusChanged.setCellStyle(cellStyle);
            } else {
                dateStatusChanged.setCellValue("");
            }
            numPatient++;
            if (numPatient >= 65535) {
                break;
            }

        }
        // add contacts
        HSSFSheet contactDetails = workbook.createSheet("Patient_Contacts");
        int contactRowNum = 0;
        HSSFRow contactHeader = contactDetails.createRow(contactRowNum++);
        int contactCellNum = 0;
        for (String title : DatabaseHeader.CONTACT_HEADER) {
            Cell cell = contactHeader.createCell(contactCellNum++);
            cell.setCellValue(title);
        }
        for (Contact contact : contacts) {
            int count = 0;
            contactHeader = contactDetails.createRow(contactRowNum++);
            Cell id = contactHeader.createCell(count);
            id.setCellValue(contact.getPatient().getId());
            Cell patientName = contactHeader.createCell(++count);
            patientName.setCellValue(contact.getPatient().getName());
            Cell province = contactHeader.createCell(++count);
            province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = contactHeader.createCell(++count);
            district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = contactHeader.createCell(++count);
            primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
            Cell contactDate = contactHeader.createCell(++count);
            contactDate.setCellValue(contact.getContactDate());
            contactDate.setCellStyle(cellStyle);
            Cell careLevel = contactHeader.createCell(++count);
            careLevel.setCellValue(contact.getCareLevel().getName());
            Cell location = contactHeader.createCell(++count);
            location.setCellValue(contact.getLocation() != null ? contact.getLocation().getName() : null);
            Cell position = contactHeader.createCell(++count);
            position.setCellValue(
                    contact.getPosition() != null ? contact.getPosition().getName() : null);
            Cell reason = contactHeader.createCell(++count);
            reason.setCellValue(contact.getReason() != null ? contact.getReason().getName() : null);
            Cell followUp = contactHeader.createCell(++count);
            followUp.setCellValue(contact.getFollowUp() != null ? contact.getFollowUp().getName() : null);
            Cell subjective = contactHeader.createCell(++count);
            subjective.setCellValue(contact.getSubjective());
            Cell objective = contactHeader.createCell(++count);
            objective.setCellValue(contact.getObjective());
            Cell plan = contactHeader.createCell(++count);
            plan.setCellValue(contact.getPlan());
            Cell actionTaken = contactHeader.createCell(++count);
            actionTaken.setCellValue(contact.getActionTaken() != null ? contact.getActionTaken().getName() : null);
            Cell lastClinicAppointmentDate = contactHeader.createCell(++count);
            if (contact.getLastClinicAppointmentDate() != null) {
                lastClinicAppointmentDate.setCellValue(contact.getLastClinicAppointmentDate());
                lastClinicAppointmentDate.setCellStyle(cellStyle);
            } else {
                lastClinicAppointmentDate.setCellValue("");
            }

            Cell attendedClinicAppointment = contactHeader.createCell(++count);
            attendedClinicAppointment.setCellValue(contact.getAttendedClinicAppointment() != null ? contact.getAttendedClinicAppointment().getName() : "");
        }

        // add contact assessments
        HSSFSheet assessmentDetails = workbook.createSheet("Patient_Contact_Assessments");
        int assessmentRowNum = 0;
        HSSFRow assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.ASSESSMENT_HEADER) {
            Cell cell = assessmentHeader.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }
        for (Contact contact : contacts) {
            if (!contact.getAssessments().isEmpty()) {
                for (Assessment item : contact.getAssessments()) {
                    int count = 0;
                    assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
                    Cell id = assessmentHeader.createCell(count);
                    id.setCellValue(contact.getPatient().getId());
                    Cell patientName = assessmentHeader.createCell(++count);
                    patientName.setCellValue(contact.getPatient().getName());
                    Cell province = assessmentHeader.createCell(++count);
                    province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    Cell district = assessmentHeader.createCell(++count);
                    district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                    Cell primaryClinic = assessmentHeader.createCell(++count);
                    primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                    Cell contactDate = assessmentHeader.createCell(++count);
                    contactDate.setCellValue(contact.getContactDate());
                    contactDate.setCellStyle(cellStyle);
                    Cell careLevel = assessmentHeader.createCell(++count);
                    careLevel.setCellValue(contact.getCareLevel().getName());
                    Cell assessment = assessmentHeader.createCell(++count);
                    assessment.setCellValue(item.toString());
                }
            }
        }
        // add referrals
        HSSFSheet referralDetails = workbook.createSheet("Patient_Referral");
        int referralRowNum = 0;
        HSSFRow referralRow = referralDetails.createRow(referralRowNum++);
        int referralCellNum = 0;
        for (String title : DatabaseHeader.REFERRAL_HEADER) {
            Cell cell = referralRow.createCell(referralCellNum++);
            cell.setCellValue(title);
        }
        for (Referral referral : referrals) {
            int count = 0;
            referralRow = referralDetails.createRow(referralRowNum++);
            Cell id = referralRow.createCell(count);
            id.setCellValue(referral.getPatient().getId());
            Cell patientName = referralRow.createCell(++count);
            patientName.setCellValue(referral.getPatient().getName());
            Cell province = referralRow.createCell(++count);
            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = referralRow.createCell(++count);
            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = referralRow.createCell(++count);
            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
            Cell referralDate = referralRow.createCell(++count);
            referralDate.setCellValue(referral.getReferralDate());
            referralDate.setCellStyle(cellStyle);
            Cell expectedVisitDate = referralRow.createCell(++count);
            if (referral.getExpectedVisitDate() != null) {
                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                expectedVisitDate.setCellStyle(cellStyle);
            } else {
                expectedVisitDate.setCellValue("");
            }
            Cell organisation = referralRow.createCell(++count);
            organisation.setCellValue(referral.getOrganisation());
            Cell designation = referralRow.createCell(++count);
            designation.setCellValue(referral.getDesignation());
            Cell attendingOfficer = referralRow.createCell(++count);
            attendingOfficer.setCellValue(referral.getAttendingOfficer());
            Cell dateAttended = referralRow.createCell(++count);
            if (referral.getDateAttended() != null) {
                dateAttended.setCellValue(referral.getDateAttended());
                dateAttended.setCellStyle(cellStyle);
            } else {
                dateAttended.setCellValue("");
            }
            Cell actionTaken = referralRow.createCell(++count);
            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
            /*
            
            "HIV Services Referred", "HIV Services Provided"
            , "HIV & STI Services Referred", "HIV & STI Services Provided", 
            "OI/ ART Services Referred", "OI/ ART Services Provided", "SRH Services Referred", 
            "SRH Services Provided", "Laboratory Services Referred", 
            "Laboratory Services Provided", "TB Services Referred", "TB Services Provided", 
            "Psych Services Referred", "Psych Services Provided", "Legal Services Referred", 
            "Legal Services Provided"
            
             */
            Cell hivReq = referralRow.createCell(++count);
            hivReq.setCellValue(!referral.getHivStiServicesReq().isEmpty()
                    ? referral.getHivStiServicesReq().toString() : null);
            Cell hivRec = referralRow.createCell(++count);
            hivRec.setCellValue(!referral.getHivStiServicesAvailed().isEmpty()
                    ? referral.getHivStiServicesAvailed().toString() : null);
            Cell oiReq = referralRow.createCell(++count);
            oiReq.setCellValue(!referral.getOiArtReq().isEmpty()
                    ? referral.getOiArtReq().toString() : null);
            Cell oiRec = referralRow.createCell(++count);
            oiRec.setCellValue(!referral.getOiArtAvailed().isEmpty()
                    ? referral.getOiArtAvailed().toString() : null);
            Cell srhReq = referralRow.createCell(++count);
            srhReq.setCellValue(!referral.getSrhReq().isEmpty()
                    ? referral.getSrhReq().toString() : null);
            Cell srhRec = referralRow.createCell(++count);
            srhRec.setCellValue(!referral.getSrhAvailed().isEmpty()
                    ? referral.getSrhAvailed().toString() : null);
            Cell labReq = referralRow.createCell(++count);
            labReq.setCellValue(!referral.getLaboratoryReq().isEmpty()
                    ? referral.getLaboratoryReq().toString() : null);
            Cell labRec = referralRow.createCell(++count);
            labRec.setCellValue(!referral.getLaboratoryAvailed().isEmpty()
                    ? referral.getLaboratoryAvailed().toString() : null);
            Cell tbReq = referralRow.createCell(++count);
            tbReq.setCellValue(!referral.getTbReq().isEmpty()
                    ? referral.getTbReq().toString() : null);
            Cell tbRec = referralRow.createCell(++count);
            tbRec.setCellValue(!referral.getTbAvailed().isEmpty()
                    ? referral.getTbAvailed().toString() : null);
            Cell psReq = referralRow.createCell(++count);
            psReq.setCellValue(!referral.getPsychReq().isEmpty()
                    ? referral.getPsychReq().toString() : null);
            Cell psRec = referralRow.createCell(++count);
            psRec.setCellValue(!referral.getPsychAvailed().isEmpty()
                    ? referral.getPsychAvailed().toString() : null);
            Cell legalReq = referralRow.createCell(++count);
            legalReq.setCellValue(!referral.getLegalReq().isEmpty()
                    ? referral.getLegalReq().toString() : null);
            Cell legalRec = referralRow.createCell(++count);
            legalRec.setCellValue(!referral.getLegalAvailed().isEmpty()
                    ? referral.getLegalAvailed().toString() : null);
        }
        // add patient dependants
        HSSFSheet dependantDetails = workbook.createSheet("Patient_Dependants");
        int dependantRowNum = 0;
        HSSFRow dependantRow = dependantDetails.createRow(dependantRowNum++);
        int dependantCellNum = 0;
        for (String title : DatabaseHeader.DEPENDANT_HEADER) {
            Cell cell = dependantRow.createCell(dependantCellNum++);
            cell.setCellValue(title);
        }
        for (Dependent dependent : dependents) {
            int count = 0;
            dependantRow = dependantDetails.createRow(dependantRowNum++);
            Cell id = dependantRow.createCell(count);
            id.setCellValue(dependent.getPatient().getId());
            Cell patientName = dependantRow.createCell(++count);
            patientName.setCellValue(dependent.getPatient().getName());
            Cell dependantName = dependantRow.createCell(++count);
            dependantName.setCellValue(dependent.getName());
            Cell province = dependantRow.createCell(++count);
            province.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = dependantRow.createCell(++count);
            district.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = dependantRow.createCell(++count);
            primaryClinic.setCellValue(dependent.getPatient().getPrimaryClinic().getName());
            Cell gender = dependantRow.createCell(++count);
            gender.setCellValue(dependent.getGender().getName());
            Cell dateOfBirth = dependantRow.createCell(++count);
            dateOfBirth.setCellValue(dependent.getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            Cell hivStatus = dependantRow.createCell(++count);
            hivStatus.setCellValue(dependent.getHivStatus() != null ? dependent.getHivStatus().getName() : "");
        }
        // add patient opportunistic infections
        HSSFSheet opportunisticInfectionDetails = workbook.createSheet("Patient_Opportunistic_Infections");
        int opportunisticInfectionRowNum = 0;
        HSSFRow opportunisticInfectionRow = opportunisticInfectionDetails.createRow(opportunisticInfectionRowNum++);
        int opportunisticInfectionCellNum = 0;
        for (String title : DatabaseHeader.OPPORTUNISTIC_INFECTION_HEADER) {
            Cell cell = opportunisticInfectionRow.createCell(opportunisticInfectionCellNum++);
            cell.setCellValue(title);
        }
        for (ChronicInfectionItem chronicInfectionItem : chronicInfectionItems) {
            int count = 0;
            opportunisticInfectionRow = opportunisticInfectionDetails.createRow(opportunisticInfectionRowNum++);
            Cell id = opportunisticInfectionRow.createCell(count);
            id.setCellValue(chronicInfectionItem.getPatient().getId());
            Cell patientName = opportunisticInfectionRow.createCell(++count);
            Cell province = opportunisticInfectionRow.createCell(++count);
            province.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = opportunisticInfectionRow.createCell(++count);
            district.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = opportunisticInfectionRow.createCell(++count);
            primaryClinic.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getName());
            patientName.setCellValue(chronicInfectionItem.getPatient().getName());
            Cell infection = opportunisticInfectionRow.createCell(++count);
            infection.setCellValue(chronicInfectionItem.getChronicInfection().getName());
            Cell dateDiagnosed = opportunisticInfectionRow.createCell(++count);
            if (chronicInfectionItem.getInfectionDate() != null) {
                dateDiagnosed.setCellValue(chronicInfectionItem.getInfectionDate());
                dateDiagnosed.setCellStyle(cellStyle);
            } else {
                dateDiagnosed.setCellValue("");
            }
            Cell medication = opportunisticInfectionRow.createCell(++count);
            medication.setCellValue(chronicInfectionItem.getMedication());
            Cell currentStatus = opportunisticInfectionRow.createCell(++count);
            currentStatus.setCellValue(chronicInfectionItem.getCurrentStatus() != null ? chronicInfectionItem.getCurrentStatus().getName() : "");
        }
        // add patient hiv-coinfections
        HSSFSheet hivCoInfectionDetails = workbook.createSheet("Patient_HIV_CO_Infections");
        int hivCoInfectionRowNum = 0;
        HSSFRow hivCoInfectionRow = hivCoInfectionDetails.createRow(hivCoInfectionRowNum++);
        int hivCoInfectionCellNum = 0;
        for (String title : DatabaseHeader.HIV_CO_INFECTION_HEADER) {
            Cell cell = hivCoInfectionRow.createCell(hivCoInfectionCellNum++);
            cell.setCellValue(title);
        }
        for (HivConInfectionItem hivConInfectionItem : hivConInfectionItems) {
            int count = 0;
            hivCoInfectionRow = hivCoInfectionDetails.createRow(hivCoInfectionRowNum++);
            Cell id = hivCoInfectionRow.createCell(count);
            id.setCellValue(hivConInfectionItem.getPatient().getId());
            Cell patientName = hivCoInfectionRow.createCell(++count);
            patientName.setCellValue(hivConInfectionItem.getPatient().getName());
            Cell province = hivCoInfectionRow.createCell(++count);
            province.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = hivCoInfectionRow.createCell(++count);
            district.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = hivCoInfectionRow.createCell(++count);
            primaryClinic.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getName());
            Cell infection = hivCoInfectionRow.createCell(++count);
            infection.setCellValue(hivConInfectionItem.getHivCoInfection().getName());
            Cell dateDiagnosed = hivCoInfectionRow.createCell(++count);
            if (hivConInfectionItem.getInfectionDate() != null) {
                dateDiagnosed.setCellValue(hivConInfectionItem.getInfectionDate());
                dateDiagnosed.setCellStyle(cellStyle);
            } else {
                dateDiagnosed.setCellValue("");
            }
            Cell medication = hivCoInfectionRow.createCell(++count);
            medication.setCellValue(hivConInfectionItem.getMedication());
            Cell resolution = hivCoInfectionRow.createCell(++count);
            resolution.setCellValue(hivConInfectionItem.getResolution());
        }
        // add patient mental health
        HSSFSheet mentalHealthDetails = workbook.createSheet("Patient_Mental_Health");
        int mentalHealthRowNum = 0;
        HSSFRow mentalHealthRow = mentalHealthDetails.createRow(mentalHealthRowNum++);
        int mentalHealthCellNum = 0;
        for (String title : DatabaseHeader.MENTAL_HIST_HEADER) {
            Cell cell = mentalHealthRow.createCell(mentalHealthCellNum++);
            cell.setCellValue(title);
        }
        for (MentalHealthItem mentalHealthItem : mentalHealthItems) {
            int count = 0;
            mentalHealthRow = mentalHealthDetails.createRow(mentalHealthRowNum++);
            Cell id = mentalHealthRow.createCell(count);
            id.setCellValue(mentalHealthItem.getPatient().getId());
            Cell patientName = mentalHealthRow.createCell(++count);
            patientName.setCellValue(mentalHealthItem.getPatient().getName());
            Cell province = mentalHealthRow.createCell(++count);
            province.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = mentalHealthRow.createCell(++count);
            district.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = mentalHealthRow.createCell(++count);
            primaryClinic.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getName());
            Cell mentalHealth = mentalHealthRow.createCell(++count);
            mentalHealth.setCellValue(mentalHealthItem.getMentalHealth().getName());
            Cell past = mentalHealthRow.createCell(++count);
            past.setCellValue(mentalHealthItem.getPast());
            Cell current = mentalHealthRow.createCell(++count);
            current.setCellValue(mentalHealthItem.getCurrent());
            Cell receiveProfessionalHelp = mentalHealthRow.createCell(++count);
            receiveProfessionalHelp.setCellValue(
                    mentalHealthItem.getReceivedProfessionalHelp() != null
                    ? mentalHealthItem.getReceivedProfessionalHelp().getName() : ""
            );
            Cell helpStartDate = mentalHealthRow.createCell(++count);
            if (mentalHealthItem.getProfHelpStart() != null) {
                helpStartDate.setCellValue(mentalHealthItem.getProfHelpStart());
                helpStartDate.setCellStyle(cellStyle);
            } else {
                helpStartDate.setCellValue("");
            }
            Cell helpEndDate = mentalHealthRow.createCell(++count);
            if (mentalHealthItem.getProfHelpEnd() != null) {
                helpEndDate.setCellValue(mentalHealthItem.getProfHelpEnd());
                helpEndDate.setCellStyle(cellStyle);
            } else {
                helpEndDate.setCellValue("");
            }
            Cell medication = mentalHealthRow.createCell(++count);
            medication.setCellValue(mentalHealthItem.getMedication());
            Cell medStartDate = mentalHealthRow.createCell(++count);
            if (mentalHealthItem.getStartDate() != null) {
                medStartDate.setCellValue(mentalHealthItem.getStartDate());
                medStartDate.setCellStyle(cellStyle);
            } else {
                medStartDate.setCellValue("");
            }
            Cell medEndDate = mentalHealthRow.createCell(++count);
            if (mentalHealthItem.getEndDate() != null) {
                medEndDate.setCellValue(mentalHealthItem.getEndDate());
                medEndDate.setCellStyle(cellStyle);
            } else {
                medEndDate.setCellValue("");
            }

            Cell hosp = mentalHealthRow.createCell(++count);
            hosp.setCellValue(
                    mentalHealthItem.getBeenHospitalized() != null
                    ? mentalHealthItem.getBeenHospitalized().getName() : ""
            );
            Cell desc = mentalHealthRow.createCell(++count);
            desc.setCellValue(mentalHealthItem.getMentalHistText());
        }
        // add patient obstetrics
        HSSFSheet obsDetails = workbook.createSheet("Patient_Obstetric_Hist");
        int obsRowNum = 0;
        HSSFRow obsRow = obsDetails.createRow(obsRowNum++);
        int obsCellNum = 0;
        for (String title : DatabaseHeader.OBSTERIC_HIST_HEADER) {
            Cell cell = obsRow.createCell(obsCellNum++);
            cell.setCellValue(title);
        }
        for (ObstercHist obstercHist : obstercHists) {
            int count = 0;
            obsRow = obsDetails.createRow(obsRowNum++);
            Cell id = obsRow.createCell(count);
            id.setCellValue(obstercHist.getPatient().getId());
            Cell patientName = obsRow.createCell(++count);
            patientName.setCellValue(obstercHist.getPatient().getName());
            Cell province = obsRow.createCell(++count);
            province.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = obsRow.createCell(++count);
            district.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = obsRow.createCell(++count);
            primaryClinic.setCellValue(obstercHist.getPatient().getPrimaryClinic().getName());
            Cell liveWith = obsRow.createCell(++count);
            liveWith.setCellValue(obstercHist.getPregnant().getName());
            Cell breatFeeding = obsRow.createCell(++count);
            breatFeeding.setCellValue(obstercHist.getBreafFeedingCurrent() != null ? obstercHist.getBreafFeedingCurrent().getName() : "");
            Cell currentPreg = obsRow.createCell(++count);
            currentPreg.setCellValue(
                    obstercHist.getPregCurrent() != null
                    ? obstercHist.getPregCurrent().getName() : ""
            );
            Cell numAncVisit = obsRow.createCell(++count);
            numAncVisit.setCellValue(
                    obstercHist.getNumberOfANCVisit() != null
                    ? obstercHist.getNumberOfANCVisit().getName() : ""
            );
            Cell gestAge = obsRow.createCell(++count);
            gestAge.setCellValue(
                    obstercHist.getGestationalAge() != null
                    ? obstercHist.getGestationalAge().getName() : ""
            );
            Cell artStarted = obsRow.createCell(++count);
            artStarted.setCellValue(
                    obstercHist.getArtStarted() != null
                    ? obstercHist.getArtStarted().getName() : ""
            );
            Cell numChildren = obsRow.createCell(++count);
            numChildren.setCellValue(obstercHist.getChildren() != null ? obstercHist.getChildren() : 0);
        }
        // add patient social history
        HSSFSheet socialHistDetails = workbook.createSheet("Patient_Social_Hist");
        int socialHistRowNum = 0;
        HSSFRow socialHistRow = socialHistDetails.createRow(socialHistRowNum++);
        int socialHistCellNum = 0;
        for (String title : DatabaseHeader.SOCIAL_HISTORY_HEADER) {
            Cell cell = socialHistRow.createCell(socialHistCellNum++);
            cell.setCellValue(title);
        }
        for (SocialHist socialHist : socialHists) {
            int count = 0;
            socialHistRow = socialHistDetails.createRow(socialHistRowNum++);
            Cell id = socialHistRow.createCell(count);
            id.setCellValue(socialHist.getPatient().getId());
            Cell patientName = socialHistRow.createCell(++count);
            patientName.setCellValue(socialHist.getPatient().getName());
            Cell province = socialHistRow.createCell(++count);
            province.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = socialHistRow.createCell(++count);
            district.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = socialHistRow.createCell(++count);
            primaryClinic.setCellValue(socialHist.getPatient().getPrimaryClinic().getName());
            Cell liveWith = socialHistRow.createCell(++count);
            liveWith.setCellValue(socialHist.getLiveWith());
            Cell relationship = socialHistRow.createCell(++count);
            relationship.setCellValue(
                    socialHist.getRelationship() != null
                    ? socialHist.getRelationship().getName() : ""
            );
            Cell abused = socialHistRow.createCell(++count);
            abused.setCellValue(
                    socialHist.getAbuse() != null ? socialHist.getAbuse().getName() : ""
            );
            Cell disclosed = socialHistRow.createCell(++count);
            disclosed.setCellValue(
                    socialHist.getDosclosure() != null ? socialHist.getDosclosure().getName() : ""
            );
            Cell feelSafe = socialHistRow.createCell(++count);
            feelSafe.setCellValue(
                    socialHist.getFeelSafe() != null ? socialHist.getFeelSafe().getName() : ""
            );
            Cell abuseType = socialHistRow.createCell(++count);
            abuseType.setCellValue(socialHist.getAbuseType() != null ? socialHist.getAbuseType().getName() : "");
            Cell abuseOutcome = socialHistRow.createCell(++count);
            abuseOutcome.setCellValue(socialHist.getAbuseOutcome() != null ? socialHist.getAbuseOutcome().getName() : "");

        }
        // add patient substance use history
        HSSFSheet substanceUseDetails = workbook.createSheet("Patient_Substace_Use_Hist");
        int substanceUseRowNum = 0;
        HSSFRow substanceUseRow = substanceUseDetails.createRow(substanceUseRowNum++);
        int substanceUseCellNum = 0;
        for (String title : DatabaseHeader.SUBSTANCE_USE_HEADER) {
            Cell cell = substanceUseRow.createCell(substanceUseCellNum++);
            cell.setCellValue(title);
        }
        for (SubstanceItem substanceItem : substanceItems) {
            int count = 0;
            substanceUseRow = substanceUseDetails.createRow(substanceUseRowNum++);
            Cell id = substanceUseRow.createCell(count);
            id.setCellValue(substanceItem.getPatient().getId());
            Cell patientName = substanceUseRow.createCell(++count);
            patientName.setCellValue(substanceItem.getPatient().getName());
            Cell province = substanceUseRow.createCell(++count);
            province.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = substanceUseRow.createCell(++count);
            district.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = substanceUseRow.createCell(++count);
            primaryClinic.setCellValue(substanceItem.getPatient().getPrimaryClinic().getName());
            Cell substance = substanceUseRow.createCell(++count);
            substance.setCellValue(substanceItem.getSubstance().getName());
            Cell curent = substanceUseRow.createCell(++count);
            curent.setCellValue(substanceItem.getCurrent() != null ? substanceItem.getCurrent().getName() : "");
            Cell past = substanceUseRow.createCell(++count);
            past.setCellValue(substanceItem.getPast() != null ? substanceItem.getPast().getName() : "");
            Cell startDate = substanceUseRow.createCell(++count);
            if (substanceItem.getStartDate() != null) {
                startDate.setCellValue(substanceItem.getStartDate());
                startDate.setCellStyle(cellStyle);
            } else {
                startDate.setCellValue("");
            }
            Cell endDate = substanceUseRow.createCell(++count);
            if (substanceItem.getEndDate() != null) {
                endDate.setCellValue(substanceItem.getEndDate());
                endDate.setCellStyle(cellStyle);
            } else {
                endDate.setCellValue("");
            }

            Cell drugIntervention = substanceUseRow.createCell(++count);
            drugIntervention.setCellValue(substanceItem.getDrugIntervention() != null ? substanceItem.getDrugIntervention().getName() : "");
            Cell duration = substanceUseRow.createCell(++count);
            duration.setCellValue(substanceItem.getDuration());
        }

        /* start here **/
        // add patient dependants
        HSSFSheet cd4CountDetails = workbook.createSheet("Client_Lab_RESULTS");
        int cd4RowNum = 0;
        HSSFRow cd4Row = cd4CountDetails.createRow(cd4RowNum++);
        int cd4CellNum = 0;
        for (String title : DatabaseHeader.CD4_COUNT_HEADER) {
            Cell cell = cd4Row.createCell(cd4CellNum++);
            cell.setCellValue(title);
        }
        for (InvestigationTest cd4Count : investigationTests) {
            int count = 0;
            cd4Row = cd4CountDetails.createRow(cd4RowNum++);
            Cell id = cd4Row.createCell(count);
            id.setCellValue(cd4Count.getPatient().getId());
            Cell patientName = cd4Row.createCell(++count);
            patientName.setCellValue(cd4Count.getPatient().getName());
            Cell province = cd4Row.createCell(++count);
            province.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = cd4Row.createCell(++count);
            district.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = cd4Row.createCell(++count);
            primaryClinic.setCellValue(cd4Count.getPatient().getPrimaryClinic().getName());
            Cell testType = cd4Row.createCell(++count);
            testType.setCellValue(cd4Count.getTestType().getName());
            Cell dateTaken = cd4Row.createCell(++count);
            if (cd4Count.getDateTaken() != null) {
                dateTaken.setCellValue(cd4Count.getDateTaken());
                dateTaken.setCellStyle(cellStyle);
            } else {
                dateTaken.setCellValue("");
            }
            Cell cd4Load = cd4Row.createCell(++count);
            cd4Load.setCellValue(cd4Count.getResult() != null ? cd4Count.getResult() : 0);
            Cell source = cd4Row.createCell(++count);
            source.setCellValue(cd4Count.getSource() != null ? cd4Count.getSource().getName() : "");
            Cell nextLabDate = cd4Row.createCell(++count);
            if (cd4Count.getNextTestDate() != null) {
                nextLabDate.setCellValue(cd4Count.getNextTestDate());
                nextLabDate.setCellStyle(cellStyle);
            } else {
                nextLabDate.setCellValue("");
            }
        }

        // arv history
        HSSFSheet arvHistDetails = workbook.createSheet("Patient_ARV_HISTORY");
        int arvHistRowNum = 0;
        HSSFRow arvHistRow = arvHistDetails.createRow(arvHistRowNum++);
        int arvHistCellNum = 0;
        for (String title : DatabaseHeader.ARV_HISTORY_HEADER) {
            Cell cell = arvHistRow.createCell(arvHistCellNum++);
            cell.setCellValue(title);
        }
        for (ArvHist arvHist : arvHists) {
            int count = 0;
            arvHistRow = arvHistDetails.createRow(arvHistRowNum++);
            Cell id = arvHistRow.createCell(count);
            id.setCellValue(arvHist.getPatient().getId());
            Cell patientName = arvHistRow.createCell(++count);
            patientName.setCellValue(arvHist.getPatient().getName());
            Cell province = arvHistRow.createCell(++count);
            province.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = arvHistRow.createCell(++count);
            district.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = arvHistRow.createCell(++count);
            primaryClinic.setCellValue(arvHist.getPatient().getPrimaryClinic().getName());

            Cell arvHistMedicine = arvHistRow.createCell(++count);
            arvHistMedicine.setCellValue(arvHist.getMedicines());
            Cell startDate = arvHistRow.createCell(++count);
            if (arvHist.getStartDate() != null) {
                startDate.setCellValue(arvHist.getStartDate());
                startDate.setCellStyle(cellStyle);
            } else {
                startDate.setCellValue("");
            }
            Cell endDate = arvHistRow.createCell(++count);
            if (arvHist.getEndDate() != null) {
                endDate.setCellValue(arvHist.getEndDate());
                endDate.setCellStyle(cellStyle);
            } else {
                endDate.setCellValue("");
            }
        }
        /* end here    */
        return workbook;
    }
}
