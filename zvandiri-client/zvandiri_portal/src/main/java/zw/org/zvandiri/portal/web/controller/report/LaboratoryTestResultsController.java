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
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.ProvinceService;
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
    private PatientReportService patientReportService;

    List<Patient> tests=new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, String type, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Lab Results Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
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
        model.addAttribute("excelExport", "/report/test-results/export/excel" + item.getQueryString(item.getInstance(item)));
        if (post) {
            model.addAttribute("items", tests);
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
        tests=patientReportService.getPatientLabResultsList(item.getInstance(item));
        return setUpModel(model, item, type, true);
    }



    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("VL_CD4_Count_Report");
        forceDownLoadXLSX(createVLCD4Workbook(item),name, response);
    }


    public XSSFWorkbook createVLCD4Workbook(SearchDTO dto) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));



        // tb Ipt here
        XSSFSheet tbIptDetails = workbook.createSheet("VL_CD4_Count");
        int testRowNum = 0;
        XSSFRow testRow = tbIptDetails.createRow(testRowNum++);
        int tbIptCellNum = 0;
        String[] headers={"UAC","Name","Date of Birth","Age","Viral Load","CD4 Count","Gender","IsCATS","IsYMM","Province","District","Facility","Mobile Number","Referrer"};
        for (String title : headers) {
            XSSFCell cell = testRow.createCell(tbIptCellNum++);
            cell.setCellValue(title);
        }

        for (Patient test : tests) {
            int count = 0;
            testRow = tbIptDetails.createRow(testRowNum++);
            XSSFCell id = testRow.createCell(count);
            id.setCellValue(test.getPatientNumber());

            XSSFCell patientName = testRow.createCell(++count);
            patientName.setCellValue(test.getName());

            XSSFCell dateOfBirth = testRow.createCell(++count);
            dateOfBirth.setCellValue(test.getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell age = testRow.createCell(++count);
            age.setCellValue(test.getAge());

            XSSFCell vl = testRow.createCell(++count);
            vl.setCellValue(test.getViralLoad());

            XSSFCell cd4 = testRow.createCell(++count);
            cd4.setCellValue(test.getCd4Count());

            XSSFCell sex = testRow.createCell(++count);
            sex.setCellValue(test.getGender().getName());

            Cell cat = testRow.createCell(++count);
            cat.setCellValue(test.getCat()!=null?test.getCat().getName():"");

            Cell ymm = testRow.createCell(++count);
            ymm.setCellValue(test.getYoungMumGroup()!=null?test.getYoungMumGroup().getName():"");

            XSSFCell province = testRow.createCell(++count);
            province.setCellValue(test.getPrimaryClinic().getDistrict().getProvince().getName());

            XSSFCell district = testRow.createCell(++count);
            district.setCellValue(test.getPrimaryClinic().getDistrict().getName());
            
            XSSFCell primaryClinic = testRow.createCell(++count);
            primaryClinic.setCellValue(test.getPrimaryClinic().getName());

            XSSFCell phone = testRow.createCell(++count);
            phone.setCellValue(test.getMobileNumber());

            XSSFCell referrer = testRow.createCell(++count);
            referrer.setCellValue(test.getReferer().getName());

        }

        return workbook;
    }




}
