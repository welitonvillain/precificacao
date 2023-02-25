
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
 * <p>Java class for promotionLastItemInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="promotionLastItemInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyQuantity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="erpId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="finalPrices" type="{http://ws.integration.angeloni.com.br/}virtualPackPriceInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ignoreFrozen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="initialDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="largeIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prices" type="{http://ws.integration.angeloni.com.br/}virtualPackPriceInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skuId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smallIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeDiscount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useSelo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "promotionLastItemInfo", propOrder = {
    "action",
    "buyQuantity",
    "description",
    "discount",
    "displayName",
    "erpId",
    "finalDate",
    "finalPrices",
    "id",
    "ignoreFrozen",
    "initialDate",
    "largeIcon",
    "mainProductId",
    "prices",
    "sites",
    "skuId",
    "smallIcon",
    "storeIds",
    "typeDiscount",
    "useSelo",
    "userId"
})
public class PromotionLastItemInfo
    extends CbcGenericInfo
{

    protected String action;
    protected Double buyQuantity;
    protected String description;
    protected Double discount;
    protected String displayName;
    protected String erpId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar finalDate;
    @XmlElement(nillable = true)
    protected List<VirtualPackPriceInfo> finalPrices;
    protected String id;
    protected boolean ignoreFrozen;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialDate;
    protected String largeIcon;
    protected String mainProductId;
    @XmlElement(nillable = true)
    protected List<VirtualPackPriceInfo> prices;
    @XmlElement(nillable = true)
    protected List<String> sites;
    protected String skuId;
    protected String smallIcon;
    @XmlElement(nillable = true)
    protected List<String> storeIds;
    protected String typeDiscount;
    protected Boolean useSelo;
    protected String userId;

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
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDiscount(Double value) {
        this.discount = value;
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
     * Gets the value of the finalPrices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the finalPrices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFinalPrices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualPackPriceInfo }
     * 
     * 
     */
    public List<VirtualPackPriceInfo> getFinalPrices() {
        if (finalPrices == null) {
            finalPrices = new ArrayList<VirtualPackPriceInfo>();
        }
        return this.finalPrices;
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
     * Gets the value of the ignoreFrozen property.
     * 
     */
    public boolean isIgnoreFrozen() {
        return ignoreFrozen;
    }

    /**
     * Sets the value of the ignoreFrozen property.
     * 
     */
    public void setIgnoreFrozen(boolean value) {
        this.ignoreFrozen = value;
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
     * Gets the value of the prices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualPackPriceInfo }
     * 
     * 
     */
    public List<VirtualPackPriceInfo> getPrices() {
        if (prices == null) {
            prices = new ArrayList<VirtualPackPriceInfo>();
        }
        return this.prices;
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
     * Gets the value of the skuId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * Sets the value of the skuId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuId(String value) {
        this.skuId = value;
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
     * Gets the value of the typeDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeDiscount() {
        return typeDiscount;
    }

    /**
     * Sets the value of the typeDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeDiscount(String value) {
        this.typeDiscount = value;
    }

    /**
     * Gets the value of the useSelo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseSelo() {
        return useSelo;
    }

    /**
     * Sets the value of the useSelo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseSelo(Boolean value) {
        this.useSelo = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
