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
package zw.org.zvandiri.portal.web.validator;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;

/**
 *
 * @author tasu
 */
@Component
public class MentalHealthScreeningValidator implements Validator{
    
    @Resource
    private MentalHealthScreeningService service;
    
    @Override
    public boolean supports(Class<?> type){
        return type.equals(MentalHealthScreening.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "screenedForMentalHealth", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "identifiedRisk", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "actionTaken", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "mentalScreenResult", "field.empty");
        MentalHealthScreening item = (MentalHealthScreening) o;
        MentalHealthScreening old = null;
        if(item.getPatient() != null){
            old = service.getByPatient(item.getPatient());
        }
        if (service.checkDuplicate(item, old)) {
            errors.rejectValue("uuid", "patient.mentalhealthscreenrecord");
        }
    }
    
}
