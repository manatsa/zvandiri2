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

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.HalfYearPeriod;

/**
 *
 * @author Judge Muzinda
 */
public interface HalfYearPeriodRepo extends AbstractRepo<HalfYearPeriod, String> {

    @Query("from HalfYearPeriod h where h.active=:active")
    public List<HalfYearPeriod> getOptAll(@Param("active") Boolean active);

    @Query("from HalfYearPeriod h where h.startDate=:startDate and h.endDate=:endDate")
    public List<HalfYearPeriod> getByStartDateAndEndDate(
            @Param("startDate") Date start, @Param("endDate") Date endDate);

    @Query("from HalfYearPeriod h where h.startDate=:startDate")
    public List<HalfYearPeriod> getHalfYearPeriodByStartDate(@Param("startDate") Date startDate);

    public HalfYearPeriod findByStartDate(Date startDate);

    @Query("from HalfYearPeriod h where h.startDate=:startDate and h.endDate=:endDate")
    public HalfYearPeriod getByByHalfYearPeriod(
            @Param("startDate") Date start, @Param("endDate") Date endDate);
}
