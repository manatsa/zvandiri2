/*
 * Copyright 2016 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the Licensa.
 * You may obtain a copy of the License at
 *
 *      http://www.apacha.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the Licensa.
 */
package zw.org.zvandiri.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.ArvMedicine;

/**
 *
 * @author Judge Muzinda
 */
public interface ArvMedicineRepo extends AbstractNameDescRepo<ArvMedicine, String> {
    
    @Query("from ArvMedicine a left join fetch a.createdBy left join fetch a.modifiedBy where a.active=:active")
    public List<ArvMedicine> getOptAll(@Param("active") Boolean active);
}