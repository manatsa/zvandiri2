/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.repo.MentalHealthScreeningRepo;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.UUIDGen;

/**
 *
 * @author tasu
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class MentalHealthScreeningServiceImpl implements MentalHealthScreeningService{
    
    @Resource
    private MentalHealthScreeningRepo repo;
    @Resource
    private UserService userService;
    
    @Override
    public List<MentalHealthScreening> getAll() {
        return repo.findByActive(Boolean.TRUE);
    }

    @Override
    public MentalHealthScreening get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item to be does not exist :" + id);
        }
        return repo.findOne(id);
    }

    @Override
    public void delete(MentalHealthScreening t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        repo.delete(t);
    }

    @Override
    public List<MentalHealthScreening> getPageable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MentalHealthScreening save(MentalHealthScreening t) {
        if (t.getId() == null) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return repo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return repo.save(t);
    }

    @Override
    public Boolean checkDuplicate(MentalHealthScreening current, MentalHealthScreening old) {
        if (current.getId() != null) {
            /**
             * @param current is in existence
             */
            if (!current.getPatient().equals(old.getPatient())) {
                if (getByPatient(current.getPatient()) != null) {
                    return true;
                }
            }

        } else if (current.getId() == null) {
            /**
             * @param current is new
             */
            if (getByPatient(current.getPatient()) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MentalHealthScreening getByPatient(Patient patient) {
        return repo.findByPatient(patient);
    }
}
