package br.com.angeloni.precos.server.config;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WSMessageHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            System.out.println("--- WSMessageHandler ---");
            context.getMessage().writeTo(System.out);
            System.out.println("\n------------------------");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        try {
            System.out.println("--- WSMessageHandler ---");
            context.getMessage().writeTo(System.out);
            System.out.println("\n------------------------");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    public void install(Object port) {
        BindingProvider bindingProvider = (BindingProvider)port;
        Binding binding = bindingProvider.getBinding();
        List<Handler> handlerChain = binding.getHandlerChain();
        handlerChain.add(this);
        binding.setHandlerChain(handlerChain);
    }
}
