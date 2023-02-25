
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for syncProductRecipesInSku complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="syncProductRecipesInSku">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skuProductRecipes" type="{http://ws.integration.angeloni.com.br/}skuProductsRecipeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "syncProductRecipesInSku", propOrder = {
    "skuProductRecipes"
})
public class SyncProductRecipesInSku {

    protected SkuProductsRecipeInfo skuProductRecipes;

    /**
     * Gets the value of the skuProductRecipes property.
     * 
     * @return
     *     possible object is
     *     {@link SkuProductsRecipeInfo }
     *     
     */
    public SkuProductsRecipeInfo getSkuProductRecipes() {
        return skuProductRecipes;
    }

    /**
     * Sets the value of the skuProductRecipes property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkuProductsRecipeInfo }
     *     
     */
    public void setSkuProductRecipes(SkuProductsRecipeInfo value) {
        this.skuProductRecipes = value;
    }

}
