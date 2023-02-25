
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for syncSkuWithdrawStore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="syncSkuWithdrawStore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skuWithdrawStore" type="{http://ws.integration.angeloni.com.br/}skuWithdrawStoreInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "syncSkuWithdrawStore", propOrder = {
    "skuWithdrawStore"
})
public class SyncSkuWithdrawStore {

    protected SkuWithdrawStoreInfo skuWithdrawStore;

    /**
     * Gets the value of the skuWithdrawStore property.
     * 
     * @return
     *     possible object is
     *     {@link SkuWithdrawStoreInfo }
     *     
     */
    public SkuWithdrawStoreInfo getSkuWithdrawStore() {
        return skuWithdrawStore;
    }

    /**
     * Sets the value of the skuWithdrawStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkuWithdrawStoreInfo }
     *     
     */
    public void setSkuWithdrawStore(SkuWithdrawStoreInfo value) {
        this.skuWithdrawStore = value;
    }

}
