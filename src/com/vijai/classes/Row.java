package com.vijai.classes;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Row {
    private List<String> string;

    public Row() {
    }

    public List<String> getString() {
        return string;
    }
    @XmlElement(name = "record_row")
    public void setString(List<String> string) {
        this.string = string;
    }
}
