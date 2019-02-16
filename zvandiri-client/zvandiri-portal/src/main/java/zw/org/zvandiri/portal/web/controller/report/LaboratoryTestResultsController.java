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
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;

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
        model.addAttribute("excelExport", "/report/detailed/export/excel" + item.getQueryString(item.getInstance(item)));
        if (post) {
            model.addAttribute("items", patientReportService.getPatientLabResultsList(item.getInstance(item)));
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/detailedPatientReport";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type) {
        return setUpModel(model, new SearchDTO(), type, false);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        return setUpModel(model, item, type, true);
    }
}
