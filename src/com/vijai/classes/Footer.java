package com.vijai.classes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "footer")
public class Footer {
    private int recordCount;
    private int recordRowCount;

    public Footer() {
    }

    public int getRecordCount() {
        return recordCount;
    }
    @XmlElement(name = "record_count")
    public void setRecordCount(int recordCount) {
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
