/*
 * Copyright 2017 jmuzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.business.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.EidTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.EidTestRepo;
import zw.org.zvandiri.business.service.EidTestService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.UUIDGen;

/**
 *
 * @author jmuzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EidTestServiceImpl implements EidTestService {
    
    @Resource
    private EidTestRepo eidTestRepo;
    @Resource
    private UserService userService;

    @Override
    public List<EidTest> getAll() {
        return eidTestRepo.findByActive(Boolean.TRUE);
    }

    @Override
    public EidTest get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item to be does not exist :" + id);
        }
        return eidTestRepo.findById(id);
    }

    @Override
    public void delete(EidTest t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        eidTestRepo.delete(t);
    }

    @Override
    public List<EidTest> getPageable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EidTest save(EidTest t) {
        if (t.getId() == null) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return eidTestRepo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return eidTestRepo.save(t);
    }

    @Override
    public Boolean checkDuplicate(EidTest current, EidTest old) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EidTest> getByPatient(Patient patient) {
        return eidTestRepo.findByPatient(patient);
    }
}