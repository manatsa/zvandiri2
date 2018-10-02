/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain.util;

/**
 *
 * @author jmuzinda
 */
public enum DisabilitySeverity {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
    
    private final Integer code;
    
    private DisabilitySeverity(Integer code) {
        this.code = code;
    }
    
    public Integer getCode () {
        return code;
    }
    
    public static DisabilitySeverity get(Integer code) {
        for(DisabilitySeverity item : values()) {
            if(code.equals(item.getCode())){
                return item;
            }
        }
        throw new IllegalArgumentException("Illegal parameter passed to method : "+ code);
    }
}
