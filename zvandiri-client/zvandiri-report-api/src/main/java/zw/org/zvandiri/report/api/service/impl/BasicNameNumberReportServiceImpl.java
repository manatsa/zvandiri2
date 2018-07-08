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

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Settings;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.SettingsService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.service.BasicNameNumberReportService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class BasicNameNumberReportServiceImpl implements BasicNameNumberReportService {
    
    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private SettingsService settingsService;
    @Resource
    private PatientReportService patientReportService;

    @Override
    public Map<String, Long> getHomeStats(SearchDTO dto) {
        Settings settings = settingsService.getItem();
        Map<String, Long> map = new HashMap<>();
        dto.setStatus(PatientChangeEvent.ACTIVE);
        map.put("ACTIVE", detailedPatientReportService.getCount(dto));
        dto.setStatus(PatientChangeEvent.DECEASED);
        map.put("DECEASED", detailedPatientReportService.getCount(dto));
        dto.setStatus(PatientChangeEvent.LOST_TO_FOLOWUP);
        map.put("LOST_TO_FOLOWUP", detailedPatientReportService.getCount(dto));
        dto.setStatus(PatientChangeEvent.OPT_OUT);
        map.put("OPT_OUT", detailedPatientReportService.getCount(dto));
        dto.setStatus(PatientChangeEvent.ACTIVE);
        dto.setMaxViralLoad(settings.getViralLoadMaxCount());
        map.put("VIRAL_LOAD_ABOVE_MINIMUM", patientReportService.getPatientLabResults(dto));
        dto.setMinCd4Count(settings.getCd4MinCount());
        map.put("CD4_COUNT_ABOVE_MINIMUM", patientReportService.getPatientWithCd4Count(dto));
        return map;
    }    
}