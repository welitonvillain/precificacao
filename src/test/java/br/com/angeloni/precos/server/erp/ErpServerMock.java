package br.com.angeloni.precos.server.erp;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
public class ErpServerMock extends Dispatcher {

    private final MockWebServer server;

    @Setter
    @Getter
    private Function<RecordedRequest, MockResponse> requestCallback;

    public ErpServerMock() {
        this.server = new MockWebServer();
        this.server.setDispatcher(this);
        try {
            this.server.start();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public String getServerUrl() {
        String baseUrl = server.url("/").url().toString();
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    @Override
    public void shutdown() {
        try {
            requestCallback = null;
            server.shutdown();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public MockResponse dispatch(final RecordedRequest request) {
        if (requestCallback != null) {
            return requestCallback.apply(request);
        }
        return new MockResponse().setResponseCode(500);
    }

}
