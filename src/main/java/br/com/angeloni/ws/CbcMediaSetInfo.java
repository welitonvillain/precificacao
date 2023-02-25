
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbcMediaSetInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbcMediaSetInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="largeUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediumUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smallUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thumnailUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbcMediaSetInfo", propOrder = {
    "largeUrl",
    "mediumUrl",
    "smallUrl",
    "thumnailUrl"
})
@XmlSeeAlso({
    MediaSetInfo.class
})
public class CbcMediaSetInfo {

    protected String largeUrl;
    protected String mediumUrl;
    protected String smallUrl;
    protected String thumnailUrl;

    /**
     * Gets the value of the largeUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLargeUrl() {
        return largeUrl;
    }

    /**
     * Sets the value of the largeUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLargeUrl(String value) {
        this.largeUrl = value;
    }

    /**
     * Gets the value of the mediumUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumUrl() {
        return mediumUrl;
    }

    /**
     * Sets the value of the mediumUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumUrl(String value) {
        this.mediumUrl = value;
    }

    /**
     * Gets the value of the smallUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallUrl() {
        return smallUrl;
    }

    /**
     * Sets the value of the smallUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallUrl(String value) {
        this.smallUrl = value;
    }

    /**
     * Gets the value of the thumnailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumnailUrl() {
        return thumnailUrl;
    }

    /**
     * Sets the value of the thumnailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumnailUrl(String value) {
        this.thumnailUrl = value;
    }

}
