/*
 * Copyright 2015 Edward Zengeni.
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
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import zw.org.zvandiri.business.domain.UserRole;
import zw.org.zvandiri.business.util.IRepoConstant;
/**
 *
 * @author Edward Zengeni
 */
public interface UserRoleRepo extends CrudRepository<UserRole, String> {
    
    @Override
    public List<UserRole> findAll();
    
    @Query("from UserRole p "+IRepoConstant.USER_ROLE_CONSTANT+" where p.active=:active Order By p.name ASC")
    public List<UserRole> getOptAll(@Param("active") Boolean active);
    
    @Query("from UserRole p "+IRepoConstant.USER_ROLE_CONSTANT+" where p.name=:name")
    public UserRole getUserRoleByName(@Param("name") String name);
}