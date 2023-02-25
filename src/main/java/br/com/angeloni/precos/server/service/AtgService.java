package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.config.WSMessageHandler;
import br.com.angeloni.ws.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.List;

@Service
@Slf4j(topic = "br.com.angeloni.ws")
public class AtgService {

    @Value("${atg.base-url}")
    private String baseUrl;

    private Promotions promotionsClient;
    private VersionedAssets assetsClient;

    @PostConstruct
    public void init() throws Exception {
        log.info("Carregando os clientes WS do ATG...");

        if (log.isDebugEnabled()) {
            //Enable JAX-WS debug
            System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        }

        URL promotionsWsdl = new URL(baseUrl + "/angeloni-bcc/promotions");
        promotionsClient = new Promotions_Service().getPromotionsPort();
        fixEndpointAddress((BindingProvider) promotionsClient, promotionsWsdl);

        URL assetsWsdl = new URL(baseUrl + "/angeloni-bcc/versionedAssets");
        assetsClient = new VersionedAssets_Service().getVersionedAssetsPort();
        fixEndpointAddress((BindingProvider) assetsClient, assetsWsdl);

        if (log.isDebugEnabled()) {
            //Enable message dump
            new WSMessageHandler().install(promotionsClient);
            new WSMessageHandler().install(assetsClient);
        }

        log.info("Clientes WS do ATG carregados com sucesso.");
    }

    private void fixEndpointAddress(BindingProvider client, URL url) {
        client.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString());
    }

    public PriceInfo getPrice(Long skuId, String priceList) {
        List<PriceInfo> list = assetsClient.getPrice(skuId.toString(), priceList, true);
        if (list == null || list.isEmpty()) {
            throw new IllegalStateException("Erro ao consultar preço para o produto: " + skuId + " e lista: " + priceList);
        }
        return list.get(0);
    }

    public ResultPriceListInfo syncPriceList(PriceListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return assetsClient.syncPriceList(request);
    }

    public List<String> setVirtualPack(VirtualPackServiceListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setVirtualPack(request);
    }

    public List<String> setGetXPayY(GetXPayYListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setGetXPayY(request);
    }

    public List<String> setPromotionLastItem(PromotionLastItemListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setPromotionLastItem(request);
    }

    public List<String> setGifts(GiftsListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setGifts(request);
    }

    public List<String> setBuyXOrMorePayY(BuyXOrMorePayYListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setBuyXOrMorePayY(request);
    }

    public List<String> setBulkPrice(BulkPriceListInfo request) throws ResultItemErrorInfo_Exception, ResultMessageErrorInfo_Exception {
        return promotionsClient.setBulkPrice(request);
    }
}

