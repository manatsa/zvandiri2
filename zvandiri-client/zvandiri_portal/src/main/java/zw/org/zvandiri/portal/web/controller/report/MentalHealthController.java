package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.report.api.DatabaseHeader;

import java.util.ArrayList;
import java.util.List;

public class MentalHealthController {

    public Workbook createMentalHealthWorkbook()
    {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        List<MentalHealthScreening> mentalHealthScreenings=new ArrayList<MentalHealthScreening>();

        // mental health screening
        Sheet mentalHealthScreeningDetails = workbook.createSheet("Patient_Mental_Health_Screening");
        int mentalHealthScreeningRowNum = 0;
        Row mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);
        int mentalHealthScreeningCellNum = 0;
        for (String title : DatabaseHeader.MENTAL_HEALTH_SCREENING_HEADER) {
            Cell cell = mentalHealthScreeningRow.createCell(mentalHealthScreeningCellNum++);
            cell.setCellValue(title);
        }

        for (MentalHealthScreening mentalHealthScreening : mentalHealthScreenings) {
            int count = 0;
            mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);
            Cell id = mentalHealthScreeningRow.createCell(count);
            id.setCellValue(mentalHealthScreening.getPatient().getPatientNumber());
            Cell patientName = mentalHealthScreeningRow.createCell(++count);
            patientName.setCellValue(mentalHealthScreening.getPatient().getName());
            Cell dateOfBirth = mentalHealthScreeningRow.createCell(++count);
            dateOfBirth.setCellValue(mentalHealthScreening.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            Cell age = mentalHealthScreeningRow.createCell(++count);
            age.setCellValue(mentalHealthScreening.getPatient().getAge());
            Cell sex = mentalHealthScreeningRow.createCell(++count);
            sex.setCellValue(mentalHealthScreening.getPatient().getGender().getName());
            Cell province = mentalHealthScreeningRow.createCell(++count);
            province.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = mentalHealthScreeningRow.createCell(++count);
            district.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = mentalHealthScreeningRow.createCell(++count);
            primaryClinic.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getName());

            Cell screenedForMentalHealth = mentalHealthScreeningRow.createCell(++count);
            screenedForMentalHealth.setCellValue(mentalHealthScreening.getScreenedForMentalHealth() != null
                    ? mentalHealthScreening.getScreenedForMentalHealth().getName() : "");
            Cell dateScreened = mentalHealthScreeningRow.createCell(++count);
            if (mentalHealthScreening.getDateScreened() != null) {
                dateScreened.setCellValue(mentalHealthScreening.getDateScreened());
                dateScreened.setCellStyle(cellStyle);
            } else {
                dateScreened.setCellValue("");
            }
            Cell screening = mentalHealthScreeningRow.createCell(++count);
            screening.setCellValue(mentalHealthScreening.getScreening() != null ? mentalHealthScreening.getScreening().getName() : "");
            Cell risk = mentalHealthScreeningRow.createCell(++count);
            risk.setCellValue(mentalHealthScreening.getRisk() != null ? mentalHealthScreening.getRisk().getName() : "");
            Cell identifiedRisks = mentalHealthScreeningRow.createCell(++count);
            identifiedRisks.setCellValue((mentalHealthScreening.getIdentifiedRisks() != null && !mentalHealthScreening.getIdentifiedRisks().isEmpty())
                    ? mentalHealthScreening.getIdentifiedRisks().toString() : "");
            Cell support = mentalHealthScreeningRow.createCell(++count);
            support.setCellValue(mentalHealthScreening.getSupport() != null
                    ? mentalHealthScreening.getSupport().getName() : "");
            Cell supports = mentalHealthScreeningRow.createCell(++count);
            supports.setCellValue((mentalHealthScreening.getSupports() != null && !mentalHealthScreening.getSupports().isEmpty())
                    ? mentalHealthScreening.getSupports().toString() : "");
            Cell referral = mentalHealthScreeningRow.createCell(++count);
            referral.setCellValue(mentalHealthScreening.getReferral() != null
                    ? mentalHealthScreening.getReferral().getName() : "");
            Cell referrals1 = mentalHealthScreeningRow.createCell(++count);
            referrals1.setCellValue((mentalHealthScreening.getReferrals() != null && !mentalHealthScreening.getReferrals().isEmpty())
                    ? mentalHealthScreening.getReferrals().toString() : "");
            Cell diagnosis = mentalHealthScreeningRow.createCell(++count);
            diagnosis.setCellValue(mentalHealthScreening.getDiagnosis() != null
                    ? mentalHealthScreening.getDiagnosis().getName() : "");
            Cell diagnoses = mentalHealthScreeningRow.createCell(++count);
            diagnoses.setCellValue((mentalHealthScreening.getDiagnoses() != null && !mentalHealthScreening.getDiagnoses().isEmpty())
                    ? mentalHealthScreening.getDiagnoses().toString() : "");
            Cell otherDiagnosis = mentalHealthScreeningRow.createCell(++count);
            otherDiagnosis.setCellValue(mentalHealthScreening.getOtherDiagnosis());
            Cell intervention = mentalHealthScreeningRow.createCell(++count);
            intervention.setCellValue(mentalHealthScreening.getIntervention() != null
                    ? mentalHealthScreening.getIntervention().getName() : "");
            Cell interventions = mentalHealthScreeningRow.createCell(++count);
            interventions.setCellValue((mentalHealthScreening.getInterventions() != null && !mentalHealthScreening.getInterventions().isEmpty())
                    ? mentalHealthScreening.getInterventions().toString() : "");
            Cell otherIntervention = mentalHealthScreeningRow.createCell(++count);
            otherIntervention.setCellValue(mentalHealthScreening.getOtherIntervention());
        }
        return workbook;
    }
}
