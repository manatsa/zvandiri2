/*
 * Copyright 2017 jackie muzinda.
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.service.DataEntryService;

/**
 *
 * @author jackie muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DataEntryServiceImpl implements DataEntryService{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Long getCount(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        if(dto.getSearch(dto)){
            builder.append(" where ");
            if(dto.getCreatedBy() != null){
                if(position == 0){
                    builder.append("p.createdBy=:createdBy");
                    position++;
                }else{
                    builder.append(" and p.createdBy=:createdBy");
                }
            }
            if(dto.getUserType()!= null){
                if(position == 0){
                    builder.append("p.createdBy.userType=:userType");
                    position++;
                }else{
                    builder.append(" and p.createdBy.userType=:userType");
                }
            }
            if(dto.getStartDate() != null && dto.getEndDate() != null){
                if(position == 0){
                    builder.append("p.dateCreated between :startDate and :endDate");
                    position++;
                }else{
                    builder.append(" and p.dateCreated between :startDate and :endDate");
                }
            }
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district.province=:province");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province=:province");
                }
            }
            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district=:district");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if(dto.getCreatedBy() != null){
            query.setParameter("createdBy", dto.getCreatedBy());
        }
        if(dto.getUserType() != null){
            query.setParameter("userType", dto.getUserType());
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        return (Long) query.getSingleResult();
    }
    
    
}