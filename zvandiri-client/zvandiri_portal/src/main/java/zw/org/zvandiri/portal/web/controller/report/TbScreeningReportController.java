/*
 * Copyright 2018 tasu.
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
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.TbScreeningReportReportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/report/tb-screening")
public class TbScreeningReportController extends BaseController{
    
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private TbScreeningReportReportService reportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private PeriodService periodService;

    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "TB Screening Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getActivePeriods());
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/tb-screening/screening/export/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("items", reportService.getDefaultReport(item.getInstance(item)));
    }

    @RequestMapping(value = "/screening", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/tbScreeningReport";
    }

    @RequestMapping(value = "/screening", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        setUpModel(model, item);
        return "report/tbScreeningReport";
    }

    @RequestMapping(value = "/screening/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("TB Screening Report");
        forceDownLoadXLSX(createTBIPTWorkbook(),"TBScreening", response);
    }


    public XSSFWorkbook createTBIPTWorkbook() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        List<TbIpt> tbIpts = new ArrayList<>();

        // tb Ipt here
        XSSFSheet tbIptDetails = workbook.createSheet("Patient_TBIPT");
        int tbIptRowNum = 0;
        XSSFRow tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
        int tbIptCellNum = 0;
        for (String title : DatabaseHeader.TB_IPT_HEADER) {
            XSSFCell cell = tbIptRow.createCell(tbIptCellNum++);
            cell.setCellValue(title);
        }

        for (TbIpt tbIpt : tbIpts) {
            int count = 0;
            tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
            XSSFCell id = tbIptRow.createCell(count);
            id.setCellValue(tbIpt.getPatient().getPatientNumber());
            XSSFCell patientName = tbIptRow.createCell(++count);
            patientName.setCellValue(tbIpt.getPatient().getName());
            XSSFCell dateOfBirth = tbIptRow.createCell(++count);
            dateOfBirth.setCellValue(tbIpt.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            XSSFCell age = tbIptRow.createCell(++count);
            age.setCellValue(tbIpt.getPatient().getAge());
            XSSFCell sex = tbIptRow.createCell(++count);
            sex.setCellValue(tbIpt.getPatient().getGender().getName());
            XSSFCell province = tbIptRow.createCell(++count);
            province.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = tbIptRow.createCell(++count);
            district.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = tbIptRow.createCell(++count);
            primaryClinic.setCellValue(tbIpt.getPatient().getPrimaryClinic().getName());

            XSSFCell screenedForTb = tbIptRow.createCell(++count);
            screenedForTb.setCellValue(tbIpt.getScreenedForTb() != null ? tbIpt.getScreenedForTb().getName() : "");
            XSSFCell dateScreened = tbIptRow.createCell(++count);
            if (tbIpt.getDateScreened() != null) {
                dateScreened.setCellValue(tbIpt.getDateScreened());
                dateScreened.setCellStyle(cellStyle);
            } else {
                dateScreened.setCellValue("");
            }
            XSSFCell tbSymptoms = tbIptRow.createCell(++count);
            tbSymptoms.setCellValue((tbIpt.getTbSymptoms() != null && tbIpt.getTbSymptoms().isEmpty())
                    ? tbIpt.getTbSymptoms().toString() : "");
            XSSFCell identifiedWithTb = tbIptRow.createCell(++count);
            identifiedWithTb.setCellValue(tbIpt.getIdentifiedWithTb() != null ? tbIpt.getIdentifiedWithTb().getName() : "");
            XSSFCell tbIdentificationOutcome = tbIptRow.createCell(++count);
            tbIdentificationOutcome.setCellValue(tbIpt.getTbIdentificationOutcome() != null ? tbIpt.getTbIdentificationOutcome().getName() : "");
            XSSFCell dateStartedTreatment = tbIptRow.createCell(++count);
            if (tbIpt.getDateStartedTreatment() != null) {
                dateStartedTreatment.setCellValue(tbIpt.getDateStartedTreatment());
                dateStartedTreatment.setCellStyle(cellStyle);
            } else {
                dateStartedTreatment.setCellValue("");
            }
            XSSFCell referralForSputum = tbIptRow.createCell(++count);
            referralForSputum.setCellValue(tbIpt.getReferralForSputum());
            XSSFCell tbTreatmentOutcome = tbIptRow.createCell(++count);
            tbTreatmentOutcome.setCellValue(tbIpt.getTbTreatmentOutcome() != null ? tbIpt.getTbTreatmentOutcome().getName() : "");
            XSSFCell referredForIpt = tbIptRow.createCell(++count);
            referredForIpt.setCellValue(tbIpt.getReferredForIpt() != null ? tbIpt.getReferredForIpt().getName() : "");
            XSSFCell onIpt = tbIptRow.createCell(++count);
            onIpt.setCellValue(tbIpt.getOnIpt() != null ? tbIpt.getOnIpt().getName() : "");
            XSSFCell dateStartedIpt = tbIptRow.createCell(++count);
            if (tbIpt.getDateStartedTreatment() != null) {
                dateStartedIpt.setCellValue(tbIpt.getDateStartedTreatment());
                dateStartedIpt.setCellStyle(cellStyle);
            } else {
                dateStartedIpt.setCellValue("");
            }
        }

        return workbook;
    }

}
