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
package zw.org.zvandiri.portal.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.GenericPeriod;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.SupportGroup;
import zw.org.zvandiri.business.domain.util.PeriodType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.HalfYearPeriodService;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.QuarterPeriodService;
import zw.org.zvandiri.business.service.SupportGroupService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.service.YearPeriodService;
import zw.org.zvandiri.business.util.dto.NameIdDTO;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/global")
public class GlobalController {

    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private SupportGroupService supportGroupService;
    @Resource
    private PeriodService periodService;
    @Resource
    private QuarterPeriodService quarterPeriodService;
    @Resource
    private HalfYearPeriodService halfYearPeriodService;
    @Resource
    private YearPeriodService yearPeriodService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/getprovincedistricts", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<NameIdDTO> getProvinceDistricts(Province province) {
        return formatDistricts(districtService.getDistrictByProvince(province));
    }

    @RequestMapping(value = "/getdistrictstations", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<NameIdDTO> getDistrictStations(District district) {
        return formatStations(facilityService.getOptByDistrict(district));
    }

    @RequestMapping(value = "/checkloggedstatus", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Boolean checkLoggedStatus() {
        if (userService.getCurrentUser() != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @RequestMapping(value = "/getdistrictsupportgroups", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<NameIdDTO> getDistrictSupportGroups(District district) {
        return formatSupportGroups(supportGroupService.getByDistrict(district));
    }
    
    @RequestMapping(value="/getperiods", method= RequestMethod.GET)
    @ResponseBody
    public List<NameIdDTO> getPeriods(PeriodType periodType){        
        if(periodType.equals(PeriodType.MONTHLY)){
            return formatPeriod(periodService.getAll());
        }else if(periodType.equals(PeriodType.QUARTERLY)){
            return formatPeriod(quarterPeriodService.getAll());
        }else if(periodType.equals(PeriodType.HALF_YEARLY)){
            return formatPeriod(halfYearPeriodService.getAll());
        }else if(periodType.equals(PeriodType.YEARLY)){
            return formatPeriod(yearPeriodService.getAll());
        }
        return formatPeriod(periodService.getAll());
    }

    private List<NameIdDTO> formatDistricts(List<District> districts) {
        List<NameIdDTO> items = new ArrayList<>();
        for (District district : districts) {
            items.add(new NameIdDTO(district.getName(), district.getId()));
        }
        return items;
    }

    private List<NameIdDTO> formatStations(List<Facility> stations) {
        List<NameIdDTO> items = new ArrayList<>();
        for (Facility facility : stations) {
            items.add(new NameIdDTO(facility.getName(), facility.getId()));
        }
        return items;
    }

    private List<NameIdDTO> formatSupportGroups(List<SupportGroup> list) {
        List<NameIdDTO> items = new ArrayList<>();
        for (SupportGroup item : list) {
            items.add(new NameIdDTO(item.getName(), item.getId()));
        }
        return items;
    }
    
    private List<NameIdDTO> formatPeriod(List<? extends GenericPeriod> periods){
        List<NameIdDTO> items = new ArrayList<>();
        for(GenericPeriod p : periods){
            items.add(new NameIdDTO(p.getName(), p.getId()));
        }
        return items;
    }
}