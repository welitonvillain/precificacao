
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mediaSetInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mediaSetInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.integration.angeloni.com.br/}cbcMediaSetInfo">
 *       &lt;sequence>
 *         &lt;element name="facebookUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nutritionFactsImage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zoomUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mediaSetInfo", propOrder = {
    "facebookUrl",
    "nutritionFactsImage",
    "zoomUrl"
})
public class MediaSetInfo
    extends CbcMediaSetInfo
{

    protected String facebookUrl;
    protected String nutritionFactsImage;
    protected String zoomUrl;

    /**
     * Gets the value of the facebookUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacebookUrl() {
        return facebookUrl;
    }

    /**
     * Sets the value of the facebookUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacebookUrl(String value) {
        this.facebookUrl = value;
    }

    /**
     * Gets the value of the nutritionFactsImage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNutritionFactsImage() {
        return nutritionFactsImage;
    }

    /**
     * Sets the value of the nutritionFactsImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNutritionFactsImage(String value) {
        this.nutritionFactsImage = value;
    }

    /**
     * Gets the value of the zoomUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoomUrl() {
        return zoomUrl;
    }

    /**
     * Sets the value of the zoomUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoomUrl(String value) {
        this.zoomUrl = value;
    }

}
