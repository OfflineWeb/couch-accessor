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
import org.ektorp.support.CouchDbRepositorySupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author papa
 *         created on 7/14/17.
 */

@Loggable
public class PersistenceSupportFactory {
    private final static String SUPPORT_PKG
            = PersistenceSupportFactory.class.getPackage().toString();

    private static class Holder {
        private static final Map<Class, CouchDbRepositorySupport>
                INSTANCES = new HashMap<>();

    }

    /**
     *
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static CouchDbRepositorySupport getDBSupport(Class clazz)
            throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        ConnectionManager connectionManager = new ConnectionManager();
        CouchDbConnector dbConnector = connectionManager.getDbConnector(clazz);
        CouchDbRepositorySupport support = null;

        if (Holder.INSTANCES.containsKey(clazz)) {
            support = Holder.INSTANCES.get(clazz);
        } else {
            String dbSupportClass = String.format("%s.%s%s",
                    SUPPORT_PKG, clazz.getSimpleName(), "Support");
            Constructor constructor = Class.forName(dbSupportClass)
                    .getConstructor(clazz, dbConnector.getClass());
            support = (CouchDbRepositorySupport) constructor.newInstance(clazz, dbConnector);
            Holder.INSTANCES.put(clazz, support);
        }

        return support;
    }
}
