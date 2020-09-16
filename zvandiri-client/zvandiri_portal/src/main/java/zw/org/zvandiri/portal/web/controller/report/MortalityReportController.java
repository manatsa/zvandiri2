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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

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
@RequestMapping("/report/mortality")
public class MortalityReportController extends BaseController {

    @Resource
    private DetailedReportService detailedReportService;
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
    MortalityService mortalityService;
    @Resource
    private PatientReportService patientReportService;

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
            model.addAttribute("excelExport", "/report/mortality/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", patientReportService.getPatientDeceased(item.getInstance(item)));
        }


        model.addAttribute("item", item.getInstance(item));
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getMortalityRangeIndex(ModelMap model, SearchDTO dto) {
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Detailed Report");
        Boolean post = Boolean.TRUE;
        if (dto.getStatus() != null && dto.getStatus().equals(PatientChangeEvent.ACTIVE) && (dto.getMaxViralLoad() == null && dto.getMinCd4Count() == null)) {
            post = Boolean.FALSE;
        }
        setUpModel(model, dto, post, Boolean.FALSE);
        return "report/mortalityDetailedReport";
    }



    @RequestMapping(value = {"/range"}, method = RequestMethod.POST)
    public String getMortalityRangeIndexPost(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        System.err.println("************************************************Now checking mortalities ++++++++++++++++++++++++++++++++");
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Detailed Report");
        setUpModel(model, item, Boolean.TRUE, Boolean.FALSE);
        return "report/mortalityDetailedReport";
    }



    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Mortality_Report");
        List<Patient> items=patientReportService.getPatientDeceased(item);
        forceDownLoadDatabase(createMortalityWorkbook(item), name, response);
    }




    public Workbook createMortalityWorkbook(SearchDTO dto) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        List<Mortality> mortalitys=mortalityService.get(dto);

        Sheet mortalityDetails = workbook.createSheet("Patient_Mortality");
        int mortalityRowNum = 0;
        Row mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
        int mortalityCellNum = 0;
        for (String title : DatabaseHeader.MORTALITY_HEADER) {
            Cell cell = mortalityRow.createCell(mortalityCellNum++);
            cell.setCellValue(title);
        }

        for (Mortality mortality : mortalitys) {
            int count = 0;
            mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
            Cell id = mortalityRow.createCell(count);
            id.setCellValue(mortality.getPatient().getPatientNumber());
            Cell patientName = mortalityRow.createCell(++count);
            patientName.setCellValue(mortality.getPatient().getName());
            Cell dateOfBirth = mortalityRow.createCell(++count);
            dateOfBirth.setCellValue(mortality.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            Cell age = mortalityRow.createCell(++count);
            age.setCellValue(mortality.getPatient().getAge());
            Cell sex = mortalityRow.createCell(++count);
            sex.setCellValue(mortality.getPatient().getGender().getName());
            Cell province = mortalityRow.createCell(++count);
            province.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = mortalityRow.createCell(++count);
            district.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = mortalityRow.createCell(++count);
            primaryClinic.setCellValue(mortality.getPatient().getPrimaryClinic().getName());

            Cell dateOfDeath = mortalityRow.createCell(++count);
            if (mortality.getDateOfDeath() != null) {
                dateOfDeath.setCellValue(mortality.getDateOfDeath());
                dateOfDeath.setCellStyle(cellStyle);
            } else {
                dateOfDeath.setCellValue("");
            }
            Cell causeOfDeath = mortalityRow.createCell(++count);
            causeOfDeath.setCellValue(mortality.getCauseOfDeath() != null ? mortality.getCauseOfDeath().getName() : "");
            Cell causeOfDeathDetails = mortalityRow.createCell(++count);
            causeOfDeathDetails.setCellValue(mortality.getCauseOfDeathDetails());
            Cell receivingEnhancedCare = mortalityRow.createCell(++count);
            receivingEnhancedCare.setCellValue(mortality.getReceivingEnhancedCare() != null ? mortality.getReceivingEnhancedCare().getName() : "");
            Cell datePutOnEnhancedCare = mortalityRow.createCell(++count);
            if (mortality.getDatePutOnEnhancedCare() != null) {
                datePutOnEnhancedCare.setCellValue(mortality.getDatePutOnEnhancedCare());
                datePutOnEnhancedCare.setCellStyle(cellStyle);
            } else {
                datePutOnEnhancedCare.setCellValue("");
            }
            Cell caseBackground = mortalityRow.createCell(++count);
            caseBackground.setCellValue(mortality.getCaseBackground());
            Cell careProvided = mortalityRow.createCell(++count);
            careProvided.setCellValue(mortality.getCareProvided());
            Cell home = mortalityRow.createCell(++count);
            home.setCellValue(mortality.getHome());

            Cell beneficiary = mortalityRow.createCell(++count);
            beneficiary.setCellValue(mortality.getBeneficiary());
            Cell facility = mortalityRow.createCell(++count);
            facility.setCellValue(mortality.getFacility());
            Cell cats = mortalityRow.createCell(++count);
            cats.setCellValue(mortality.getCats());
            Cell zm = mortalityRow.createCell(++count);
            zm.setCellValue(mortality.getZm());
            Cell other = mortalityRow.createCell(++count);
            other.setCellValue(mortality.getOther());
            Cell contactWithZM = mortalityRow.createCell(++count);
            contactWithZM.setCellValue(mortality.getContactWithZM() != null ? mortality.getContactWithZM().getName() : "");
            Cell dateOfContactWithZim = mortalityRow.createCell(++count);
            if (mortality.getDateOfContactWithZim() != null) {
                dateOfContactWithZim.setCellValue(mortality.getDateOfContactWithZim());
                dateOfContactWithZim.setCellStyle(cellStyle);
            } else {
                dateOfContactWithZim.setCellValue("");
            }
            Cell descriptionOfCase = mortalityRow.createCell(++count);
            descriptionOfCase.setCellValue(mortality.getDescriptionOfCase());
            Cell learningPoints = mortalityRow.createCell(++count);
            learningPoints.setCellValue(mortality.getLearningPoints());
            Cell actionPlan = mortalityRow.createCell(++count);
            actionPlan.setCellValue(mortality.getActionPlan());

        }

        return workbook;
    }

}
