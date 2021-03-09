/*
 * Copyright 2017 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.report;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.ReferalReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.ReferralReportAPIService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/referral")
public class ReferralReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ReferalReportService referalReportService;
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private ReferralReportAPIService referralReportAPIService;

    List<Referral> referrals=new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "External Referral Detailed Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("excelExport", "/report/referral/export/excel" + item.getQueryString(item.getInstance(item)));
        if (post) {
            referrals=referalReportService.get(item.getInstance(item));
            model.addAttribute("items", referrals);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/referralDetailedReport";
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Referral_Report");
        forceDownLoadXLSX(createReferralsWorkbook(item),name, response);
        //forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(referralReportAPIService.getDefaultReport(item.getInstance(item)), name), name, response);
    }


    public XSSFWorkbook createReferralsWorkbook(SearchDTO dto) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        XSSFSheet referralDetails = workbook.createSheet("Patient_Referral");
        int referralXSSFRowNum = 0;
        XSSFRow referralXSSFRow = referralDetails.createRow(referralXSSFRowNum++);
        int referralXSSFCellNum = 0;
        for (String title : DatabaseHeader.REFERRAL_HEADER) {
            XSSFCell XSSFCell = referralXSSFRow.createCell(referralXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }
        for (Referral referral : referrals) {
            int count = 0;
            referralXSSFRow = referralDetails.createRow(referralXSSFRowNum++);
            XSSFCell id = referralXSSFRow.createCell(count);
            id.setCellValue(referral.getPatient().getPatientNumber());

            XSSFCell patientName = referralXSSFRow.createCell(++count);
            patientName.setCellValue(referral.getPatient().getName());

            XSSFCell dateOfBirth = referralXSSFRow.createCell(++count);
            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell age = referralXSSFRow.createCell(++count);
            age.setCellValue(referral.getPatient().getAge());

            XSSFCell sex = referralXSSFRow.createCell(++count);
            sex.setCellValue(referral.getPatient().getGender().getName());

            XSSFCell province = referralXSSFRow.createCell(++count);
            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());

            XSSFCell district = referralXSSFRow.createCell(++count);
            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());

            XSSFCell primaryClinic = referralXSSFRow.createCell(++count);
            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());

            XSSFCell referralDate = referralXSSFRow.createCell(++count);
            referralDate.setCellValue(referral.getReferralDate());
            referralDate.setCellStyle(cellStyle);

            XSSFCell expectedVisitDate = referralXSSFRow.createCell(++count);
            if (referral.getExpectedVisitDate() != null) {
                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                expectedVisitDate.setCellStyle(cellStyle);
            } else {
                expectedVisitDate.setCellValue("");
            }

            XSSFCell organisation = referralXSSFRow.createCell(++count);
            organisation.setCellValue(referral.getOrganisation());

            XSSFCell designation = referralXSSFRow.createCell(++count);
            designation.setCellValue(referral.getDesignation());

            XSSFCell attendingOfficer = referralXSSFRow.createCell(++count);
            attendingOfficer.setCellValue(referral.getAttendingOfficer());

            XSSFCell dateAttended = referralXSSFRow.createCell(++count);
            if (referral.getDateAttended() != null) {
                dateAttended.setCellValue(referral.getDateAttended());
                dateAttended.setCellStyle(cellStyle);
            } else {
                dateAttended.setCellValue("");
            }

            XSSFCell actionTaken = referralXSSFRow.createCell(++count);
            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");

            XSSFCell hivReq = referralXSSFRow.createCell(++count);
            hivReq.setCellValue(!referral.getHivStiServicesReq().isEmpty()? referral.getHivStiServicesReq().toString() : null);

            XSSFCell hivRec = referralXSSFRow.createCell(++count);
            hivRec.setCellValue(!referral.getHivStiServicesAvailed().isEmpty()
                    ? referral.getHivStiServicesAvailed().toString() : null);

            XSSFCell oiReq = referralXSSFRow.createCell(++count);
            oiReq.setCellValue(!referral.getOiArtReq().isEmpty()
                    ? referral.getOiArtReq().toString() : null);

            XSSFCell oiRec = referralXSSFRow.createCell(++count);
            oiRec.setCellValue(!referral.getOiArtAvailed().isEmpty()
                    ? referral.getOiArtAvailed().toString() : null);

            XSSFCell srhReq = referralXSSFRow.createCell(++count);
            srhReq.setCellValue(!referral.getSrhReq().isEmpty()
                    ? referral.getSrhReq().toString() : null);
            XSSFCell srhRec = referralXSSFRow.createCell(++count);
            srhRec.setCellValue(!referral.getSrhAvailed().isEmpty()
                    ? referral.getSrhAvailed().toString() : null);
            XSSFCell labReq = referralXSSFRow.createCell(++count);
            labReq.setCellValue(!referral.getLaboratoryReq().isEmpty()
                    ? referral.getLaboratoryReq().toString() : null);
            XSSFCell labRec = referralXSSFRow.createCell(++count);
            labRec.setCellValue(!referral.getLaboratoryAvailed().isEmpty()
                    ? referral.getLaboratoryAvailed().toString() : null);
            XSSFCell tbReq = referralXSSFRow.createCell(++count);
            tbReq.setCellValue(!referral.getTbReq().isEmpty()
                    ? referral.getTbReq().toString() : null);
            XSSFCell tbRec = referralXSSFRow.createCell(++count);
            tbRec.setCellValue(!referral.getTbAvailed().isEmpty()
                    ? referral.getTbAvailed().toString() : null);
            XSSFCell psReq = referralXSSFRow.createCell(++count);
            psReq.setCellValue(!referral.getPsychReq().isEmpty()
                    ? referral.getPsychReq().toString() : null);
            XSSFCell psRec = referralXSSFRow.createCell(++count);
            psRec.setCellValue(!referral.getPsychAvailed().isEmpty()
                    ? referral.getPsychAvailed().toString() : null);
            XSSFCell legalReq = referralXSSFRow.createCell(++count);
            legalReq.setCellValue(!referral.getLegalReq().isEmpty()
                    ? referral.getLegalReq().toString() : null);
            XSSFCell legalRec = referralXSSFRow.createCell(++count);
            legalRec.setCellValue(!referral.getLegalAvailed().isEmpty()
                    ? referral.getLegalAvailed().toString() : null);
            if (referralXSSFRowNum >= 65535) {
                break;
            }
        }



        return workbook;

    }


    }
