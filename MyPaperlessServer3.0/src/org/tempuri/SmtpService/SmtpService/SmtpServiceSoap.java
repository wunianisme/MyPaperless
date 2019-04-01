/**
 * SmtpServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.SmtpService.SmtpService;

public interface SmtpServiceSoap extends java.rmi.Remote {
    public java.lang.String geterrMsg() throws java.rmi.RemoteException;
    public boolean sendMail(org.tempuri.SmtpService.SmtpService.MailStruct obj) throws java.rmi.RemoteException;
    public boolean WMSendMail(java.lang.String mailto, java.lang.String from, java.lang.String cc, java.lang.String subject, java.lang.String msg) throws java.rmi.RemoteException;
    public boolean saveAttachment(java.lang.String name, byte[] buffer) throws java.rmi.RemoteException;
    public boolean cleanAttach() throws java.rmi.RemoteException;
}
