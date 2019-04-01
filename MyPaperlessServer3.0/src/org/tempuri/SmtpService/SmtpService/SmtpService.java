/**
 * SmtpService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.SmtpService.SmtpService;

public interface SmtpService extends javax.xml.rpc.Service {
    public java.lang.String getSmtpServiceSoap12Address();

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap12() throws javax.xml.rpc.ServiceException;

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getSmtpServiceSoapAddress();

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap() throws javax.xml.rpc.ServiceException;

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
