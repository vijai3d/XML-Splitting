package com.vijai.xmlClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "record")
public class Record {

    private Long recordId;
    private List<Row> recordRow;

    public Record() {
    }

    public Long getRecordId() {
        return recordId;
    }
    @XmlElement(name = "record_id")
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public List<Row> getRecordRow() {
        return recordRow;
    }
    @XmlElement(name = "record_rows")
    public void setRecordRow(List<Row> recordRow) {
        this.recordRow = recordRow;
    }
}