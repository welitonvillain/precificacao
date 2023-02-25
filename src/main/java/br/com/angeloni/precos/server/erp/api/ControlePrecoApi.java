package br.com.angeloni.precos.server.erp.api;

import br.com.angeloni.precos.server.erp.model.ControlePreco;
import br.com.angeloni.precos.server.erp.wrapper.ControlePrecoListWrapper;
import br.com.angeloni.precos.server.erp.wrapper.ControlePrecoWrapper;
import br.com.angeloni.precos.server.erp.wrapper.RetornoResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ControlePrecoApi {

  private ErpClient client;

  @Autowired
  public ControlePrecoApi(ErpClient client) {
    this.client = client;
  }

  public RetornoResponseWrapper controlePrecoV01Post(ControlePreco body) throws RestClientException {
    if (body == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling pedidoDeliveryV01Post");
    }

    String path = UriComponentsBuilder.fromPath(client.getProperties().getPrecosUri()).build().toUriString();

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
    final HttpHeaders headerParams = new HttpHeaders();
    final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

    final String[] accepts = {
        "application/json"
    };
    final List<MediaType> accept = client.selectHeaderAccept(accepts);
    final String[] contentTypes = {};
    final MediaType contentType = client.selectHeaderContentType(contentTypes);

    ParameterizedTypeReference<RetornoResponseWrapper> returnType = new ParameterizedTypeReference<RetornoResponseWrapper>() {
    };
    return client.invokeAPI(path, HttpMethod.POST, queryParams, createPostBody(body), headerParams, formParams, accept, contentType, returnType);
  }

  private ControlePrecoWrapper createPostBody(ControlePreco body) {
    final ControlePrecoWrapper postBody = new ControlePrecoWrapper();
    ControlePrecoListWrapper list = new ControlePrecoListWrapper();
    list.setTtPrecificacao(Arrays.asList(body));
    postBody.setDsPrecificacao(list);
    return postBody;
  }
}
