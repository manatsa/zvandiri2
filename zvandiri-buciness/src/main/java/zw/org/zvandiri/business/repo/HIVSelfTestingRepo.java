/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import zw.org.zvandiri.business.domain.HIVSelfTesting;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
public interface HIVSelfTestingRepo extends AbstractRepo<HIVSelfTesting, String>{
    public Long countByArtInitiation(YesNo artInitiation);
    
    @Query("select count(m) from HIVSelfTesting m where m.hisSelfTestingResult is not null")
    public Long countByHIvSelfTesting();
    
    @Query("select count(m) from HIVSelfTesting m where m.homeBasedTestingResult is not null")
    public Long countHomeBased();
    
    public List<HIVSelfTesting> findByPerson(Person person);
}
