/*
 * Copyright 2017 jmuzinda.
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
package zw.org.zvandiri.portal.web.controller.patient;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.EidTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.EidTestService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.EidTestValidator;

/**
 *
 * @author jmuzinda
 */
@Controller
@RequestMapping("/patient/eid")
public class EidTestController extends BaseController {
    
    @Resource
    private EidTestService eidTestService;
    @Resource
    private PatientService patientService;
    @Resource
    private EidTestValidator eidTestValidator;

    public String setUpModel(ModelMap model, EidTest item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s EID Details");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        model.addAttribute("viralLoad", Boolean.FALSE);
        model.addAttribute("eid", Boolean.TRUE);
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/labResultForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String itemId) {
        EidTest item;
        if (itemId != null) {
            item = eidTestService.get(itemId);
            return setUpModel(model, item);
        }
        item = new EidTest(patientService.get(patientId));
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid EidTest item, BindingResult result) {
        eidTestValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        eidTestService.save(item);
        return "redirect:../dashboard/profile.htm?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        EidTest item = eidTestService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : EID Detail", "../dashboard/profile.htm?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        EidTest item = eidTestService.get(dto.getId());
        Patient patient = item.getPatient();
        //eidTestService.delete(item);
        return "redirect:../dashboard/profile.htm?type=2&id=" + patient.getId();
    }
}