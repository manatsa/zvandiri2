/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.mobile.api.resource;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import zw.org.zvandiri.business.domain.HIVSelfTesting;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.service.HIVSelfTestingService;
import zw.org.zvandiri.business.service.PersonService;
import zw.org.zvandiri.business.util.DateUtil;

/**
 *
 * @author tasu
 */
@Component
@Path("/mobile/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonProcessResource {

    @Resource
    private PersonService personService;
    @Resource
    private HIVSelfTestingService hIVSelfTestingService;

    @POST
    @Path("/add-person")
    public ResponseEntity<Map<String, Object>> addPerson(Person person) {
        Map<String, Object> map = new HashMap<>();
        String id = "";
        try {
            person.setDateOfBirth(DateUtil.getDateFromStringRest(person.getDob()));
            id = personService.save(person).getId();
        } catch (Exception e) {
            map.put("message", "System error occurred saving person");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", id);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @POST
    @Path("/add-self-testing")
    public ResponseEntity<Map<String, Object>> addHivSelfTesting(HIVSelfTesting item) {
        Map<String, Object> map = new HashMap<>();
        try {
            item.setPerson(personService.get(item.getPerson().getId()));
            hIVSelfTestingService.save(item);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "System error occurred saving hiv self testing");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", "Item saved");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
