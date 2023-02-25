
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
 * <p>Java class for buyXOrMorePayYInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buyXOrMorePayYInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="finalPrices" type="{http://ws.integration.angeloni.com.br/}priceListPromotionInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ignoreFrozen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="initialDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="mainProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://ws.integration.angeloni.com.br/}priceListPromotionInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "buyXOrMorePayYInfo", propOrder = {
    "action",
    "displayName",
    "finalDate",
    "finalPrices",
    "id",
    "ignoreFrozen",
    "initialDate",
    "mainProductId",
    "price",
    "sites",
    "userId"
})
public class BuyXOrMorePayYInfo
    extends CbcGenericInfo
{

    protected String action;
    protected String displayName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar finalDate;
    @XmlElement(nillable = true)
    protected List<PriceListPromotionInfo> finalPrices;
    protected String id;
    protected boolean ignoreFrozen;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialDate;
    protected String mainProductId;
    @XmlElement(nillable = true)
    protected List<PriceListPromotionInfo> price;
    @XmlElement(nillable = true)
    protected List<String> sites;
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
     * {@link PriceListPromotionInfo }
     * 
     * 
     */
    public List<PriceListPromotionInfo> getFinalPrices() {
        if (finalPrices == null) {
            finalPrices = new ArrayList<PriceListPromotionInfo>();
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
     * Gets the value of the price property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the price property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PriceListPromotionInfo }
     * 
     * 
     */
    public List<PriceListPromotionInfo> getPrice() {
        if (price == null) {
            price = new ArrayList<PriceListPromotionInfo>();
        }
        return this.price;
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
