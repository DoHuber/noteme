package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import com.google.gwt.http.client.URL;

public class Source extends DomainObject {

    private static final long serialVersionUID = 1L;
    private Source URL;
    private int sourceId;

    public Source(URL url) {
        // TODO Auto-generated constructor stub
    }
    public Source() {
        // TODO Auto-generated constructor stub
    }

    public Source getURL() {
        return URL;
    }

    public void setURL(Source uRL) {
        URL = uRL;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

}
