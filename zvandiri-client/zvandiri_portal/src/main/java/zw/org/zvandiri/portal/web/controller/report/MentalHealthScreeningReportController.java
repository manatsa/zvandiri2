package zw.org.zvandiri.portal.web.controller.report;


import org.apache.poi.xssf.usermodel.*;
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
import javax.swing.JOptionPane;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/mental-health")
public class MentalHealthScreeningReportController extends BaseController {

    @Resource
    private MentalHealthScreeningService mentalHealthScreeningService;
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

    
    List<MentalHealthScreening> mentalHealthScreenings=new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, Boolean post, Boolean hei) {
        SearchDTO item2 = getUserLevelObjectState(item);
        
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getAll());
        if (item2.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item2.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }


        if (post) {
            //JOptionPane.showMessageDialog(null, item2.toString());
        //System.err.println("+++++++++++++++++++++"+item2+"*********************************************************");
            model.addAttribute("excelExport", "/report/mental-health/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", mentalHealthScreenings);
        }


        model.addAttribute("item", item.getInstance(item2));
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getMentalRangeIndex(ModelMap model, SearchDTO dto) {
        dto = getUserLevelObjectState(dto);
        model.addAttribute("pageTitle", APP_PREFIX + "Mental Health  Report");
        Boolean post = Boolean.TRUE;
        if (dto.getStatus() != null && dto.getStatus().equals(PatientChangeEvent.ACTIVE) && (dto.getMaxViralLoad() == null && dto.getMinCd4Count() == null)) {
            post = Boolean.FALSE;
        }
        setUpModel(model, dto, post, Boolean.FALSE);
        return "report/mentalHealthScreening";
    }



    @RequestMapping(value = {"/range"}, method = RequestMethod.POST)
    public String getMentalRangeIndexPost(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        item = getUserLevelObjectState(item);
        mentalHealthScreenings=mentalHealthScreeningService.get(item);
        //System.err.println("+++++++++++++++++++++"+item+"*********************************************************");
        model.addAttribute("pageTitle", APP_PREFIX + "Mental Health  Report");
        setUpModel(model, item, Boolean.TRUE, Boolean.FALSE);
        return "report/mentalHealthScreening";
    }



    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Mental_Health_Report");
        forceDownLoadDatabase(createMentalHealthWorkbook(), name, response);
    }



    public XSSFWorkbook createMentalHealthWorkbook()
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        

        // mental health screening
        XSSFSheet mentalHealthScreeningDetails = workbook.createSheet("Patient_Mental_Health_Screening");
        int mentalHealthScreeningRowNum = 0;
        XSSFRow mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);
        int mentalHealthScreeningXSSFCellNum = 0;
        for (String title : DatabaseHeader.MENTAL_HEALTH_SCREENING_HEADER) {
            XSSFCell XSSFCell = mentalHealthScreeningRow.createCell(mentalHealthScreeningXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }

        for (MentalHealthScreening mentalHealthScreening : mentalHealthScreenings) {
            int count = 0;
            mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);

            XSSFCell id = mentalHealthScreeningRow.createCell(count);
            id.setCellValue(mentalHealthScreening.getPatient().getPatientNumber());

            XSSFCell patientName = mentalHealthScreeningRow.createCell(++count);
            patientName.setCellValue(mentalHealthScreening.getPatient().getName());

            XSSFCell dateOfBirth = mentalHealthScreeningRow.createCell(++count);
            dateOfBirth.setCellValue(mentalHealthScreening.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(XSSFCellStyle);

            XSSFCell age = mentalHealthScreeningRow.createCell(++count);
            age.setCellValue(mentalHealthScreening.getPatient().getAge());

            XSSFCell sex = mentalHealthScreeningRow.createCell(++count);
            sex.setCellValue(mentalHealthScreening.getPatient().getGender().getName());

            XSSFCell cat = mentalHealthScreeningRow.createCell(++count);
            cat.setCellValue(mentalHealthScreening.getPatient().getCat()!=null?mentalHealthScreening.getPatient().getCat().getName():"");

            XSSFCell ymm = mentalHealthScreeningRow.createCell(++count);
            cat.setCellValue(mentalHealthScreening.getPatient().getYoungMumGroup()!=null?mentalHealthScreening.getPatient().getYoungMumGroup().getName():"");

            XSSFCell province = mentalHealthScreeningRow.createCell(++count);
            province.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());

            XSSFCell district = mentalHealthScreeningRow.createCell(++count);
            district.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getName());

            XSSFCell primaryClinic = mentalHealthScreeningRow.createCell(++count);
            primaryClinic.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getName());

            XSSFCell screenedForMentalHealth = mentalHealthScreeningRow.createCell(++count);
            screenedForMentalHealth.setCellValue(mentalHealthScreening.getScreenedForMentalHealth() != null
                    ? mentalHealthScreening.getScreenedForMentalHealth().getName() : "");

            XSSFCell dateScreened = mentalHealthScreeningRow.createCell(++count);
            if (mentalHealthScreening.getDateScreened() != null) {
                dateScreened.setCellValue(mentalHealthScreening.getDateScreened());
                dateScreened.setCellStyle(XSSFCellStyle);
            } else {
                dateScreened.setCellValue("");
            }

            XSSFCell screening = mentalHealthScreeningRow.createCell(++count);
            screening.setCellValue(mentalHealthScreening.getScreening() != null ? mentalHealthScreening.getScreening().getName() : "");
            XSSFCell risk = mentalHealthScreeningRow.createCell(++count);
            risk.setCellValue(mentalHealthScreening.getRisk() != null ? mentalHealthScreening.getRisk().getName() : "");
//            XSSFCell identifiedRisks = mentalHealthScreeningRow.createCell(++count);
//            identifiedRisks.setCellValue((mentalHealthScreening.getIdentifiedRisks() != null && !mentalHealthScreening.getIdentifiedRisks().isEmpty())
//                    ? mentalHealthScreening.getIdentifiedRisks().toString() : "");
            XSSFCell support = mentalHealthScreeningRow.createCell(++count);
            support.setCellValue(mentalHealthScreening.getSupport() != null
                    ? mentalHealthScreening.getSupport().getName() : "");
            XSSFCell supports = mentalHealthScreeningRow.createCell(++count);
            supports.setCellValue((mentalHealthScreening.getSupports() != null && !mentalHealthScreening.getSupports().isEmpty())
                    ? mentalHealthScreening.getSupports().toString() : "");
            XSSFCell referral = mentalHealthScreeningRow.createCell(++count);
            referral.setCellValue(mentalHealthScreening.getReferral() != null
                    ? mentalHealthScreening.getReferral().getName() : "");
            XSSFCell referrals1 = mentalHealthScreeningRow.createCell(++count);
            referrals1.setCellValue((mentalHealthScreening.getReferrals() != null && !mentalHealthScreening.getReferrals().isEmpty())
                    ? mentalHealthScreening.getReferrals().toString() : "");
            XSSFCell diagnosis = mentalHealthScreeningRow.createCell(++count);
            diagnosis.setCellValue(mentalHealthScreening.getDiagnosis() != null
                    ? mentalHealthScreening.getDiagnosis().getName() : "");
            XSSFCell diagnoses = mentalHealthScreeningRow.createCell(++count);
            diagnoses.setCellValue((mentalHealthScreening.getDiagnoses() != null && !mentalHealthScreening.getDiagnoses().isEmpty())
                    ? mentalHealthScreening.getDiagnoses().toString() : "");
            XSSFCell otherDiagnosis = mentalHealthScreeningRow.createCell(++count);
            otherDiagnosis.setCellValue(mentalHealthScreening.getOtherDiagnosis());
            XSSFCell intervention = mentalHealthScreeningRow.createCell(++count);
            intervention.setCellValue(mentalHealthScreening.getIntervention() != null
                    ? mentalHealthScreening.getIntervention().getName() : "");
            XSSFCell interventions = mentalHealthScreeningRow.createCell(++count);
            interventions.setCellValue((mentalHealthScreening.getInterventions() != null && !mentalHealthScreening.getInterventions().isEmpty())
                    ? mentalHealthScreening.getInterventions().toString() : "");
            XSSFCell otherIntervention = mentalHealthScreeningRow.createCell(++count);
            otherIntervention.setCellValue(mentalHealthScreening.getOtherIntervention());
        }
        return workbook;
    }
}
