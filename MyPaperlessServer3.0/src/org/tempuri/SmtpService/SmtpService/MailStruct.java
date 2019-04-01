/**
 * MailStruct.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.SmtpService.SmtpService;

public class MailStruct  implements java.io.Serializable {
    /*private java.lang.String mailto;

    private java.lang.String cc;

    private java.lang.String bcc;

    private java.lang.String from;

    private java.lang.String subject;

    private java.lang.String body;

    private org.tempuri.SmtpService.SmtpService.MailFormat format;

    private org.tempuri.SmtpService.SmtpService.MailPriority priority;

    private org.tempuri.SmtpService.SmtpService.MailEncoding encode;

    public MailStruct() {
    }

    public MailStruct(
           java.lang.String mailto,
           java.lang.String cc,
           java.lang.String bcc,
           java.lang.String from,
           java.lang.String subject,
           java.lang.String body,
           org.tempuri.SmtpService.SmtpService.MailFormat format,
           org.tempuri.SmtpService.SmtpService.MailPriority priority,
           org.tempuri.SmtpService.SmtpService.MailEncoding encode) {
           this.mailto = mailto;
           this.cc = cc;
           this.bcc = bcc;
           this.from = from;
           this.subject = subject;
           this.body = body;
           this.format = format;
           this.priority = priority;
           this.encode = encode;
    }


    *//**
     * Gets the mailto value for this MailStruct.
     * 
     * @return mailto
     *//*
    public java.lang.String getMailto() {
        return mailto;
    }


    *//**
     * Sets the mailto value for this MailStruct.
     * 
     * @param mailto
     *//*
    public void setMailto(java.lang.String mailto) {
        this.mailto = mailto;
    }


    *//**
     * Gets the cc value for this MailStruct.
     * 
     * @return cc
     *//*
    public java.lang.String getCc() {
        return cc;
    }


    *//**
     * Sets the cc value for this MailStruct.
     * 
     * @param cc
     *//*
    public void setCc(java.lang.String cc) {
        this.cc = cc;
    }


    *//**
     * Gets the bcc value for this MailStruct.
     * 
     * @return bcc
     *//*
    public java.lang.String getBcc() {
        return bcc;
    }


    *//**
     * Sets the bcc value for this MailStruct.
     * 
     * @param bcc
     *//*
    public void setBcc(java.lang.String bcc) {
        this.bcc = bcc;
    }


    *//**
     * Gets the from value for this MailStruct.
     * 
     * @return from
     *//*
    public java.lang.String getFrom() {
        return from;
    }


    *//**
     * Sets the from value for this MailStruct.
     * 
     * @param from
     *//*
    public void setFrom(java.lang.String from) {
        this.from = from;
    }


    *//**
     * Gets the subject value for this MailStruct.
     * 
     * @return subject
     *//*
    public java.lang.String getSubject() {
        return subject;
    }


    *//**
     * Sets the subject value for this MailStruct.
     * 
     * @param subject
     *//*
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    *//**
     * Gets the body value for this MailStruct.
     * 
     * @return body
     *//*
    public java.lang.String getBody() {
        return body;
    }


    *//**
     * Sets the body value for this MailStruct.
     * 
     * @param body
     *//*
    public void setBody(java.lang.String body) {
        this.body = body;
    }


    *//**
     * Gets the format value for this MailStruct.
     * 
     * @return format
     *//*
    public org.tempuri.SmtpService.SmtpService.MailFormat getFormat() {
        return format;
    }


    *//**
     * Sets the format value for this MailStruct.
     * 
     * @param format
     *//*
    public void setFormat(org.tempuri.SmtpService.SmtpService.MailFormat format) {
        this.format = format;
    }


    *//**
     * Gets the priority value for this MailStruct.
     * 
     * @return priority
     *//*
    public org.tempuri.SmtpService.SmtpService.MailPriority getPriority() {
        return priority;
    }


    *//**
     * Sets the priority value for this MailStruct.
     * 
     * @param priority
     *//*
    public void setPriority(org.tempuri.SmtpService.SmtpService.MailPriority priority) {
        this.priority = priority;
    }


    *//**
     * Gets the encode value for this MailStruct.
     * 
     * @return encode
     *//*
    public org.tempuri.SmtpService.SmtpService.MailEncoding getEncode() {
        return encode;
    }


    *//**
     * Sets the encode value for this MailStruct.
     * 
     * @param encode
     *//*
    public void setEncode(org.tempuri.SmtpService.SmtpService.MailEncoding encode) {
        this.encode = encode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MailStruct)) return false;
        MailStruct other = (MailStruct) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mailto==null && other.getMailto()==null) || 
             (this.mailto!=null &&
              this.mailto.equals(other.getMailto()))) &&
            ((this.cc==null && other.getCc()==null) || 
             (this.cc!=null &&
              this.cc.equals(other.getCc()))) &&
            ((this.bcc==null && other.getBcc()==null) || 
             (this.bcc!=null &&
              this.bcc.equals(other.getBcc()))) &&
            ((this.from==null && other.getFrom()==null) || 
             (this.from!=null &&
              this.from.equals(other.getFrom()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.body==null && other.getBody()==null) || 
             (this.body!=null &&
              this.body.equals(other.getBody()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.encode==null && other.getEncode()==null) || 
             (this.encode!=null &&
              this.encode.equals(other.getEncode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMailto() != null) {
            _hashCode += getMailto().hashCode();
        }
        if (getCc() != null) {
            _hashCode += getCc().hashCode();
        }
        if (getBcc() != null) {
            _hashCode += getBcc().hashCode();
        }
        if (getFrom() != null) {
            _hashCode += getFrom().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getBody() != null) {
            _hashCode += getBody().hashCode();
        }
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getEncode() != null) {
            _hashCode += getEncode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MailStruct.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "MailStruct"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "mailto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "cc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bcc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "bcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("from");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "from"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "MailFormat"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "MailPriority"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "encode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/SmtpService/SmtpService", "MailEncoding"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    *//**
     * Return type metadata object
     *//*
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    *//**
     * Get Custom Serializer
     *//*
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    *//**
     * Get Custom Deserializer
     *//*
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }*/

}
