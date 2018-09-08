/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.util.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import zw.org.zvandiri.business.util.DateUtil;

/**
 *
 * @author jmuzinda
 */
public class QuarterMod implements Serializable {
    
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public QuarterMod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getName () {
        return DateUtil.periodFriendly.format(startDate)+" - "+DateUtil.periodFriendly.format(endDate);
    }

    @Override
    public String toString() {
        return "QuarterMod{" + "startDate=" + startDate + ", endDate=" + endDate + '}';
    }
    
}
