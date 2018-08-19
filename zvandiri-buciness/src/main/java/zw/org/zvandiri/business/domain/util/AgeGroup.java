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
public enum AgeGroup {
    
    UNDER_ONE(0, 1, "<1"), ONE_TO_NINE(1, 9, "1-9"), TEN_TO_FOURTEEN(10, 14, "10-14"),
    FIFTEEN_TO_NINETEEN(15, 19, "15-19"), TWENTY_TO_TWENTY_FOUR(20, 24, "20-24");
    
    private final Integer start;
    private final Integer end;
    private final String name;

    private AgeGroup(Integer start, Integer end, String name) {
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
    
    public static AgeGroup get(Integer start){
        switch(start){
            case 0:
                return UNDER_ONE;
            case 5:
                return ONE_TO_NINE;
            case 10:
                return TEN_TO_FOURTEEN;
            case 15:
                return FIFTEEN_TO_NINETEEN;
            case 20:
                return TWENTY_TO_TWENTY_FOUR;
            default :
                throw new IllegalArgumentException("Illegal parameter passed to method :"+start);
        }
    }
    
    public static Integer getAgeRange(int age){
        if(age >= 0 && age < 1){
            return 0;
        } else if(age >= 1 && age < 10){
            return 5;
        }else if(age >= 10 && age < 15){
            return 10;
        } else if(age >= 15 && age < 20){
            return 15;
        }
        return 20;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getAltName(){
        return start+"-"+end;
    }
}