package com.bytesgo.littleproxy;

import com.bytesgo.littleproxy.chain.ProxyChainAdapter;
import com.bytesgo.littleproxy.chain.ProxyChainContext;
import com.bytesgo.littleproxy.chain.ProxyChainManager;

/**
 * Tests a proxy chained to a downstream proxy with an untrusted SSL cert. When
 * the downstream proxy is unavailable, the downstream proxy should just fall
 * back to a direct connection.
 */
public class ChainedProxyWithFallbackToDirectDueToSSLTest extends BadServerAuthenticationTCPChainedProxyTest {
    @Override
    protected boolean isChained() {
        // Set this to false since we don't actually expect anything to go
        // through the chained proxy
        return false;
    }

    @Override
    protected boolean expectBadGatewayForEverything() {
        return false;
    }

    protected ProxyChainManager proxyChainManager() {
        return new ProxyChainManager() {
            @Override
            public void lookupProxyChain(ProxyChainContext proxyChainContext) {
                // This first one has a bad cert
                proxyChainContext.getProxyChains().add(newChainedProxy());
                proxyChainContext.getProxyChains().add(ProxyChainAdapter.FALLBACK_TO_DIRECT_CONNECTION);
            }
        };
    }
}
