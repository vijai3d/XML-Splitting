package com.vijai.xmlClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "footer")
public class Footer {
    private long recordCount;
    private int recordRowCount;

    public Footer() {
    }

    public long getRecordCount() {
        return recordCount;
    }
    @XmlElement(name = "record_count")
    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getRecordRowCount() {
        return recordRowCount;
    }
    @XmlElement(name = "record_row_count")
    public void setRecordRowCount(int recordRowCount) {
        this.recordRowCount = recordRowCount;
    }
}
