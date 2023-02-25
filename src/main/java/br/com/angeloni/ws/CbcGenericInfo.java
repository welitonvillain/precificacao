
package br.com.angeloni.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cbcGenericInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cbcGenericInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cbcGenericInfo")
@XmlSeeAlso({
    SkuSalesCostByStoreInfo.class,
    SkuEanInfo.class,
    SkuMediaSetInfo.class,
    SkuRecipeInfo.class,
    SkuMaximumBuyInfo.class,
    ProductSkuMediasInfo.class,
    ProductFeature.class,
    ProductRecipeInfo.class,
    SkuSalesCostInfo.class,
    ProductBeverageInfo.class,
    PackageInfo.class,
    ProductInfo.class,
    PriceInfo.class,
    PamphletInfo.class,
    ProductCategoryInfo.class,
    SkuBeverageInfo.class,
    PriceListInfo.class,
    SkuInfo.class
})
public abstract class CbcGenericInfo {


}
