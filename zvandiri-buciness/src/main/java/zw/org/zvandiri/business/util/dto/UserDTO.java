/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.util.dto;

import java.io.Serializable;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.UserLevel;

/**
 *
 * @author tasu
 */
public class UserDTO implements Serializable{
    
    private String id;
    private Long version;
    private String firstName;
    private String lastName;
    private String userName;
    private UserLevel userLevel;
    private Province province;
    private District district;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
    
    public static UserDTO getInstance(User user) {
        UserDTO item = new UserDTO();
        item.setId(user.getId());
        item.setVersion(user.getVersion());
        item.setFirstName(user.getFirstName());
        item.setLastName(user.getLastName());
        item.setUserName(user.getUserName());
        item.setUserLevel(user.getUserLevel());
        item.setDistrict(user.getDistrict());
        item.setProvince(user.getProvince());
        return item;
    }
}
