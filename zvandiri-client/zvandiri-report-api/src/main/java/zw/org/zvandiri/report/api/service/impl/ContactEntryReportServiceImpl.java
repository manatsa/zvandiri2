/*
 * Copyright 2017 User.
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
package zw.org.zvandiri.report.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.service.ContactEntryService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.ContactEntryReportService;

/**
 *
 * @author User
 */
@Repository
public class ContactEntryReportServiceImpl implements ContactEntryReportService{
    @Resource
    private UserService userService;
    @Resource
    private ContactEntryService contactEntryService;
    
    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<User> users = userService.getAll();
        List<String> items = new ArrayList<>();
        items.add("User");
        items.add("Records Captured");
        list.add(new GenericReportModel(items));
        for(User user : users){
            dto.setCreatedBy(user);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(user.getFirstName() + " " + user.getLastName());
            Long itemCount = contactEntryService.getCount(dto);
            row.add(itemCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
