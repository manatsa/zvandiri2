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
package zw.org.zvandiri.portal.web.validator;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.ChronicInfectionItem;
import zw.org.zvandiri.business.service.ChronicInfectionItemService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class ChronicInfectionItemValidator implements Validator {

    @Resource
    private ChronicInfectionItemService chronicInfectionItemService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ChronicInfectionItem.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "infectionDate", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "medication", "field.empty");
        ChronicInfectionItem item = (ChronicInfectionItem) o;
        ChronicInfectionItem old = null;
        if (item.getPatient() != null && item.getChronicInfection() != null) {
            if (item.getPatient() != null) {
                old = chronicInfectionItemService.getByPatientAndChronicInfection(item.getPatient(), item.getChronicInfection());
            }
            if (chronicInfectionItemService.checkDuplicate(item, old)) {
                errors.rejectValue("uuid", "patient.chronicinfectionitemrecord");
            }
        }
        if (item.getChronicInfection() == null) {
            errors.rejectValue("chronicInfection", "field.empty");
        }
        if (item.getCurrentStatus() == null) {
            errors.rejectValue("currentStatus", "field.empty");
        }
        if (item.getInfectionDate() != null && item.getInfectionDate().after(new Date())) {
            errors.rejectValue("infectionDate", "date.aftertoday");
        }
        if (item.getInfectionDate() != null && item.getPatient().getDateOfBirth() != null && item.getInfectionDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("infectionDate", "date.beforebirth");
        }
    }
}
