/*
 *
 *   The MIT License
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

package offlineweb.common.couch.accessor.support;

import offlineweb.common.logger.annotations.Loggable;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author papa
 *         created on 7/14/17.
 */

@Loggable
public class ConnectionManager {

    private static class Holder {
        private static CouchDbInstance dbInstance = new StdCouchDbInstance(
                                new StdHttpClient
                                    .Builder()
                                    .host("192.168.0.7")
                                    .port(5984)
                                    .build());
    }

    private final Map<Class, CouchDbConnector> CONNECTORS = new HashMap<>();

    /**
     * returns a database instance
     * @return a CouchDbInstance instance
     */
    protected CouchDbInstance getDbInstance() {
        return Holder.dbInstance;
    }

    /**
     * If a connector instance for the support class
     * @param clazz
     * @return
     */
    public CouchDbConnector getDbConnector(Class clazz) {
        synchronized (CONNECTORS) {
            CouchDbConnector connector = CONNECTORS.get(clazz);

            if (connector == null) {
                String dbName = clazz.getSimpleName().toLowerCase();
                connector = getDbInstance().createConnector(dbName, true);
                CONNECTORS.put(clazz, connector);
            }
            return connector;
        }
    }
}
