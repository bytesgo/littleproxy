package com.bytesgo.littleproxy;

import static com.bytesgo.littleproxy.model.enums.TransportProtocol.TCP;
import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import com.bytesgo.littleproxy.server.HttpProxyServerBootstrap;
import com.bytesgo.littleproxy.ssl.SelfSignedSslEngineSource;
import com.bytesgo.littleproxy.ssl.SslEngineSource;

/**
 * Tests that servers are authenticated and that if they're missing certs, we
 * get an error.
 */
public class BadServerAuthenticationTCPChainedProxyTest extends
        BaseChainedProxyTest {
    protected final SslEngineSource serverSslEngineSource = new SelfSignedSslEngineSource(
            "chain_proxy_keystore_1.jks");
    
    protected final SslEngineSource clientSslEngineSource = new SelfSignedSslEngineSource(
            "chain_proxy_keystore_2.jks");

    @Override
    protected boolean expectBadGatewayForEverything() {
        return true;
    }
    
    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(TCP)
                .withSslEngineSource(serverSslEngineSource);
    }

    @Override
    protected ProxyChain newChainedProxy() {
        return new BaseChainedProxy() {
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
                return clientSslEngineSource.newSslEngine();
            }
        };
    }
}
