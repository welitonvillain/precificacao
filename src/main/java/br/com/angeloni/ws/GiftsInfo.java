
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
 * <p>Java class for giftsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="giftsInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyQuantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="erpId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="giftSku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="incrementItens" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="initialDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="largeIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainSku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skuGiftQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="smallIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "giftsInfo", propOrder = {
    "action",
    "buyQuantity",
    "description",
    "displayName",
    "erpId",
    "finalDate",
    "giftSku",
    "id",
    "incrementItens",
    "initialDate",
    "largeIcon",
    "longDescription",
    "mainSku",
    "sites",
    "skuGiftQuantity",
    "smallIcon"
})
public class GiftsInfo
    extends CbcGenericInfo
{

    protected String action;
    protected Double buyQuantity;
    protected String description;
    protected String displayName;
    protected String erpId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar finalDate;
    protected String giftSku;
    protected String id;
    protected Boolean incrementItens;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialDate;
    protected String largeIcon;
    protected String longDescription;
    protected String mainSku;
    @XmlElement(nillable = true)
    protected List<String> sites;
    protected Integer skuGiftQuantity;
    protected String smallIcon;

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
     * Gets the value of the erpId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErpId() {
        return erpId;
    }

    /**
     * Sets the value of the erpId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErpId(String value) {
        this.erpId = value;
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
     * Gets the value of the giftSku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftSku() {
        return giftSku;
    }

    /**
     * Sets the value of the giftSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftSku(String value) {
        this.giftSku = value;
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
     * Gets the value of the incrementItens property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncrementItens() {
        return incrementItens;
    }

    /**
     * Sets the value of the incrementItens property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncrementItens(Boolean value) {
        this.incrementItens = value;
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
     * Gets the value of the longDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the mainSku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainSku() {
        return mainSku;
    }

    /**
     * Sets the value of the mainSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainSku(String value) {
        this.mainSku = value;
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
     * Gets the value of the skuGiftQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSkuGiftQuantity() {
        return skuGiftQuantity;
    }

    /**
     * Sets the value of the skuGiftQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSkuGiftQuantity(Integer value) {
        this.skuGiftQuantity = value;
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

}
