/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 *
 * @author tasu
 */
public enum IdentifiedRisk {
    
    DEPRESSION(1), POST_TRAUMA(2), SUICIDAL(3), PSYCHOSIS(4);
    
    private final Integer code;

    private IdentifiedRisk(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    
    public final static IdentifiedRisk get(Integer code){
        for(IdentifiedRisk risk : values()){
            if(code.equals(risk.getCode())){
                return risk;
            }
        }
        throw new IllegalArgumentException("Parameter passed to method not recognized: " + code);
    }
    
    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}