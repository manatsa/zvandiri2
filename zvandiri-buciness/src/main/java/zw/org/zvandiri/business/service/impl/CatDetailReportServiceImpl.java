/*
 * Copyright 2018 jmuzinda.
 /*
 * Copyright 2018 tasu.
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.service.CatDetailReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author tasu
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CatDetailReportServiceImpl implements CatDetailReportService{
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<CatDetail> getCats(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from CatDetail p");
        int position = 0;
        builder.append(" where ");
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
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.patient.gender=:gender");
                position++;
            } else {
                builder.append(" and p.patient.gender=:gender");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.patient.status=:status");
                position++;
            } else {
                builder.append(" and p.patient.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.patient.dateOfBirth between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and p.patient.dateOfBirth between :startDate and :endDate");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), CatDetail.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        List<CatDetail> list = query.getResultList();
        return list;
    }

    @Override
    public Long getPatientAboutToGraduate(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from CatDetail p");
        int position = 0;
        builder.append(" where ");
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
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.patient.gender=:gender");
                position++;
            } else {
                builder.append(" and p.patient.gender=:gender");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.patient.status=:status");
                position++;
            } else {
                builder.append(" and p.patient.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.patient.dateOfBirth between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and p.patient.dateOfBirth between :startDate and :endDate");
            }
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
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }
}
