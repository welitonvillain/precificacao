
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="bulkPrice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="cmv" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="complexCampaign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="freezing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="skuId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tieredPrice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceInfo", propOrder = {
    "bulkPrice",
    "cmv",
    "complexCampaign",
    "freezing",
    "price",
    "skuId",
    "tieredPrice",
    "type"
})
public class PriceInfo
    extends CbcGenericInfo
{

    protected Boolean bulkPrice;
    protected Double cmv;
    protected Boolean complexCampaign;
    protected Boolean freezing;
    protected Double price;
    protected String skuId;
    protected Boolean tieredPrice;
    protected String type;

    /**
     * Gets the value of the bulkPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBulkPrice() {
        return bulkPrice;
    }

    /**
     * Sets the value of the bulkPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBulkPrice(Boolean value) {
        this.bulkPrice = value;
    }

    /**
     * Gets the value of the cmv property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCmv() {
        return cmv;
    }

    /**
     * Sets the value of the cmv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCmv(Double value) {
        this.cmv = value;
    }

    /**
     * Gets the value of the complexCampaign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isComplexCampaign() {
        return complexCampaign;
    }

    /**
     * Sets the value of the complexCampaign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setComplexCampaign(Boolean value) {
        this.complexCampaign = value;
    }

    /**
     * Gets the value of the freezing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFreezing() {
        return freezing;
    }

    /**
     * Sets the value of the freezing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFreezing(Boolean value) {
        this.freezing = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPrice(Double value) {
        this.price = value;
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
     * Gets the value of the tieredPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTieredPrice() {
        return tieredPrice;
    }

    /**
     * Sets the value of the tieredPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTieredPrice(Boolean value) {
        this.tieredPrice = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
