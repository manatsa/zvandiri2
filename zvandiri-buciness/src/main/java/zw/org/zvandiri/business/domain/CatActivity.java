/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.CatsMentorship;

/**
 *
 * @author jmuzinda
 */
@Entity
public class CatActivity extends BaseEntity {
    
    @ManyToOne
    private CatDetail catDetail;
    private String certificateNumber;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateIssued;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateReceivedMentorship;
    @Enumerated
    private CatsMentorship catsMentorship;
    @ManyToOne
    private District district;
    @Transient
    private Province province;
    @Column(columnDefinition = "text")
    private String description;

    public CatActivity() {
    }

    public CatActivity(CatDetail catDetail) {
        this.catDetail = catDetail;
    }

    public CatDetail getCatDetail() {
        return catDetail;
    }

    public void setCatDetail(CatDetail catDetail) {
        this.catDetail = catDetail;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateReceivedMentorship() {
        return dateReceivedMentorship;
    }

    public void setDateReceivedMentorship(Date dateReceivedMentorship) {
        this.dateReceivedMentorship = dateReceivedMentorship;
    }

    public CatsMentorship getCatsMentorship() {
        return catsMentorship;
    }

    public void setCatsMentorship(CatsMentorship catsMentorship) {
        this.catsMentorship = catsMentorship;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
