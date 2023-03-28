package com.bytesgo.littleproxy;

import static com.bytesgo.littleproxy.model.enums.TransportProtocol.*;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import com.bytesgo.littleproxy.server.HttpProxyServerBootstrap;

public class UnencryptedTCPChainedProxyTest extends BaseChainedProxyTest {
    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(TCP);
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
                return false;
            }
        };
    }
}
