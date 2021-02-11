/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.ServiceOffered;

/**
 *
 * @author jmuzinda
 */
public interface ServiceOfferedRepo  extends AbstractNameDescRepo<ServiceOffered, String> {
    
    @Query("from ServiceOffered e left join fetch e.createdBy left join fetch e.modifiedBy where e.active=:active")
    public List<ServiceOffered> getOptAll(@Param("active") Boolean active);
}