
package br.com.angeloni.ws;

import org.springframework.ui.Model;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "promotions", targetNamespace = "http://ws.integration.angeloni.com.br/", wsdlLocation = "file:/u/JavaPrecLote/wsdl/promotions.wsdl")
public class Promotions_Service
    extends Service
{

    private final static URL PROMOTIONS_WSDL_LOCATION;
    private final static WebServiceException PROMOTIONS_EXCEPTION;
    private final static QName PROMOTIONS_QNAME = new QName("http://ws.integration.angeloni.com.br/", "promotions");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
//            url = new URL("file:C:\\Users\\weliton.villain\\Documents\\Development\\Intellij\\precificacao\\src\\main\\resources\\wsdl\\promotions.wsdl");
            url = new URL("file:/u/JavaPrecLote/wsdl/promotions.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PROMOTIONS_WSDL_LOCATION = url;
        PROMOTIONS_EXCEPTION = e;
    }

    public Promotions_Service() {
        super(__getWsdlLocation(), PROMOTIONS_QNAME);
    }

    public Promotions_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), PROMOTIONS_QNAME, features);
    }

    public Promotions_Service(URL wsdlLocation) {
        super(wsdlLocation, PROMOTIONS_QNAME);
    }

    public Promotions_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PROMOTIONS_QNAME, features);
    }

    public Promotions_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Promotions_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Promotions
     */
    @WebEndpoint(name = "PromotionsPort")
    public Promotions getPromotionsPort() {
        return super.getPort(new QName("http://ws.integration.angeloni.com.br/", "PromotionsPort"), Promotions.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Promotions
     */
    @WebEndpoint(name = "PromotionsPort")
    public Promotions getPromotionsPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.integration.angeloni.com.br/", "PromotionsPort"), Promotions.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PROMOTIONS_EXCEPTION!= null) {
            throw PROMOTIONS_EXCEPTION;
        }
        return PROMOTIONS_WSDL_LOCATION;
    }

}