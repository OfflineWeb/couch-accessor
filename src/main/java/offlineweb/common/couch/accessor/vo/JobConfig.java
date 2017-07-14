/*
 *
 *  * The MIT License
 *  *
 *  * Copyright 2017 papa.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package offlineweb.common.couch.accessor.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import offlineweb.common.couch.accessor.common.DocumentSource;
import offlineweb.common.couch.accessor.common.JobType;

import java.io.Serializable;

/**
 * POJO for Job Configuration
 * @author papa
 *         created on 7/13/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobConfig  implements Serializable {

    @JsonProperty(value = "_id")
    private String id;              /* unique job id, UUID, generated, required */

    @JsonProperty(value = "name", required = true)
    private String name;            /* name of the job, assigned by user, required */

    @JsonProperty("type")
    private JobType jobType;        /* type of job, if null, then it's all, optional */

    @JsonProperty("range")
    private String[] range;         /* range of titles, optional */

    @JsonProperty(value = "source", required = true)
    private DocumentSource source;   /* source of document, required */

    @JsonProperty("sched")
    private boolean isScheduled;    /* is the job scheduled, default is false, optional */

    @JsonProperty("freq")
    private int jobFrequency;       /* how frequently job is run, in days, default is 15, optional */

    public JobConfig() {
        this.jobType = JobType.ALL;
        this.isScheduled = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String[] getRange() {
        return range;
    }

    public void setRange(String[] range) {
        this.range = range;
    }

    public DocumentSource getSource() {
        return source;
    }

    public void setSource(DocumentSource source) {
        this.source = source;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public int getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequency(int jobFrequency) {
        this.jobFrequency = jobFrequency;
    }
}
