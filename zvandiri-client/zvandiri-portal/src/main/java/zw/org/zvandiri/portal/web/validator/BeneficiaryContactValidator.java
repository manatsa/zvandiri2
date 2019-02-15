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
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.service.ContactService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class BeneficiaryContactValidator implements Validator {

    @Resource
    private ContactService contactService;
    
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Contact.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contact item = (Contact) o;
        ValidationUtils.rejectIfEmpty(errors, "contactDate", "field.empty");
        if (item.getCareLevel() == null) {
            errors.rejectValue("careLevel", "field.empty");
        }
        if (item.getLocation() == null) {
            errors.rejectValue("location", "field.empty");
        }
        if (item.getPosition() == null) {
            errors.rejectValue("position", "field.empty");
        }
        if (item.getReason() == null) {
            errors.rejectValue("reason", "field.empty");
        }
        if (item.getFollowUp() == null) {
            errors.rejectValue("followUp", "field.empty");
        }
        if (item.getActionTaken() == null) {
            errors.rejectValue("actionTaken", "field.empty");
        }
        if (item.getReason() != null) {
            if (item.getReason().equals(Reason.INTERNAL_REFERRAL) && item.getInternalReferral() == null) {
                errors.rejectValue("internalReferral", "field.empty");
            }
            if (item.getReason().equals(Reason.EXTERNAL_REFERRAL) && item.getExternalReferral() == null) {
                errors.rejectValue("externalReferral", "field.empty");
            }
        }
        if (item.getFollowUp() != null) {
            if (item.getFollowUp().equals(FollowUp.STABLE) && item.getStables() == null) {
                errors.rejectValue("stables", "item.select.one");
            }
            if (item.getFollowUp().equals(FollowUp.ENHANCED) && item.getEnhanceds() == null) {
                errors.rejectValue("enhanceds", "item.select.one");
            }
        }
        if (item.getAssessments() == null) {
            errors.rejectValue("assessments", "field.empty");
        }
        if (item.getContactDate() != null && item.getContactDate().after(new Date())) {
            errors.rejectValue("contactDate", "date.aftertoday");
        }
        if (item.getContactDate() != null && item.getPatient().getDateOfBirth() != null && item.getContactDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("contactDate", "date.beforebirth");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getLastClinicAppointmentDate().after(new Date())) {
            errors.rejectValue("lastClinicAppointmentDate", "date.aftertoday");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getPatient().getDateOfBirth() != null && item.getLastClinicAppointmentDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("lastClinicAppointmentDate", "date.beforebirth");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getAttendedClinicAppointment() == null) {
            errors.rejectValue("attendedClinicAppointment", "field.empty");
        }
        if (item.getActionTaken() != null && item.getActionTaken().getName().equalsIgnoreCase("Internal Referral") 
                && item.getReferredPerson() == null) {
            errors.rejectValue("referredPerson", "field.empty");
        }
    }
    
}
