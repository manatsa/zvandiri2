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
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.HivCoInfection;
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author Judge Muzinda
 */
public interface HivConInfectionItemRepo extends AbstractRepo<HivConInfectionItem, String> {
    
    @Query("from HivConInfectionItem h left join fetch h.patient left join fetch h.hivCoInfection left join fetch h.modifiedBy left join fetch h.createdBy where h.patient=:patient")
    public List<HivConInfectionItem> findByPatient(@Param("patient") Patient patient);
    
    @Query("from HivConInfectionItem h left join fetch h.patient left join fetch h.hivCoInfection left join fetch h.modifiedBy left join fetch h.createdBy where h.patient=:patient and h.hivCoInfection=:hivCoInfection")
    public HivConInfectionItem findByPatientAndHivCoInfection(@Param("patient") Patient patient, @Param("hivCoInfection") HivCoInfection hivCoInfection);
}