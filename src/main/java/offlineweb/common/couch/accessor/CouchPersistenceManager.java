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

package offlineweb.common.couch.accessor;

import offlineweb.common.couch.accessor.support.ConnectionManager;
import offlineweb.common.couch.accessor.support.PersistenceSupportFactory;
import offlineweb.common.logger.annotations.Loggable;
import offlineweb.common.persistence.intf.PersistenceException;
import offlineweb.common.persistence.intf.PersistenceManager;
import offlineweb.common.persistence.intf.QueryIntf;
import org.ektorp.CouchDbConnector;
import org.ektorp.PageRequest;
import org.ektorp.support.CouchDbRepositorySupport;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author papa
 *         created on 7/13/17.
 */

@Loggable
public class CouchPersistenceManager implements PersistenceManager {

    @Override
    public <T> List<T> findAll(Class<T> clazz) throws PersistenceException {
        return (List<T>) getDbSupport(clazz).getAll();
    }

    @Override
    public <T, M> T findById(Class<T> clazz, M id) throws PersistenceException {
        return (T) getDbSupport(clazz).get((String) id);
    }

    @Override
    public <T> void saveOrUpdate(T entity) throws PersistenceException {

    }

    @Override
    public <T> void saveOrUpdate(List<T> entities) throws PersistenceException {

    }

    @Override
    public <T, M> List<T> execute(Class<T> clazz, QueryIntf<M> query) throws PersistenceException {
        return null;
    }

    @Override
    public <M> void execute(QueryIntf<M> query) throws PersistenceException {

    }

    private <T> CouchDbRepositorySupport getDbSupport(Class<T> clazz) {
        CouchDbRepositorySupport dbSupport = null;
        try {
            dbSupport = new PersistenceSupportFactory().getDBSupport(clazz);
        } catch (ClassNotFoundException |NoSuchMethodException |
                IllegalAccessException | InvocationTargetException |
                InstantiationException e) {
            throw new PersistenceException(e);
        }
        return dbSupport;
    }
}
