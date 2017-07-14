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
import offlineweb.common.couch.accessor.common.DocumentState;
import org.ektorp.Attachment;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * POJO for Document
 *
 * @author papa
 *         created on 7/13/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document implements Serializable {

    @JsonProperty("_id")
    private String id;              /* unique job id, UUID, generated, required */

    @JsonProperty(value = "cntid", required = true)
    private String contentId;       /* id of document found at source */

    @JsonProperty(value = "title", required = true)
    private String title;           /* unique job id, UUID, generated, required */

    @JsonProperty(value = "source", required = true)
    private DocumentSource source;   /* source of document, required */

    @JsonProperty("__attachments")
    private Map<String, Attachment> attachments;    /* attachments, if available */

    @JsonProperty(value = "state", required = true)
    private DocumentState state;     /* current state of the document in local repo  */

    @JsonProperty("cdate")
    private Date createDate;        /* the date the document is created in local repo */

    @JsonProperty("udate")
    private Date updateDate;        /* the date the document last updated in local repo */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentSource getSource() {
        return source;
    }

    public void setSource(DocumentSource source) {
        this.source = source;
    }

    public Map<String, Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Attachment> attachments) {
        this.attachments = attachments;
    }

    public DocumentState getState() {
        return state;
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
