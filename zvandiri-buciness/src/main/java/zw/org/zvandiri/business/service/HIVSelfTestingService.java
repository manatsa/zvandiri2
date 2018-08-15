/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.service;

import java.util.List;
import zw.org.zvandiri.business.domain.HIVSelfTesting;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
public interface HIVSelfTestingService extends GenericService<HIVSelfTesting>{
    public Long getOnArt(YesNo onArt);
    
    public Long countByHIvSelfTesting();
    
    public List<HIVSelfTesting> getByPerson(Person person);
}
