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

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.EducationLevel;
import zw.org.zvandiri.business.service.EducationLevelService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class EducationLevelValidator implements Validator {

    @Resource
    private EducationLevelService educationLevelService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(EducationLevel.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "field.empty");
        /**
         * @param name item name must always be unique
         */
        EducationLevel current = (EducationLevel) o;
        EducationLevel old = null;
        if (current.getId() != null) {
            old = educationLevelService.get(current.getId());
        }
        if (educationLevelService.checkDuplicate(current, old)) {
            errors.rejectValue("name", "item.duplicate");
        }
    }

}
