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
package zw.org.zvandiri.report.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Period;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.util.AgeGroup;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.CatDetailReportService;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ReferalReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.BasicNameNumber;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.Notifications;
import zw.org.zvandiri.report.api.service.ReferralReportAPIService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class ReferralReportAPIServiceImpl implements ReferralReportAPIService {

    @Resource
    private ReferalReportService referalReportService;
    @Resource
    private ContactReportService contactReportService;
    @Resource
    private PatientReportService patientReportService;
    @Resource
    private PeriodService periodService;
    @Resource
    private CatDetailReportService catDetailReportService;

    /*@Override
    public List<BasicNameNumber> getNotifications(SearchDTO dto) {
        List<BasicNameNumber> list = new ArrayList<>();
        int i = 0;
        for (String item : Notifications.labels) {
            switch (i) {
                case 0:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_WEEK.getEnd()));
                    dto.setEndDate(new Date());
                    Integer count = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/new-contacts-past-week.htm" : "#", 0, 0));
                    i++;
                    continue;
                case 1:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWO_WEEKS.getEnd()));
                    dto.setEndDate(new Date());
                    count = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/new-contacts-past-two-week.htm" : "#"));
                    i++;
                    continue;
                case 2:dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_MONTH.getEnd()));
                    dto.setEndDate(new Date());
                    count = patientReportService.getPatientWithNoContact(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/no-contacts-past-one-month" : "#"));
                    i++;
                    continue;
                case 3:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    dto.setEndDate(new Date());
                    count = patientReportService.getPatientWithNoContact(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/no-contacts-past-one-month" : "#"));
                    i++;
                    continue;
                case 4:
                    dto.setStartDate(DateUtil.getDateFromAge(24));
                    dto.setEndDate(DateUtil.getDateFromAge(23));
                    count = patientReportService.getPatientAboutToGraduate(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/patients-graduating-in-tweleve-months.htm" : "#"));
                    i++;
                    continue;
                case 5:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    dto.setEndDate(new Date());
                    count = patientReportService.getPatientWithNoContact(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >= 1 ? "report/notification/no-contacts-past-six-months.htm" : "#"));
                    i++;
                    continue;
                case 6:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    dto.setEndDate(new Date());
                    count = patientReportService.getPatientWithNoContact(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >=1 ? "report/notification/no-contacts-past-twelve-months.htm" : "#"));
                    i++;
                    continue;                    
                case 7:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_WEEK.getEnd()));
                    dto.setEndDate(new Date());
                    dto.setYesNo(YesNo.NO);
                    count = referalReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >=1 ? "report/notification/referrals-with-no-feedback-past-one-week.htm" : "#"));
                    i++;
                    continue;
                case 8:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWO_WEEKS.getEnd()));
                    dto.setEndDate(new Date());
                    dto.setYesNo(YesNo.NO);
                    count = referalReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, count, count >=1 ? "report/notification/referrals-with-no-feedback-past-two-week.htm" : "#"));
                    i++;
                    continue;
                default:
                    list.add(new BasicNameNumber(item, i, 0, 0));
                    i++;
            }
        }
        return list;
    }*/
    @Override
    public List<BasicNameNumber> getNotifications(SearchDTO dto) {
        List<BasicNameNumber> list = new ArrayList<>();
        int i = 0;
        Integer threeMonths = null;
        Integer sixMonths = null;
        Integer twelveMonths = null;
        dto.setStatus(PatientChangeEvent.ACTIVE);
        for (String item : Notifications.labels) {
            switch (i) {
                case 0:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 1:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = patientReportService.getPatientWithContact(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = patientReportService.getPatientWithContact(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = patientReportService.getPatientWithContact(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 2:
                    dto.setEndDate(DateUtil.getDateFromAge(23));
                    dto.setStartDate(DateUtil.getDateDiffDate(DateRangeItem.PAST_THREE_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    threeMonths = catDetailReportService.getPatientAboutToGraduate(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(DateRangeItem.PAST_SIX_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    sixMonths = catDetailReportService.getPatientAboutToGraduate(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(DateRangeItem.PAST_TWELVE_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    twelveMonths = catDetailReportService.getPatientAboutToGraduate(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 3:
                    dto.setReason(Reason.INTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.NO);
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 4:
                    dto.setReason(Reason.INTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.YES);
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 5:
                    dto.setReason(Reason.EXTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.NO);
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                case 6:
                    dto.setReason(Reason.EXTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.YES);
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "beneficiary.htm", sixMonths, twelveMonths));
                    i++;
                    continue;
                default:
                    list.add(new BasicNameNumber(item, i, 0, 0));
                    i++;
            }
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getPieDefaultData(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        for (AgeGroup item : AgeGroup.values()) {
            items.add(item.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        Long rowCount = 0L;
        row.add("Counts");
        for (AgeGroup item : AgeGroup.values()) {
            dto.setAgeGroup(item);
            Long itemCount = referalReportService.getCount(dto);
            row.add(itemCount.toString());
            rowCount += itemCount;
        }
        row.add(rowCount.toString());
        model.setRow(row);
        list.add(model);
        return list;
    }

    @Override
    public List<GenericReportModel> getPeriodRange(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<Period> periods = periodService.getPastPeriod(6);
        List<String> items = new ArrayList<>();
        items.add("");
        for (Period period : periods) {
            items.add(DateUtil.periodFriendly.format(period.getEndDate()));
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        row.add("Counts");
        Integer rowCount = 0;
        for (Period period : periods) {
            dto.setStartDate(period.getStartDate());
            dto.setEndDate(period.getEndDate());
            Integer itemCount = referalReportService.getCount(dto) != null ? referalReportService.getCount(dto).intValue() : 0;
            row.add(itemCount.toString());
            rowCount += itemCount;
        }
        row.add(rowCount.toString());
        model.setRow(row);
        list.add(model);
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        String[] headers = {"Name", "Age", "Gender", "Phone No.", "District", "Clinic",
            "Referral Date", "Organisation", "Action"};
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Referral item : referalReportService.get(dto.getInstance(dto))) {
            String[] inner = {
                item.getPatient().getName(),
                item.getPatient().getAge() + "",
                item.getPatient().getGender().getName(),
                item.getPatient().getMobileNumber(),
                item.getPatient().getPrimaryClinic().getDistrict().getName(),
                item.getPatient().getPrimaryClinic().getName(),
                DateUtil.getStringFromDate(item.getReferralDate()),
                item.getOrganisation(),
                item.getActionTaken() != null ? item.getActionTaken().getName() : ""
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
