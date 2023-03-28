package com.bytesgo.littleproxy;

import java.util.Queue;
import org.junit.Test;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.chain.ProxyChainManager;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Tests that when there are no chained proxies, we get a bad gateway.
 */
public class NoChainedProxiesTest extends AbstractProxyTest {
  @Override
  protected void setUp() {
    this.proxyServer = bootstrapProxy().withPort(0).withChainProxyManager(new ProxyChainManager() {
      @Override
      public void lookupProxyChain(HttpRequest httpRequest, Queue<ProxyChain> proxyChains) {
        // Leave list empty
      }
    }).withIdleConnectionTimeout(1).start();
  }

  @Test
  public void testNoChainedProxy() throws Exception {
    ResponseInfo response = httpGetWithApacheClient(webHost, DEFAULT_RESOURCE, true, false);
    assertReceivedBadGateway(response);
  }
}
