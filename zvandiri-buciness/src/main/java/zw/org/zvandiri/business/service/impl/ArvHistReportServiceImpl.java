/*
 * Copyright 2016 Tasunungurwa Muzinda.
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
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.repo.ArvHistRepo;
import zw.org.zvandiri.business.service.ArvHistReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author Tasunungurwa Muzinda
 */
@Repository
@Transactional
public class ArvHistReportServiceImpl implements ArvHistReportService {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private ArvHistRepo arvHistRepo;

    @Override
    public Long getNewlyInitiatedOnART(SearchDTO dto) {
        
        // modify dates to be for past 3 months
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        dto.setEndDate(new Date());
        StringBuilder builder = new StringBuilder("Select count(Distinct a.patient.id) from ArvHist a");
        int position = 0;
        builder.append(" where ");
        if(dto.getProvince() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic.district.province=:province");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic.district.province=:province");
            }
        }
        if(dto.getDistrict() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic.district=:district");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic.district=:district");
            }
        }
        if(dto.getPrimaryClinic() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic=:primaryClinic");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic=:primaryClinic");
            }
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            if(position == 0){
                builder.append("a.startDate between :startDate and :endDate");
                position++;
            }else{
                builder.append(" and a.startDate between :startDate and :endDate");
            }
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if(dto.getProvince() != null){
            query.setParameter("province", dto.getProvince());
        }
        if(dto.getDistrict() != null){
            query.setParameter("district", dto.getDistrict());
        }
        if(dto.getPrimaryClinic() != null){
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        Long result = (Long) query.getSingleResult();
        return result == null ? 0 : result;
    }

    @Override
    public Long getNumberCurrentlyOnART(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(Distinct a.patient.id) from ArvHist a");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic=:primaryClinic");
            }
        }
        if (position == 0) {
            builder.append("a.active=:active");
            position++;
        } else {
            builder.append(" and a.active=:active");
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        query.setParameter("active", Boolean.TRUE);
        Long result = (Long) query.getSingleResult();
        return result == null ? 0 : result;
    }

    @Override
    public Long getOnARTForGivenPeriod(SearchDTO dto) {
        //dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
        dto.setEndDate(new Date());
        StringBuilder builder = new StringBuilder("Select count(Distinct a.patient.id) from ArvHist a");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("a.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and a.patient.primaryClinic=:primaryClinic");
            }
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            if(position == 0){
                builder.append("a.startDate between :startDate and :endDate");
                position++;
            }else{
                builder.append(" and a.startDate between :startDate and :endDate");
            }
        }
        if (position == 0) {
            builder.append("a.active=:active");
            position++;
        } else {
            builder.append(" and a.active=:active");
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        query.setParameter("active", Boolean.TRUE);
        Long result = (Long) query.getSingleResult();
        return result == null ? 0 : result;
    }
    
    @Override
    public Long getNumberExitingProgram(SearchDTO dto){
        StringBuilder builder = new StringBuilder("Select count(a) from ArvHist a");
        int position = 0;
        builder.append(" where ");
        if(dto.getProvince() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic.district.province=:province");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic.district.province=:province");
            }
        }
        if(dto.getDistrict() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic.district=:district");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic.district=:district");
            }
        }
        if(dto.getPrimaryClinic() != null){
            if(position == 0){
                builder.append("a.patient.primaryClinic=:primaryClinic");
                position++;
            }else{
                builder.append(" and a.patient.primaryClinic=:primaryClinic");
            }
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            if(position == 0){
                builder.append("a.endDate between :startDate and :endDate");
                position++;
            }else{
                builder.append(" and a.endDate between :startDate and :endDate");
            }
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if(dto.getProvince() != null){
            query.setParameter("province", dto.getProvince());
        }
        if(dto.getDistrict() != null){
            query.setParameter("district", dto.getDistrict());
        }
        if(dto.getPrimaryClinic() != null){
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if(dto.getStartDate() != null && dto.getEndDate() != null){
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        Long result = (Long) query.getSingleResult();
        return result == null ? 0 : result;
    }
    
    @Override
    public long count(){
        return arvHistRepo.count();
    }
}
