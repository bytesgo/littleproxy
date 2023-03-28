package com.bytesgo.littleproxy;

import java.util.Queue;
import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.chain.ProxyChainManager;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Tests a proxy chained to a downstream proxy with an untrusted SSL cert. When the downstream proxy is unavailable, the
 * downstream proxy should just fall back to a the next chained proxy.
 */
public class ChainedProxyWithFallbackToOtherChainedProxyDueToSSLTest extends BadServerAuthenticationTCPChainedProxyTest {
  @Override
  protected boolean expectBadGatewayForEverything() {
    return false;
  }

  protected ProxyChainManager proxyChainManager() {
    return new ProxyChainManager() {
      @Override
      public void lookupProxyChain(HttpRequest httpRequest, Queue<ProxyChain> proxyChains) {
        // This first one has a bad cert
        proxyChains.add(newChainedProxy());
        // This 2nd one should work
        proxyChains.add(new BaseChainedProxy() {
          @Override
          public TransportProtocol getTransportProtocol() {
            return TransportProtocol.TCP;
          }

          @Override
          public boolean requiresEncryption() {
            return true;
          }

          @Override
          public SSLEngine newSslEngine() {
            return serverSslEngineSource.newSslEngine();
          }
        });
      }
    };
  }
}
