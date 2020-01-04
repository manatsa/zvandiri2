/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import zw.org.zvandiri.business.domain.HIVSelfTesting;
import zw.org.zvandiri.business.domain.Person;

/**
 *
 * @author tasu
 */
public interface HIVSelfTestingRepo extends AbstractRepo<HIVSelfTesting, String>{
    
    public List<HIVSelfTesting> findByPerson(Person person);
}
