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
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.PersonService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.PersonValidator;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/person")
public class PersonController extends BaseController{
    
    @Resource
    private PersonService personService;
    @Resource
    private PersonValidator validator;
    @Resource
    private ProvinceService provinceService;
    
    public String setUpModel(ModelMap map, Person item){
        map.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Person");
        map.addAttribute("item", item);
        map.addAttribute("gender", Gender.values());
        map.addAttribute("results", Result.values());
        map.addAttribute("yesNo", YesNo.values());
        map.addAttribute("provinces", provinceService.getAll());
        map.addAttribute("formAction", "item.form");
        return "patient/personForm";
    }
    
    @RequestMapping(value = "item.form", method = RequestMethod.GET)
    public String getForm(ModelMap map, @RequestParam(required = false) String id){
        Person person = new Person();
        if(id != null){
            person = personService.get(id);
        }
        return setUpModel(map, person);
    }
    
    @RequestMapping(value = "item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap map, @ModelAttribute("item") @Valid Person person, BindingResult result){
        validator.validate(person, result);
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if(result.hasErrors()){
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(map, person);
        }
        personService.save(person);
        return "redirect:item.list?type=1";
    }
    
    @RequestMapping(value = {"/", "item.list"}, method = RequestMethod.GET)
    public String getItemList(@RequestParam(required = false) Integer type, ModelMap map){
        map.addAttribute("pageTitle", APP_PREFIX + "Person List");
        map.addAttribute("items", personService.getAll());
        if (type != null) {
            map.addAttribute("message", AppMessage.getMessage(type));
        }
        return "patient/personList";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model){
        Person person = personService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, person.getNameOfClient(), "item.list");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX+"Delete "+person.getNameOfClient());
        return "admin/deleteItem";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteDistrict(@Valid ItemDeleteDTO dto){
        personService.delete(personService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}
