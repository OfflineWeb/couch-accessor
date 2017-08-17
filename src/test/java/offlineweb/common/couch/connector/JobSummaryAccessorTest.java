/*
 * The MIT License
 *
 *   Copyright 2017 papa.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package offlineweb.common.couch.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author papa
 * created on 8/1/17.
 */
public class JobSummaryAccessorTest {
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    // '{"language":"javascript","views":{"all":{"map":"function(doc) {emit(doc.id, null)}"}}}'
    // '{"language":"javascript","views":{"all":{"map":"function(doc) {emit(doc.id, null)}"}}}

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("language", "javascript");
        jsonObject.put("views", new HashMap<String, Object>());
        ((Map) jsonObject.get("views")).put("all", new HashMap<String, Object>());
        ((Map)((Map) jsonObject.get("views")).get("all")).put("map", "function(doc) {emit(doc.id, null)}");

        System.out.println(jsonObject);

        String jsonString = objectMapper.writeValueAsString(jsonObject);
        System.out.println(jsonString);

        assertTrue(true);
    }

}