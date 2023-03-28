package com.bytesgo.littleproxy;

import static com.bytesgo.littleproxy.model.enums.TransportProtocol.*;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import com.bytesgo.littleproxy.server.HttpProxyServerBootstrap;

public class MitmWithUnencryptedUDTChainedProxyTest extends MitmWithChainedProxyTest {
    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(UDT);
    }

    @Override
    protected ProxyChain newChainedProxy() {
        return new BaseChainedProxy() {
            @Override
            public TransportProtocol getTransportProtocol() {
                return TransportProtocol.UDT;
            }

            @Override
            public boolean requiresEncryption() {
                return false;
            }
        };
    }
}
