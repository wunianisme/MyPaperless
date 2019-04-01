/**
 * SmtpServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.SmtpService.SmtpService;

public class SmtpServiceLocator{

/*public class SmtpServiceLocator extends org.apache.axis.client.Service implements org.tempuri.SmtpService.SmtpService.SmtpService {

    public SmtpServiceLocator() {
    }


    public SmtpServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SmtpServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SmtpServiceSoap12
    private java.lang.String SmtpServiceSoap12_address = "http://10.132.48.76/smtp/smtpservice.asmx";

    public java.lang.String getSmtpServiceSoap12Address() {
        return SmtpServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SmtpServiceSoap12WSDDServiceName = "SmtpServiceSoap12";

    public java.lang.String getSmtpServiceSoap12WSDDServiceName() {
        return SmtpServiceSoap12WSDDServiceName;
    }

    public void setSmtpServiceSoap12WSDDServiceName(java.lang.String name) {
        SmtpServiceSoap12WSDDServiceName = name;
    }

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SmtpServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSmtpServiceSoap12(endpoint);
    }

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.SmtpService.SmtpService.SmtpServiceSoap12Stub _stub = new org.tempuri.SmtpService.SmtpService.SmtpServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getSmtpServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmtpServiceSoap12EndpointAddress(java.lang.String address) {
        SmtpServiceSoap12_address = address;
    }


    // Use to get a proxy class for SmtpServiceSoap
    private java.lang.String SmtpServiceSoap_address = "http://10.132.48.76/smtp/smtpservice.asmx";

    public java.lang.String getSmtpServiceSoapAddress() {
        return SmtpServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SmtpServiceSoapWSDDServiceName = "SmtpServiceSoap";

    public java.lang.String getSmtpServiceSoapWSDDServiceName() {
        return SmtpServiceSoapWSDDServiceName;
    }

    public void setSmtpServiceSoapWSDDServiceName(java.lang.String name) {
        SmtpServiceSoapWSDDServiceName = name;
    }

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SmtpServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSmtpServiceSoap(endpoint);
    }

    public org.tempuri.SmtpService.SmtpService.SmtpServiceSoap getSmtpServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.SmtpService.SmtpService.SmtpServiceSoapStub _stub = new org.tempuri.SmtpService.SmtpService.SmtpServiceSoapStub(portAddress, this);
            _stub.setPortName(getSmtpServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmtpServiceSoapEndpointAddress(java.lang.String address) {
        SmtpServiceSoap_address = address;
    }

    *//**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     *//*
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.SmtpService.SmtpService.SmtpServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.SmtpService.SmtpService.SmtpServiceSoap12Stub _stub = new org.tempuri.SmtpService.SmtpService.SmtpServiceSoap12Stub(new java.net.URL(SmtpServiceSoap12_address), this);
                _stub.setPortName(getSmtpServiceSoap12WSDDServiceName());
                return _stub;
            }
            if (org.tempuri.SmtpService.SmtpService.SmtpServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.SmtpService.SmtpService.SmtpServiceSoapStub _stub = new org.tempuri.SmtpService.SmtpService.SmtpServiceSoapStub(new java.net.URL(SmtpServiceSoap_address), this);
                _stub.setPortName(getSmtpServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    *//**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     *//*
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SmtpServiceSoap12".equals(inputPortName)) {
            return getSmtpServiceSoap12();
        }
        else if ("SmtpServiceSoap".equals(inputPortName)) {
            return getSmtpServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "SmtpService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "SmtpServiceSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "SmtpServiceSoap"));
        }
        return ports.iterator();
    }

    *//**
    * Set the endpoint address for the specified port name.
    *//*
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SmtpServiceSoap12".equals(portName)) {
            setSmtpServiceSoap12EndpointAddress(address);
        }
        else 
if ("SmtpServiceSoap".equals(portName)) {
            setSmtpServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    *//**
    * Set the endpoint address for the specified port name.
    *//*
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }*/

}
