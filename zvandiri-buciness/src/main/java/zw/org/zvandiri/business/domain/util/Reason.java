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
package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 *
 * @author Judge Muzinda
 */
public enum Reason {
 
    SELF_REFERRAL(1), EXTERNAL_REFERRAL(2), INTERNAL_REFERRAL(3), SCHEDULED_CONTACT(4), OTHER(5);
    
    private final Integer code;
    
    private Reason(Integer code){
        this.code = code;
    }
    
    public Integer getCode(){
        return code;
    }
    
    public static Reason get(Integer code) {
        for (Reason item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Un recognised code passed to method : " + code);
    }
    
    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}