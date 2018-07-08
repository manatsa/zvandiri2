/*
 * Copyright 2016 Judge Muzinda.
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
import zw.org.zvandiri.business.domain.CD4Count;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.CD4CountRepo;
import zw.org.zvandiri.business.service.CD4CountService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.UUIDGen;

/**
 *
 * @author Judge Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CD4CountServiceImpl implements CD4CountService {

    @Resource
    private CD4CountRepo cD4CountRepo;
    @Resource
    private UserService userService;

    @Override
    public List<CD4Count> getAll() {
        return cD4CountRepo.findByActive(Boolean.TRUE);
    }

    @Override
    public CD4Count get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item to be does not exist :" + id);
        }
        return cD4CountRepo.findById(id);
    }

    @Override
    public void delete(CD4Count t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        cD4CountRepo.delete(t);
    }

    @Override
    public List<CD4Count> getPageable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CD4Count save(CD4Count t) {
        if (t.getId() == null) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return cD4CountRepo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return cD4CountRepo.save(t);
    }

    @Override
    public Boolean checkDuplicate(CD4Count current, CD4Count old) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CD4Count> getByPatient(Patient patient) {
        return cD4CountRepo.findByPatient(patient);
    }
}
