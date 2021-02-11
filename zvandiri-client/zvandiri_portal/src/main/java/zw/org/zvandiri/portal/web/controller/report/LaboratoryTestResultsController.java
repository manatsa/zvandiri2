/*
 * Copyright 2018 jmuzinda.
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmuzinda
 */
@Controller
@RequestMapping("/report/test-results")
public class LaboratoryTestResultsController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    InvestigationTestService investigationTestService;

    List<InvestigationTest> investigationTests=new ArrayList<>();

    List<Patient> tests=new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, String type, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Lab Results Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict()!=null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (type.equals("viral-load")) {
            item.setMaxViralLoad(0);
            item.setTestType(TestType.VIRAL_LOAD);
        } else if (type.equals("cd4-count")) {
            item.setMinCd4Count(2000000);
            item.setTestType(TestType.CD4_COUNT);
        }
        model.addAttribute("excelExport", "/report/test-results/export/excel");
        if (post) {
            model.addAttribute("items", investigationTests);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/laboratoryDetailedReport";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type) {
        return setUpModel(model, new SearchDTO(), type, false);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item = getUserLevelObjectState(item);
         investigationTests= investigationTestService.get(item.getInstance(item));
         System.err.println("List Size : "+investigationTests.size());
        return setUpModel(model, item, type, true);
    }



    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("VL_CD4_Count_Report");
        forceDownLoadXLSX(createVLCD4Workbook(item),name, response);
    }


    public XSSFWorkbook createVLCD4Workbook(SearchDTO dto) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));



        // tb Ipt here
        XSSFSheet tbIptDetails = workbook.createSheet("VL_CD4_Count");
        int testRowNum = 0;
        XSSFRow cd4XSSFRow = tbIptDetails.createRow(testRowNum++);
        int tbIptCellNum = 0;
        String[] headers={"UIC", "Client Name","Date of Birth", "Age", "Gender", "IsCATS","IsYMM","Region", "District","Primary Clinic", "Test Type", "Date Taken",
                "Result", "Source", "Next Lab Due","VLSuppressionStatus","Result Taken","TND","Record Source"};
        for (String title : headers) {
            XSSFCell cell = cd4XSSFRow.createCell(tbIptCellNum++);
            cell.setCellValue(title);
        }

        for (InvestigationTest cd4Count : investigationTests) {
            int count = 0;
            cd4XSSFRow = tbIptDetails.createRow(testRowNum++);
            XSSFCell id = cd4XSSFRow.createCell(count);
            id.setCellValue(cd4Count.getPatient().getPatientNumber());
            XSSFCell patientName = cd4XSSFRow.createCell(++count);
            patientName.setCellValue(cd4Count.getPatient().getName());
            XSSFCell dateOfBirth = cd4XSSFRow.createCell(++count);
            dateOfBirth.setCellValue(cd4Count.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(XSSFCellStyle);
            XSSFCell age = cd4XSSFRow.createCell(++count);
            age.setCellValue(cd4Count.getPatient().getAge());
            XSSFCell sex = cd4XSSFRow.createCell(++count);
            sex.setCellValue(cd4Count.getPatient().getGender().getName());

            XSSFCell cats = cd4XSSFRow.createCell(++count);
            cats.setCellValue(cd4Count.getPatient().getCat() != null ?
                    cd4Count.getPatient().getCat().getName() : "");

            XSSFCell ymm = cd4XSSFRow.createCell(++count);
            ymm.setCellValue(cd4Count.getPatient().getYoungMumGroup() != null ?
                    cd4Count.getPatient().getYoungMumGroup().getName() : "");

            XSSFCell province = cd4XSSFRow.createCell(++count);
            province.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = cd4XSSFRow.createCell(++count);
            district.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = cd4XSSFRow.createCell(++count);
            primaryClinic.setCellValue(cd4Count.getPatient().getPrimaryClinic().getName());
            XSSFCell testType = cd4XSSFRow.createCell(++count);
            testType.setCellValue(cd4Count.getTestType().getName());
            XSSFCell dateTaken = cd4XSSFRow.createCell(++count);
            if (cd4Count.getDateTaken() != null) {
                dateTaken.setCellValue(cd4Count.getDateTaken());
                dateTaken.setCellStyle(XSSFCellStyle);
            } else {
                dateTaken.setCellValue("");
            }
            XSSFCell cd4Load = cd4XSSFRow.createCell(++count);
            cd4Load.setCellValue(cd4Count.getResult() != null ? cd4Count.getResult() : 0);
            XSSFCell source = cd4XSSFRow.createCell(++count);
            source.setCellValue(cd4Count.getSource() != null ? cd4Count.getSource().getName() : "");
            XSSFCell nextLabDate = cd4XSSFRow.createCell(++count);
            if (cd4Count.getNextTestDate() != null) {
                nextLabDate.setCellValue(cd4Count.getNextTestDate());
                nextLabDate.setCellStyle(XSSFCellStyle);
            } else {
                nextLabDate.setCellValue("");
            }
            XSSFCell suppressionStatus = cd4XSSFRow.createCell(++count);
            if (cd4Count.getViralLoadSuppressionStatus() != null) {
                suppressionStatus.setCellValue(cd4Count.getViralLoadSuppressionStatus());
                suppressionStatus.setCellStyle(XSSFCellStyle);
            } else {
                suppressionStatus.setCellValue("");
            }

            XSSFCell resultsTaken = cd4XSSFRow.createCell(++count);
            if (cd4Count.getResultTaken() != null) {
                resultsTaken.setCellValue(cd4Count.getResultTaken().getName());
                resultsTaken.setCellStyle(XSSFCellStyle);
            } else {
                resultsTaken.setCellValue("");
            }

            XSSFCell tnd = cd4XSSFRow.createCell(++count);
            if (cd4Count.getTnd() != null) {
                tnd.setCellValue(cd4Count.getTnd());
                tnd.setCellStyle(XSSFCellStyle);
            } else {
                tnd.setCellValue("");
            }

            XSSFCell recordSource = cd4XSSFRow.createCell(++count);
            if (cd4Count.getRecordSource() != null) {
                recordSource.setCellValue(cd4Count.getRecordSource().getName());
                recordSource.setCellStyle(XSSFCellStyle);
            } else {
                recordSource.setCellValue("");
            }

        }

            return workbook;
    }




}
