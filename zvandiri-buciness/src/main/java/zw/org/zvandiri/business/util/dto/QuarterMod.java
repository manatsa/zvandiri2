/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    public List<QuarterMod> getPastFourQuarters() {
        List<QuarterMod> quarters = new ArrayList<>();
        // get first factor
        Date currentDate = new Date();
        // first quarter date element
        QuarterMod firstElement = new QuarterMod(
                DateUtil.getQuarter(currentDate, getQuarterFactor(currentDate), Boolean.TRUE), 
                DateUtil.getQuarter(currentDate, getQuarterFactor(currentDate), Boolean.FALSE));
        quarters.add(firstElement);
        // modify last quarter plus 10 days just in case
        Date secondPastQuarterDate = DateUtil.getDateDiffDate(-20, firstElement.startDate);
        QuarterMod secondElement = new QuarterMod(
                DateUtil.getQuarter(secondPastQuarterDate, getQuarterFactor(secondPastQuarterDate), Boolean.TRUE), 
                DateUtil.getQuarter(secondPastQuarterDate, getQuarterFactor(secondPastQuarterDate), Boolean.FALSE));
        quarters.add(secondElement);
        // modify last quarter plus 10 days just in case
        Date thirdPastQuarterDate = DateUtil.getDateDiffDate(-20, secondElement.startDate);
        QuarterMod thirdElement = new QuarterMod(
                DateUtil.getQuarter(thirdPastQuarterDate, getQuarterFactor(thirdPastQuarterDate), Boolean.TRUE), 
                DateUtil.getQuarter(thirdPastQuarterDate, getQuarterFactor(thirdPastQuarterDate), Boolean.FALSE));
        quarters.add(thirdElement);
        // modify last quarter plus 10 days just in case
        Date fourthPastQuarterDate = DateUtil.getDateDiffDate(-20, thirdElement.startDate);
        QuarterMod fourthElement = new QuarterMod(
                DateUtil.getQuarter(fourthPastQuarterDate, getQuarterFactor(fourthPastQuarterDate), Boolean.TRUE), 
                DateUtil.getQuarter(fourthPastQuarterDate, getQuarterFactor(fourthPastQuarterDate), Boolean.FALSE));
        quarters.add(fourthElement);
        return quarters;
    }
    
    private int getQuarterFactor(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (date.equals(getFirstStartDate(date)) || date.equals(getFirstEndDate(date)) || (date.after(getFirstStartDate(date)) && date.before(getFirstEndDate(date)))) {
            return 1;
        } else if (date.equals(getSecondStartDate(date)) || date.equals(getSecondEndDate(date)) || (date.after(getSecondStartDate(date)) && date.before(getSecondEndDate(date)))) {
            return 2;
        } else if (date.equals(getThirdStartDate(date)) || date.equals(getThirdEndDate(date)) || (date.after(getThirdStartDate(date)) && date.before(getThirdEndDate(date)))) {
            return 3;
        } else if (date.equals(getFourthStartDate(date)) || date.equals(getFourthEndDate(date)) || (date.after(getFourthStartDate(date)) && date.before(getFourthEndDate(date)))) {
            return 4;
        }
        throw new IllegalArgumentException("Date not fitting into any given range");
    }

    private Date getFirstStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.JANUARY, 01);
        return cal.getTime();
    }

    private Date getFirstEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.MARCH, 31);
        return cal.getTime();
    }

    private Date getSecondStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.APRIL, 01);
        return cal.getTime();
    }

    private Date getSecondEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.JUNE, 30);
        return cal.getTime();
    }

    private Date getThirdStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.JULY, 01);
        return cal.getTime();
    }

    private Date getThirdEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.SEPTEMBER, 30);
        return cal.getTime();
    }

    private Date getFourthStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.OCTOBER, 01);
        return cal.getTime();
    }

    private Date getFourthEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        return cal.getTime();
    }

    @Override
    public String toString() {
        return "QuarterMod{" + "startDate=" + startDate + ", endDate=" + endDate + '}';
    }
    
}