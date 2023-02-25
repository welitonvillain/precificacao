package br.com.angeloni.precos.server.erp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

@Service
public class ErpClient {

    private HttpHeaders defaultHeaders = new HttpHeaders();
    private RestTemplate restTemplate;
    private ErpProperties properties;

    @Autowired
    public ErpClient(ErpProperties properties, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public ErpProperties getProperties() {
        return properties;
    }

    /**
     * Add a default header.
     *
     * @param name  The header's name
     * @param value The header's value
     * @return ToledoClient this client
     */
    public ErpClient addDefaultHeader(String name, String value) {
        if (defaultHeaders.containsKey(name)) {
            defaultHeaders.remove(name);
        }
        defaultHeaders.add(name, value);
        return this;
    }

    /**
     * Check if the given MIME is a JSON MIME. JSON MIME examples: application/json application/json; charset=UTF8
     * APPLICATION/JSON
     *
     * @param mediaType the input MediaType
     * @return boolean true if the MediaType represents JSON, false otherwise
     */
    public boolean isJsonMime(MediaType mediaType) {
        return mediaType != null && (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || mediaType.getSubtype()
                .matches("^.*\\+json[;]?\\s*$"));
    }

    /**
     * Select the Accept header's value from the given accepts array: if JSON exists in the given array, use it;
     * otherwise use all of them (joining into a string)
     *
     * @param accepts The accepts array to select from
     * @return List The list of MediaTypes to use for the Accept header
     */
    public List<MediaType> selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        for (String accept : accepts) {
            MediaType mediaType = MediaType.parseMediaType(accept);
            if (isJsonMime(mediaType)) {
                return Collections.singletonList(mediaType);
            }
        }
        return MediaType.parseMediaTypes(StringUtils.arrayToCommaDelimitedString(accepts));
    }

    /**
     * Select the Content-Type header's value from the given array: if JSON exists in the given array, use it; otherwise
     * use the first one of the array.
     *
     * @param contentTypes The Content-Type array to select from
     * @return MediaType The Content-Type header to use. If the given array is empty, JSON will be used.
     */
    public MediaType selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0) {
            return MediaType.APPLICATION_JSON;
        }
        for (String contentType : contentTypes) {
            MediaType mediaType = MediaType.parseMediaType(contentType);
            if (isJsonMime(mediaType)) {
                return mediaType;
            }
        }
        return MediaType.parseMediaType(contentTypes[0]);
    }

    /**
     * Select the body to use for the request
     *
     * @param obj         the body object
     * @param formParams  the form parameters
     * @param contentType the content type of the request
     * @return Object the selected body
     */
    protected Object selectBody(Object obj, MultiValueMap<String, Object> formParams, MediaType contentType) {
        boolean isForm =
                MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType) || MediaType.APPLICATION_FORM_URLENCODED
                        .isCompatibleWith(contentType);
        return isForm ? formParams : obj;
    }

    /**
     * Invoke API by sending HTTP request with the given options.
     *
     * @param <T>          the return type to use
     * @param path         The sub-path of the HTTP URL
     * @param method       The request method
     * @param queryParams  The query parameters
     * @param body         The request body object
     * @param headerParams The header parameters
     * @param formParams   The form parameters
     * @param accept       The request's Accept header
     * @param contentType  The request's Content-Type header
     * @param returnType   The return type into which to deserialize the response
     * @return The response body in chosen type
     */
    public <T> T invokeAPI(String path, HttpMethod method, MultiValueMap<String, String> queryParams, Object body,
                           HttpHeaders headerParams, MultiValueMap<String, Object> formParams, List<MediaType> accept,
                           MediaType contentType, ParameterizedTypeReference<T> returnType) throws RestClientException {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl()).path(path);
        if (queryParams != null) {
            builder.queryParams(queryParams);
        }

        final BodyBuilder requestBuilder = RequestEntity.method(method, builder.build().toUri());
        if (accept != null) {
            requestBuilder.accept(accept.toArray(new MediaType[accept.size()]));
        }
        if (contentType != null) {
            requestBuilder.contentType(contentType);
        }

        addHeadersToRequest(headerParams, requestBuilder);
        addHeadersToRequest(defaultHeaders, requestBuilder);

        RequestEntity<Object> requestEntity = requestBuilder.body(selectBody(body, formParams, contentType));

        ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, returnType);

        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return null;
        } else if (responseEntity.getStatusCode().is2xxSuccessful()) {
            if (returnType == null) {
                return null;
            }
            return responseEntity.getBody();
        } else {
            // The error handler built into the RestTemplate should handle 400 and 500 series errors.
            throw new RestClientException("API returned " + responseEntity.getStatusCode()
                    + " and it wasn't handled by the RestTemplate error handler");
        }
    }

    /**
     * Add headers to the request that is being built
     *
     * @param headers        The headers to add
     * @param requestBuilder The current request
     */
    protected void addHeadersToRequest(HttpHeaders headers, BodyBuilder requestBuilder) {
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = entry.getValue();
            for (String value : values) {
                if (value != null) {
                    requestBuilder.header(entry.getKey(), value);
                }
            }
        }
    }
}
