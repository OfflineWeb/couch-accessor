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

package offlineweb.common.couch.accessor;

import offlineweb.common.couch.accessor.util.CouchConfig;
import offlineweb.common.couch.accessor.util.CouchException;
import offlineweb.common.logger.annotations.Loggable;
import offlineweb.common.restconnector.RESTClient;
import offlineweb.common.sharedobject.po.JobConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author papa
 *         created on 7/30/17.
 */

@Loggable
public class JobAccessor {

    /**
     * creates a 'job_config' database if does not exist
     * @throws IOException throws exception if database creation fails
     */
    public void createJobDb() throws IOException {

        Map<String, Object> dbStatus =
                RESTClient.head(CouchConfig.getCouchURL(),
                        Arrays.asList("job_config"), null);

        if ((Integer)dbStatus.get("status") == 404) {
            Map response  = RESTClient.put(CouchConfig.getCouchURL(),
                    Arrays.asList("document"));

            if (response.containsKey("error")) {
                throw new CouchException(response);
            }
        }
    }

    /**
     * returns all job configs
     * @return a list job configs
     * @throws IOException throws when query fails
     */
    public List<JobConfig> getAll() throws IOException {
        List<JobConfig> jobConfigs = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("job_config", "_all_docs"));
        return jobConfigs;
    }


    /**
     * returns a job config associated with the specific job id
     * @param jobId unique job id
     * @return job config object
     * @throws IOException throws when no such job id
     */
    public JobConfig findById(String jobId) throws IOException {
        JobConfig jobConfig = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("job_config", jobId));
        return jobConfig;
    }

    /**
     * saves or updates a job config
     * @param jobConfig job config to persist
     * @return updated job config
     * @throws IOException throws when fails to persist
     */
    public JobConfig saveOrUpdate(JobConfig jobConfig) throws IOException {
        if (jobConfig == null) {
            throw new CouchException("Requires a valid JobConfig object");
        }

        JobConfig retConfig = null;
        Map<String, String> header = new HashMap<>();
        header.put("X-Couch-Full-Commit", "true");

        if (jobConfig.getId() == null) {
            retConfig = RESTClient.post(
                    CouchConfig.getCouchURL(),
                    Arrays.asList("job_config"),
                    null,
                    jobConfig,
                    header);
        } else {
            retConfig = RESTClient.put(
                    CouchConfig.getCouchURL(),
                    Arrays.asList("job_config", jobConfig.getId()),
                    null,
                    jobConfig,
                    header);
        }

        return retConfig;
    }

}
