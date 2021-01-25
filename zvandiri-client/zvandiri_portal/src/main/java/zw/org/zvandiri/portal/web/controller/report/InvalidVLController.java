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
package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.repo.InvestigationTestRepo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/invalid-vls")
public class InvalidVLController extends BaseController {


    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private SupportGroupService supportGroupService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private PeriodService periodService;
    @Resource
    InvestigationTestService investigationTestService;

    List<InvestigationTest> vls=new ArrayList<>();


    public void setUpModel(ModelMap model, SearchDTO item, Boolean post, Boolean hei) {
        item = getUserLevelObjectState(item);
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }


        if (post) {
            model.addAttribute("excelExport", "/report/invalid-vls/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", vls);
        }


        model.addAttribute("item", item.getInstance(item));
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getMortalityRangeIndex(ModelMap model, SearchDTO dto) {
        model.addAttribute("pageTitle", APP_PREFIX + "Invalid VLs Detailed Report");
        Boolean post = Boolean.TRUE;
        if (dto.getStatus() != null && dto.getStatus().equals(PatientChangeEvent.ACTIVE) && (dto.getMaxViralLoad() == null && dto.getMinCd4Count() == null)) {
            post = Boolean.FALSE;
        }
        setUpModel(model, dto, post, Boolean.FALSE);
        return "report/invalidVLDetailedReport";
    }



    @RequestMapping(value = {"/range"}, method = RequestMethod.POST)
    public String getMortalityRangeIndexPost(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        //System.err.println("************************************************Now checking mortalities ++++++++++++++++++++++++++++++++");
        model.addAttribute("pageTitle", APP_PREFIX + "Invalid VL Detailed Report");
        vls=getInvalidVLsList(item);
        setUpModel(model, item, Boolean.TRUE, Boolean.FALSE);
        return "report/invalidVLDetailedReport";
    }

    private List<InvestigationTest> getInvalidVLsList(SearchDTO item) {
        List<InvestigationTest> investigationTestList=new ArrayList<>();
        investigationTestList=investigationTestService.get(item);
        System.err.println(investigationTestList.get(0).toString());
        return investigationTestList;
    }


    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Invalid_VL_Mortality_Report");
        forceDownLoadDatabase(createVLsWorkbook(item), name, response);
    }




    public XSSFWorkbook createVLsWorkbook(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        XSSFSheet vlsDetails = workbook.createSheet("Invalid VLs");
        int vlRowNum = 0;
        XSSFRow vlRow = vlsDetails.createRow(vlRowNum++);
        int mortalityXSSFCellNum = 0;
        for (String title : DatabaseHeader.MORTALITY_HEADER) {
            XSSFCell XSSFCell = vlRow.createCell(mortalityXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }

        for (InvestigationTest test : vls) {
            int count = 0;
            vlRow = vlsDetails.createRow(vlRowNum++);
            XSSFCell id = vlRow.createCell(count);
            id.setCellValue(test.getPatient().getPatientNumber());
            XSSFCell patientName = vlRow.createCell(++count);
            patientName.setCellValue(test.getPatient().getName());
            XSSFCell dateOfBirth = vlRow.createCell(++count);
            dateOfBirth.setCellValue(test.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(XSSFCellStyle);
            XSSFCell age = vlRow.createCell(++count);
            age.setCellValue(test.getPatient().getAge());
            XSSFCell sex = vlRow.createCell(++count);
            sex.setCellValue(test.getPatient().getGender().getName());
            Cell cat = vlRow.createCell(++count);
            cat.setCellValue(test.getPatient().getCat()!=null?test.getPatient().getCat().getName():"");
            XSSFCell province = vlRow.createCell(++count);
            province.setCellValue(test.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = vlRow.createCell(++count);
            district.setCellValue(test.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = vlRow.createCell(++count);
            primaryClinic.setCellValue(test.getPatient().getPrimaryClinic().getName());




        }

        return workbook;
    }

}
