/**
 * 
 */
package com.bytesgo.littleproxy.chain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.bytesgo.littleproxy.model.ProxyAuthorization;

import io.netty.handler.codec.http.HttpRequest;

/**
 * @author leeyazhou
 *
 */
public class ProxyChainContext {
    private HttpRequest httpRequest;
    private ProxyAuthorization proxyAuthorization;

    private Queue<ProxyChain> proxyChains = new ConcurrentLinkedQueue<>();

    public ProxyChainContext() {
    }

    public ProxyChainContext(HttpRequest httpRequest, ProxyAuthorization proxyAuthorization) {
        this.httpRequest = httpRequest;
        this.proxyAuthorization = proxyAuthorization;
    }

    /**
     * @return the httpRequest
     */
    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    /**
     * @param httpRequest the httpRequest to set
     */
    public ProxyChainContext setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        return this;
    }

    /**
     * @return the proxyAuthorization
     */
    public ProxyAuthorization getProxyAuthorization() {
        return proxyAuthorization;
    }

    /**
     * @param proxyAuthorization the proxyAuthorization to set
     */
    public ProxyChainContext setProxyAuthorization(ProxyAuthorization proxyAuthorization) {
        this.proxyAuthorization = proxyAuthorization;
        return this;
    }

    /**
     * @param proxyChains the proxyChains to set
     */
    public void setProxyChains(Queue<ProxyChain> proxyChains) {
        this.proxyChains = proxyChains;
    }

    /**
     * @return the proxyChains
     */
    public Queue<ProxyChain> getProxyChains() {
        return proxyChains;
    }

}
