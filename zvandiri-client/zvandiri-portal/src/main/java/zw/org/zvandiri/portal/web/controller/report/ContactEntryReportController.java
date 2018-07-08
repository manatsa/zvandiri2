/*
 * Copyright 2017 User.
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
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.ContactEntryReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/report/aggregate")
public class ContactEntryReportController extends BaseController{
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private ContactEntryReportService contactEntryReportService;

    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Contact Entry Report");
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/aggregate/contact-entry/export/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("items", contactEntryReportService.getDefaultReport(item.getInstance(item)));
    }

    @RequestMapping(value = "/contact-entry", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/dataEntryReport";
    }

    @RequestMapping(value = "/contact-entry", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        setUpModel(model, item);
        return "report/dataEntryReport";
    }

    @RequestMapping(value = "/contact-entry/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Contact Entry Report");
        forceDownLoad(officeExportService.exportExcelFile(contactEntryReportService.getDefaultReport(item.getInstance(item)), name), name, response);
    }
}
