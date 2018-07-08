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
import zw.org.zvandiri.business.domain.ArvAdverseEffect;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.repo.ArvAdverseEffectRepo;
import zw.org.zvandiri.business.service.ArvAdverseEffectService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.UUIDGen;

/**
 *
 * @author Judge Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ArvAdverseEffectServiceImpl implements ArvAdverseEffectService {
    
    @Resource
    private ArvAdverseEffectRepo arvAdverseEffectRepo;
    @Resource
    private UserService userService;

    @Override
    public List<ArvAdverseEffect> getAll() {
        return arvAdverseEffectRepo.findByActive(Boolean.TRUE);
    }

    @Override
    public ArvAdverseEffect get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item to be does not exist :" + id);
        }
        return arvAdverseEffectRepo.findOne(id);
    }

    @Override
    public void delete(ArvAdverseEffect t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        arvAdverseEffectRepo.delete(t);
    }

    @Override
    public List<ArvAdverseEffect> getPageable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArvAdverseEffect save(ArvAdverseEffect t) {
        if (t.getId() == null) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return arvAdverseEffectRepo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return arvAdverseEffectRepo.save(t);
    }

    @Override
    public List<ArvAdverseEffect> getByArvHist(ArvHist arvHist) {
        return arvAdverseEffectRepo.findByArvHist(arvHist);
    }

    @Override
    public Boolean checkDuplicate(ArvAdverseEffect current, ArvAdverseEffect old) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}