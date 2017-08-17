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

import offlineweb.common.couch.connector.util.CouchConfig;
import offlineweb.common.couch.connector.util.CouchException;
import offlineweb.common.restconnector.RESTClient;
import offlineweb.common.sharedobject.po.JobSummary;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author papa
 *         created on 7/31/17.
 */
public class JobSummaryAccessor {
    /**
     * creates a 'job_summary' database if does not exist
     * creates the 'by_job_id/all' view if does not exist
     * @throws IOException throws exception if database creation fails
     */
    public void createJobSummaryDb() throws IOException {

        Map<String, Object> dbStatus =
                RESTClient.head(CouchConfig.getCouchURL(),
                        Arrays.asList("job_summary"), null);

        if ((Integer)dbStatus.get("status") == 404) {
            Map response  = RESTClient.put(CouchConfig.getCouchURL(),
                    Arrays.asList("job_summary"));

            if (response.containsKey("error")) {
                throw new CouchException(response);
            }
        }

        Map<String, Object> viewStatus = RESTClient.head(CouchConfig.getCouchURL(),
                    Arrays.asList("job_summary", "_design", "by_job_id"), null);

        if ((Integer)viewStatus.get("status") == 404) {
            String viewByJobId = "'{\"language\": \"javascript\", " +
                    "\"views\": { \"all\": { \"map\": \"function(doc) " +
                    "{emit(doc.job, doc)}\"  } } }'";

            viewStatus = RESTClient.put(CouchConfig.getCouchURL(),
                    Arrays.asList("job_summary", "_design", "by_job_id"),
                    null,
                    viewByJobId
            );

            if (viewStatus.containsKey("error")) {
                throw new CouchException(viewStatus);
            }
        }
    }

    /**
     *
     * @param jobId job summaries associated with specified job id
     * @return  a list of job summaries
     * @throws IOException throws exception if view access fails
     */
    public List<JobSummary> getAllByJobId(String jobId) throws IOException {
        Map<String, String> queryParms = new HashMap<>();
        queryParms.put("key", jobId);

        List<JobSummary> jobSummaries = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("job_summary", "_design", "by_job_id", "_view", "all"),
                queryParms);

        return jobSummaries;
    }
}
