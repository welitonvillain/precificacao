
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for skuMaximumBuyInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="skuMaximumBuyInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcGenericInfo">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxQuantityBuy" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skuMaximumBuyInfo", propOrder = {
    "id",
    "maxQuantityBuy"
})
public class SkuMaximumBuyInfo
    extends CbcGenericInfo
{

    protected String id;
    protected int maxQuantityBuy;

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
     * Gets the value of the maxQuantityBuy property.
     * 
     */
    public int getMaxQuantityBuy() {
        return maxQuantityBuy;
    }

    /**
     * Sets the value of the maxQuantityBuy property.
     * 
     */
    public void setMaxQuantityBuy(int value) {
        this.maxQuantityBuy = value;
    }

}
