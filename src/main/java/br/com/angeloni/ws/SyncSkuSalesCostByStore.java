
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for syncSkuSalesCostByStore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="syncSkuSalesCostByStore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataRequest" type="{http://ws.integration.angeloni.com.br/}skuSalesCostByStoreInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "syncSkuSalesCostByStore", propOrder = {
    "dataRequest"
})
public class SyncSkuSalesCostByStore {

    protected SkuSalesCostByStoreInfo dataRequest;

    /**
     * Gets the value of the dataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SkuSalesCostByStoreInfo }
     *     
     */
    public SkuSalesCostByStoreInfo getDataRequest() {
        return dataRequest;
    }

    /**
     * Sets the value of the dataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkuSalesCostByStoreInfo }
     *     
     */
    public void setDataRequest(SkuSalesCostByStoreInfo value) {
        this.dataRequest = value;
    }

}
