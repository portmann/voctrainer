//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.08 at 04:54:09 PM CET 
//


package com.lisilab.its.model.datashop;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}dataset"/>
 *         &lt;element ref="{}sample"/>
 *         &lt;element ref="{}analysis"/>
 *         &lt;element ref="{}custom_field"/>
 *       &lt;/choice>
 *       &lt;attribute name="result_code" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="result_message" type="{}string_255" />
 *       &lt;attribute name="analysis_id" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="custom_field_id" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datasetOrSampleOrAnalysis"
})
@XmlRootElement(name = "pslc_datashop_message")
public class PslcDatashopMessage {

    @XmlElements({
        @XmlElement(name = "dataset", type = Dataset.class),
        @XmlElement(name = "sample", type = Sample.class),
        @XmlElement(name = "analysis", type = Analysis.class),
        @XmlElement(name = "custom_field", type = CustomField.class)
    })
    protected List<Object> datasetOrSampleOrAnalysis;
    @XmlAttribute(name = "result_code")
    protected BigInteger resultCode;
    @XmlAttribute(name = "result_message")
    protected String resultMessage;
    @XmlAttribute(name = "analysis_id")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger analysisId;
    @XmlAttribute(name = "custom_field_id")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger customFieldId;

    /**
     * Gets the value of the datasetOrSampleOrAnalysis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasetOrSampleOrAnalysis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasetOrSampleOrAnalysis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dataset }
     * {@link Sample }
     * {@link Analysis }
     * {@link CustomField }
     * 
     * 
     */
    public List<Object> getDatasetOrSampleOrAnalysis() {
        if (datasetOrSampleOrAnalysis == null) {
            datasetOrSampleOrAnalysis = new ArrayList<Object>();
        }
        return this.datasetOrSampleOrAnalysis;
    }

    /**
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setResultCode(BigInteger value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the resultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Sets the value of the resultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMessage(String value) {
        this.resultMessage = value;
    }

    /**
     * Gets the value of the analysisId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAnalysisId() {
        return analysisId;
    }

    /**
     * Sets the value of the analysisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnalysisId(BigInteger value) {
        this.analysisId = value;
    }

    /**
     * Gets the value of the customFieldId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCustomFieldId() {
        return customFieldId;
    }

    /**
     * Sets the value of the customFieldId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomFieldId(BigInteger value) {
        this.customFieldId = value;
    }

}