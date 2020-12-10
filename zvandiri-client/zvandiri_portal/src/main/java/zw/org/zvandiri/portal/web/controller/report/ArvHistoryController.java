package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.ContactRepo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.PatientInnerJoin;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/report/arvhist")
public class ArvHistoryController extends BaseController {
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ContactReportService contactReportService;

    @Resource
    private DetailedPatientReportService detailedPatientReportService;

    @PersistenceContext
    private EntityManager entityManager;



    List<Contact> contacts=new ArrayList<>();
    List<ArvHist> arvHists = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "ARVHist Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/arvhist/export/excel" + item.getQueryString(item.getInstance(item)));
//            System.err.println("*********************************************"+item.toString());
            //contacts=contactReportService.get(item.getInstance(item));
            model.addAttribute("items",arvHists );
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/arvHistDetailedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReferralReportIndex(HttpServletResponse response, ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item=getUserLevelObjectState(item);

            arvHists=getPatients(item);

        return setUpModel(model, item, true);
    }

    @RequestMapping(value="/export/excel", method = RequestMethod.GET)
    public void downloadAll(HttpServletResponse response, @ModelAttribute("item") @Valid SearchDTO item){
        String name = DateUtil.getFriendlyFileName("Detailed_ARVHist_Report");
        forceDownLoadDatabase(getARVHistoryWorkbook(item), name, response);
    }



    public XSSFWorkbook getARVHistoryWorkbook(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        XSSFSheet arvHistDetails = workbook.createSheet("ARV_HISTORY");
        int arvHistXSSFRowNum = 0;
        XSSFRow arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
        int arvHistXSSFCellNum = 0;
        for (String title : DatabaseHeader.ARV_HISTORY_HEADER) {
            XSSFCell XSSFCell = arvHistXSSFRow.createCell(arvHistXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }


            for (ArvHist arvHist : arvHists) {
                int count = 0;
                arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
                XSSFCell id = arvHistXSSFRow.createCell(count);
                id.setCellValue(arvHist.getPatient().getPatientNumber());
                XSSFCell patientName = arvHistXSSFRow.createCell(++count);
                patientName.setCellValue(arvHist.getPatient().getName());
                XSSFCell dateOfBirth = arvHistXSSFRow.createCell(++count);
                dateOfBirth.setCellValue(arvHist.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(XSSFCellStyle);
                XSSFCell age = arvHistXSSFRow.createCell(++count);
                age.setCellValue(arvHist.getPatient().getAge());
                XSSFCell sex = arvHistXSSFRow.createCell(++count);
                sex.setCellValue(arvHist.getPatient().getGender().getName());

                XSSFCell cat = arvHistXSSFRow.createCell(++count);
                cat.setCellValue(arvHist.getPatient().getCat().getName());
                XSSFCell ymm = arvHistXSSFRow.createCell(++count);
                ymm.setCellValue((arvHist.getPatient().getYoungMumGroup()!=null)?arvHist.getPatient().getYoungMumGroup().getName():"");

                XSSFCell province = arvHistXSSFRow.createCell(++count);
                province.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                XSSFCell district = arvHistXSSFRow.createCell(++count);
                district.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getName());
                XSSFCell primaryClinic = arvHistXSSFRow.createCell(++count);
                primaryClinic.setCellValue(arvHist.getPatient().getPrimaryClinic().getName());

                XSSFCell arvHistMedicine = arvHistXSSFRow.createCell(++count);
                arvHistMedicine.setCellValue(arvHist.getMedicines());
                XSSFCell startDate = arvHistXSSFRow.createCell(++count);
                if (arvHist.getStartDate() != null) {
                    startDate.setCellValue(arvHist.getStartDate());
                    startDate.setCellStyle(XSSFCellStyle);
                } else {
                    startDate.setCellValue("");
                }
                XSSFCell endDate = arvHistXSSFRow.createCell(++count);
                if (arvHist.getEndDate() != null) {
                    endDate.setCellValue(arvHist.getEndDate());
                    endDate.setCellStyle(XSSFCellStyle);
                } else {
                    endDate.setCellValue("");
                }
            }


        return workbook;
    }

    private List<ArvHist> getPatients(SearchDTO dto)
    {
        StringBuilder builder=new StringBuilder("select distinct a from ArvHist a left join  a.patient as p  ");
        int position = 0;

        if (dto.getSearch(dto)) {
            builder.append(" where ");
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district.province=:province");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province=:province");
                }
            }
            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district=:district");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }


            if (dto.getStartDate() != null && dto.getEndDate() != null) {

                if (position == 0) {
                    builder.append(" (a.dateCreated between :startDate and  :endDate)");
                    position++;
                } else {
                    builder.append(" and (a.dateCreated between :startDate and :endDate)");
                }

            }


        }

        builder.append(" )");
        //builder.append(" Order By p.first_name, p.last_name DESC");
        Query query = entityManager.createQuery(builder.toString(), ArvHist.class);

        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }

        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }


        return query.getResultList();
    }
}
