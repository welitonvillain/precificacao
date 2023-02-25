
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for setGetXPayY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setGetXPayY">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataRequest" type="{http://ws.integration.angeloni.com.br/}getXPayYListInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setGetXPayY", propOrder = {
    "dataRequest"
})
public class SetGetXPayY {

    protected GetXPayYListInfo dataRequest;

    /**
     * Gets the value of the dataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GetXPayYListInfo }
     *     
     */
    public GetXPayYListInfo getDataRequest() {
        return dataRequest;
    }

    /**
     * Sets the value of the dataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetXPayYListInfo }
     *     
     */
    public void setDataRequest(GetXPayYListInfo value) {
        this.dataRequest = value;
    }

}
