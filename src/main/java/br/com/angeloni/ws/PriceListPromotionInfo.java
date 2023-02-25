
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceListPromotionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceListPromotionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ignoreFrozen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="itemPriceListInfo" type="{http://ws.integration.angeloni.com.br/}itemPriceListInfo" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceListPromotionInfo", propOrder = {
    "id",
    "ignoreFrozen",
    "itemPriceListInfo",
    "userId"
})
public class PriceListPromotionInfo {

    protected String id;
    protected boolean ignoreFrozen;
    protected ItemPriceListInfo itemPriceListInfo;
    protected String userId;

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
     * Gets the value of the itemPriceListInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ItemPriceListInfo }
     *     
     */
    public ItemPriceListInfo getItemPriceListInfo() {
        return itemPriceListInfo;
    }

    /**
     * Sets the value of the itemPriceListInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPriceListInfo }
     *     
     */
    public void setItemPriceListInfo(ItemPriceListInfo value) {
        this.itemPriceListInfo = value;
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
