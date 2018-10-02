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

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.util.dto.ContactDetailsDTO;
import zw.org.zvandiri.portal.util.MobileNumberFormat;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class ContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ContactDetailsDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "mobileNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "address", "field.empty");
        ContactDetailsDTO item = (ContactDetailsDTO) o;
        if (item.getMobileNumber() != null && !item.getMobileNumber().matches(MobileNumberFormat.ZIMBABWE)) {
            errors.rejectValue("mobileNumber", "mobileNumber.format");
        }
    }
}
