
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
import offlineweb.common.sharedobject.po.Attachment;
import offlineweb.common.sharedobject.po.Document;

import java.io.IOException;
import java.util.*;

/**
 * Handles all communication couchdb related
 * @author papa
 *         created on 7/29/17.
 */
@Loggable
public class DocumentAccessor {
    public void createDocumentDb() throws IOException {

        Map<String, Object> dbStatus =
                RESTClient.head(CouchConfig.getCouchURL(),
                        Arrays.asList("document"), null);

        if ((Integer)dbStatus.get("status") == 404) {
            Map response  = RESTClient.put(CouchConfig.getCouchURL(),
                    Arrays.asList("document"));

            if (response.containsKey("error")) {
                throw new CouchException(response);
            }
        }
    }

    public Document findById(String docId) throws IOException {
        Document document = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("document", docId));
        return document;
    }

    public List<Document> getAll() throws IOException {
        List<Document> documents = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("document", "_all_docs"));
        return documents;
    }

    public Document saveOrUpdate(Document document) throws IOException {
        if (document == null) {
            throw new CouchException("Requires a valid Document object");
        }
        Document retDocument = null;
        Map<String, String> header = new HashMap<>();
        header.put("X-Couch-Full-Commit", "true");
        if (document.getId() == null) {

            retDocument = RESTClient.post(
                    CouchConfig.getCouchURL(),
                    Arrays.asList("document"),
                    null,
                    document,
                    header);

        } else {
            retDocument = RESTClient.put(
                    CouchConfig.getCouchURL(),
                    Arrays.asList("document", document.getId()),
                    null,
                    document,
                    header);
        }

        handleAttachments(retDocument);
        return retDocument;
    }

    public List<Document> getDocsInRange(String fromTitle, String toTitle) throws IOException {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("startkey", fromTitle);
        queryParam.put("endkey", toTitle);
        List<Document> documents = RESTClient.get(CouchConfig.getCouchURL(),
                Arrays.asList("document", "_all_docs"),
                queryParam);
        return documents;
    }

    /**
     * {"ok":true,"id":"274e6cf4251833ad6d5380f7480002e4","rev":"3-97895729adec1e618a53519268232ac4"}
     * @param document
     * @throws IOException
     */
    private void handleAttachments(Document document) throws IOException {
        if (document == null) {
            throw new CouchException("Requires a valid Document object");
        }

        Map<String, Attachment> attachments = document.getAttachments();
        if (attachments == null || attachments.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Attachment> attch : attachments.entrySet()) {
            if (attch.getValue().isNeedsUpdate()) {
                Map<String, String> queryParam = new HashMap<>();
                queryParam.put("rev", document.getRev());

                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", attch.getValue().getContentType());

                Map<String, String> attachmentUpdate =
                        RESTClient.put(CouchConfig.getCouchURL(),
                        Arrays.asList("document", document.getId(), attch.getKey()),
                        queryParam,
                        attch.getValue().getContent(),
                        header);

                if (!attachmentUpdate.containsKey("ok")) {
                    throw new CouchException(attachmentUpdate);
                }

                document.setRev(attachmentUpdate.get("rev"));
            }
        }
    }
}
