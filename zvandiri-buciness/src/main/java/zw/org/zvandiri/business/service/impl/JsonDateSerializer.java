/*
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

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.codehaus.jackson.JsonGenerator;

import org.codehaus.jackson.JsonProcessingException;

import org.codehaus.jackson.map.JsonSerializer;

import org.codehaus.jackson.map.SerializerProvider;

import org.springframework.stereotype.Component;

/**
 *
 * Used to serialize Java.util.Date, which is not a common JSON
 *
 * type, so we have to create a custom serialize method;.
 *
 *
 *
 * @author Loiane Groner
 *
 * http://loianegroner.com (English)
 *
 * http://loiane.com (Portuguese)
 *
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override

    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        String formattedDate = dateFormat.format(date);

        gen.writeString(formattedDate);

    }

}
