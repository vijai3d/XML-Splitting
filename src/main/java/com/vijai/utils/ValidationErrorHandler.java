package com.vijai.utils;

import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;

public class ValidationErrorHandler implements org.xml.sax.ErrorHandler {


    @Override
    public void warning(org.xml.sax.SAXParseException exception) throws org.xml.sax.SAXException {
        throw exception;
    }

    @Override
    public void error(org.xml.sax.SAXParseException exception) throws org.xml.sax.SAXException {

    }

    @Override
    public void fatalError(org.xml.sax.SAXParseException exception) throws org.xml.sax.SAXException {

    }
}
