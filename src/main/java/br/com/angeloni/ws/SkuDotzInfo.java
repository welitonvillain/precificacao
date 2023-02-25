
package br.com.angeloni.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for skuDotzInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="skuDotzInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dotz" type="{http://ws.integration.angeloni.com.br/}dotzInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skuId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skuDotzInfo", propOrder = {
    "dotz",
    "skuId"
})
public class SkuDotzInfo {

    @XmlElement(nillable = true)
    protected List<DotzInfo> dotz;
    protected String skuId;

    /**
     * Gets the value of the dotz property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dotz property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDotz().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DotzInfo }
     * 
     * 
     */
    public List<DotzInfo> getDotz() {
        if (dotz == null) {
            dotz = new ArrayList<DotzInfo>();
        }
        return this.dotz;
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

}
