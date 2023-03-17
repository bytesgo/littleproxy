package org.littleshoot.proxy;

import static com.bytesgo.littleproxy.TransportProtocol.*;
import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.ChainedProxy;
import com.bytesgo.littleproxy.HttpProxyServerBootstrap;
import com.bytesgo.littleproxy.SslEngineSource;
import com.bytesgo.littleproxy.TransportProtocol;
import com.bytesgo.littleproxy.extras.SelfSignedSslEngineSource;

public class MitmWithEncryptedUDTChainedProxyTest extends MitmWithChainedProxyTest {
    private final SslEngineSource sslEngineSource = new SelfSignedSslEngineSource(
            "chain_proxy_keystore_1.jks");

    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(UDT)
                .withSslEngineSource(sslEngineSource);
    }

    @Override
    protected ChainedProxy newChainedProxy() {
        return new BaseChainedProxy() {
            @Override
            public TransportProtocol getTransportProtocol() {
                return TransportProtocol.UDT;
            }

            @Override
            public boolean requiresEncryption() {
                return true;
            }

            @Override
            public SSLEngine newSslEngine() {
                return sslEngineSource.newSslEngine();
            }
        };
    }
}
