
package br.com.angeloni.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Promotions", targetNamespace = "http://ws.integration.angeloni.com.br/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Promotions {


    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setVirtualPack", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetVirtualPack")
    @ResponseWrapper(localName = "setVirtualPackResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetVirtualPackResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setVirtualPackRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setVirtualPackResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setVirtualPack/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setVirtualPack/Fault/ResultItemErrorInfo")
    })
    public List<String> setVirtualPack(
        @WebParam(name = "dataRequest", targetNamespace = "")
        VirtualPackServiceListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setGifts", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetGifts")
    @ResponseWrapper(localName = "setGiftsResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetGiftsResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setGiftsRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setGiftsResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setGifts/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setGifts/Fault/ResultItemErrorInfo")
    })
    public List<String> setGifts(
        @WebParam(name = "dataRequest", targetNamespace = "")
        GiftsListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setGetXPayY", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetGetXPayY")
    @ResponseWrapper(localName = "setGetXPayYResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetGetXPayYResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setGetXPayYRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setGetXPayYResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setGetXPayY/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setGetXPayY/Fault/ResultItemErrorInfo")
    })
    public List<String> setGetXPayY(
        @WebParam(name = "dataRequest", targetNamespace = "")
        GetXPayYListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setBuyXOrMorePayY", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetBuyXOrMorePayY")
    @ResponseWrapper(localName = "setBuyXOrMorePayYResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetBuyXOrMorePayYResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setBuyXOrMorePayYRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setBuyXOrMorePayYResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setBuyXOrMorePayY/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setBuyXOrMorePayY/Fault/ResultItemErrorInfo")
    })
    public List<String> setBuyXOrMorePayY(
        @WebParam(name = "dataRequest", targetNamespace = "")
        BuyXOrMorePayYListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setPromotionLastItem", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetPromotionLastItem")
    @ResponseWrapper(localName = "setPromotionLastItemResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetPromotionLastItemResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setPromotionLastItemRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setPromotionLastItemResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setPromotionLastItem/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setPromotionLastItem/Fault/ResultItemErrorInfo")
    })
    public List<String> setPromotionLastItem(
        @WebParam(name = "dataRequest", targetNamespace = "")
        PromotionLastItemListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

    /**
     * 
     * @param dataRequest
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws ResultItemErrorInfo_Exception
     * @throws ResultMessageErrorInfo_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setBulkPrice", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetBulkPrice")
    @ResponseWrapper(localName = "setBulkPriceResponse", targetNamespace = "http://ws.integration.angeloni.com.br/", className = "br.com.angeloni.ws.SetBulkPriceResponse")
    @Action(input = "http://ws.integration.angeloni.com.br/Promotions/setBulkPriceRequest", output = "http://ws.integration.angeloni.com.br/Promotions/setBulkPriceResponse", fault = {
        @FaultAction(className = ResultMessageErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setBulkPrice/Fault/ResultMessageErrorInfo"),
        @FaultAction(className = ResultItemErrorInfo_Exception.class, value = "http://ws.integration.angeloni.com.br/Promotions/setBulkPrice/Fault/ResultItemErrorInfo")
    })
    public List<String> setBulkPrice(
        @WebParam(name = "dataRequest", targetNamespace = "")
        BulkPriceListInfo dataRequest)
        throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception
    ;

}
