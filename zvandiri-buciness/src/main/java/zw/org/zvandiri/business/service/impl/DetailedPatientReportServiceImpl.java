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

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.PatientInnerJoin;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author Judge Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DetailedPatientReportServiceImpl implements DetailedPatientReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Patient> get(SearchDTO dto, Integer firstResult) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Patient p " + PatientInnerJoin.PATIENT_INNER_JOIN);
        int position = 0;
        if (dto.getSearch(dto)) {
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
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }
            if (dto.getAgeGroup() != null) {
                if (position == 0) {
                    builder.append("p.dateOfBirth between :start and :end");
                    position++;
                } else {
                    builder.append(" and p.dateOfBirth between :start and :end");
                }
            }
            if (dto.getPeriod() != null) {
                if (position == 0) {
                    builder.append("p.period=:period");
                    position++;
                } else {
                    builder.append(" and p.period=:period");
                }
            }
            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append("p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
            if (dto.getHei() != null) {
                if (position == 0) {
                    builder.append("p.hei=:hei");
                    position++;
                } else {
                    builder.append(" and p.hei=:hei");
                }
            }
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                if (position == 0) {
                    builder.append("p.dateJoined between :startDate and :endDate");
                    position++;
                } else {
                    builder.append(" and (p.dateJoined between :startDate and :endDate)");
                }
            }
        }
        //builder.append(" order by p.lastName, p.firstName, p.middleName ASC");
        TypedQuery<Patient> query = entityManager.createQuery(builder.toString(), Patient.class);
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
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getHei() != null) {
            query.setParameter("hei", dto.getHei());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        query.setFirstResult(firstResult != null ? firstResult : 0);
        query.setMaxResults(dto.getMax() != null ? dto.getMax() : 10);
        return query.getResultList();
    }


    @Override
    public List<Patient> getSimplePatient(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Patient p " + PatientInnerJoin.SIMMPLE_PATIENT_INNER_JOIN);
        int position = 0;
        String startDate = "dateJoined";
        if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
            startDate = "dateModified";
        }
        if (dto.getSearch(dto)) {
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
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }
            if (dto.getAgeGroup() != null) {
                if (position == 0) {
                    builder.append("p.dateOfBirth between :start and :end");
                    position++;
                } else {
                    builder.append(" and p.dateOfBirth between :start and :end");
                }
            }
            if (dto.getPeriod() != null) {
                if (position == 0) {
                    builder.append("p.period=:period");
                    position++;
                } else {
                    builder.append(" and p.period=:period");
                }
            }
            if (dto.getStatuses() == null || dto.getStatuses().isEmpty()) {
                if (dto.getStatus() != null) {
                    if (position == 0) {
                        builder.append("p.status=:status");
                        position++;
                    } else {
                        builder.append(" and p.status=:status");
                    }
                }
            }
            if (dto.getHei() != null) {
                if (position == 0) {
                    builder.append("p.hei=:hei");
                    position++;
                } else {
                    builder.append(" and p.hei=:hei");
                }
            }
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                if (position == 0) {
                    builder.append("p.");
                    builder.append(startDate);
                    builder.append(" between :startDate and :endDate");
                    position++;
                } else {
                    builder.append(" and (p.");
                    builder.append(startDate);
                    builder.append(" between :startDate and :endDate)");
                }
            }
            if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
                if (position == 0) {
                    builder.append("p.status in (:statuses)");
                    position++;
                } else {
                    builder.append(" and p.status in (:statuses)");
                }
            }
        }
        //builder.append(" order by p.lastName ASC, p.firstName ASC, p.middleName ASC, p.dateModified DESC, p.dateCreated DESC");
        TypedQuery<Patient> query = entityManager.createQuery(builder.toString(), Patient.class);
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
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getHei() != null) {
            query.setParameter("hei", dto.getHei());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
            query.setParameter("statuses", dto.getStatuses());
        }
        return query.getResultList();
    }

    @Override
    public Long getCount(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(Distinct p) from Patient p");
        int position = 0;
        if (dto.getSearch(dto)) {
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
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }
            if (dto.getAgeGroup() != null) {
                if (position == 0) {
                    builder.append("p.dateOfBirth between :start and :end");
                    position++;
                } else {
                    builder.append(" and p.dateOfBirth between :start and :end");
                }
            }
            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append("p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
            if (dto.getHei() != null) {
                if (position == 0) {
                    builder.append("p.hei=:hei");
                    position++;
                } else {
                    builder.append(" and p.hei=:hei");
                }
            }
            if (dto.getPeriod() != null) {
                if (position == 0) {
                    builder.append("p.period=:period");
                    position++;
                } else {
                    builder.append(" and p.period=:period");
                }
            }
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                if (position == 0) {
                    builder.append("p.dateJoined between :startDate and :endDate");
                    position++;
                } else {
                    builder.append(" and (p.dateJoined between :startDate and :endDate)");
                }
            }
        }
        TypedQuery<Long> query = entityManager.createQuery(builder.toString(), Long.class);
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
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getHei() != null) {
            query.setParameter("hei", dto.getHei());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return query.getSingleResult();
    }

    @Override
    public Integer getNumResults(SearchDTO dto) {
        return get(dto).size();
    }

    @Override
    public List<Patient> get(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Patient p " + PatientInnerJoin.PATIENT_FULL_ASSOC_FETCH);
        int position = 0;
        String startDate = "dateJoined";
        if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
            startDate = "dateModified";
        }
        if (dto.getSearch(dto)) {
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
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }
            if (dto.getAgeGroup() != null) {
                if (position == 0) {
                    builder.append("p.dateOfBirth between :start and :end");
                    position++;
                } else {
                    builder.append(" and p.dateOfBirth between :start and :end");
                }
            }
            if (dto.getPeriod() != null) {
                if (position == 0) {
                    builder.append("p.period=:period");
                    position++;
                } else {
                    builder.append(" and p.period=:period");
                }
            }
            if (dto.getStatuses() == null || dto.getStatuses().isEmpty()) {
                if (dto.getStatus() != null) {
                    if (position == 0) {
                        builder.append("p.status=:status");
                        position++;
                    } else {
                        builder.append(" and p.status=:status");
                    }
                }
            }
            if (dto.getHei() != null) {
                if (position == 0) {
                    builder.append("p.hei=:hei");
                    position++;
                } else {
                    builder.append(" and p.hei=:hei");
                }
            }
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                if (position == 0) {
                    builder.append("p.");
                    builder.append(startDate);
                    builder.append(" between :startDate and :endDate");
                    position++;
                } else {
                    builder.append(" and (p.");
                    builder.append(startDate);
                    builder.append(" between :startDate and :endDate)");
                }
            }
            if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
                if (position == 0) {
                    builder.append("p.status in (:statuses)");
                    position++;
                } else {
                    builder.append(" and p.status in (:statuses)");
                }
            }
        }
        //builder.append(" order by p.lastName ASC, p.firstName ASC, p.middleName ASC, p.dateModified DESC, p.dateCreated DESC");
        TypedQuery<Patient> query = entityManager.createQuery(builder.toString(), Patient.class);
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
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getHei() != null) {
            query.setParameter("hei", dto.getHei());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if (dto.getStatuses() != null && !dto.getStatuses().isEmpty()) {
            query.setParameter("statuses", dto.getStatuses());
        }
        return query.getResultList();
    }

}
