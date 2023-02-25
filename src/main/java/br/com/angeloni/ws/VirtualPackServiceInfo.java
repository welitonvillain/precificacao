
package br.com.angeloni.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for virtualPackServiceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="virtualPackServiceInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyQuantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="closenessQualifierImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="closenessQualifierMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initialDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="largeIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replaceValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="smallIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualPackErp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualPackMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "virtualPackServiceInfo", propOrder = {
    "action",
    "buyQuantity",
    "closenessQualifierImageUrl",
    "closenessQualifierMessage",
    "description",
    "displayName",
    "finalDate",
    "id",
    "initialDate",
    "largeIcon",
    "mainProductId",
    "replaceValue",
    "sites",
    "skus",
    "smallIcon",
    "storeIds",
    "userType",
    "virtualPackErp",
    "virtualPackMessage"
})
public class VirtualPackServiceInfo
    extends CbcGenericInfo
{

    protected String action;
    protected Double buyQuantity;
    protected String closenessQualifierImageUrl;
    protected String closenessQualifierMessage;
    protected String description;
    protected String displayName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar finalDate;
    protected String id;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialDate;
    protected String largeIcon;
    protected String mainProductId;
    protected Double replaceValue;
    @XmlElement(nillable = true)
    protected List<String> sites;
    @XmlElement(nillable = true)
    protected List<String> skus;
    protected String smallIcon;
    @XmlElement(nillable = true)
    protected List<String> storeIds;
    protected String userType;
    protected String virtualPackErp;
    protected String virtualPackMessage;

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the buyQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBuyQuantity() {
        return buyQuantity;
    }

    /**
     * Sets the value of the buyQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBuyQuantity(Double value) {
        this.buyQuantity = value;
    }

    /**
     * Gets the value of the closenessQualifierImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClosenessQualifierImageUrl() {
        return closenessQualifierImageUrl;
    }

    /**
     * Sets the value of the closenessQualifierImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosenessQualifierImageUrl(String value) {
        this.closenessQualifierImageUrl = value;
    }

    /**
     * Gets the value of the closenessQualifierMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClosenessQualifierMessage() {
        return closenessQualifierMessage;
    }

    /**
     * Sets the value of the closenessQualifierMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosenessQualifierMessage(String value) {
        this.closenessQualifierMessage = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the finalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFinalDate() {
        return finalDate;
    }

    /**
     * Sets the value of the finalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFinalDate(XMLGregorianCalendar value) {
        this.finalDate = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the initialDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the value of the initialDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInitialDate(XMLGregorianCalendar value) {
        this.initialDate = value;
    }

    /**
     * Gets the value of the largeIcon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLargeIcon() {
        return largeIcon;
    }

    /**
     * Sets the value of the largeIcon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLargeIcon(String value) {
        this.largeIcon = value;
    }

    /**
     * Gets the value of the mainProductId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainProductId() {
        return mainProductId;
    }

    /**
     * Sets the value of the mainProductId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainProductId(String value) {
        this.mainProductId = value;
    }

    /**
     * Gets the value of the replaceValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getReplaceValue() {
        return replaceValue;
    }

    /**
     * Sets the value of the replaceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setReplaceValue(Double value) {
        this.replaceValue = value;
    }

    /**
     * Gets the value of the sites property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sites property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSites().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSites() {
        if (sites == null) {
            sites = new ArrayList<String>();
        }
        return this.sites;
    }

    /**
     * Gets the value of the skus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSkus() {
        if (skus == null) {
            skus = new ArrayList<String>();
        }
        return this.skus;
    }

    /**
     * Gets the value of the smallIcon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallIcon() {
        return smallIcon;
    }

    /**
     * Sets the value of the smallIcon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallIcon(String value) {
        this.smallIcon = value;
    }

    /**
     * Gets the value of the storeIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storeIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStoreIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStoreIds() {
        if (storeIds == null) {
            storeIds = new ArrayList<String>();
        }
        return this.storeIds;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

    /**
     * Gets the value of the virtualPackErp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualPackErp() {
        return virtualPackErp;
    }

    /**
     * Sets the value of the virtualPackErp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualPackErp(String value) {
        this.virtualPackErp = value;
    }

    /**
     * Gets the value of the virtualPackMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualPackMessage() {
        return virtualPackMessage;
    }

    /**
     * Sets the value of the virtualPackMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualPackMessage(String value) {
        this.virtualPackMessage = value;
    }

}
