package com.bytesgo.littleproxy;

import static com.bytesgo.littleproxy.TransportProtocol.*;
import com.bytesgo.littleproxy.ChainedProxy;
import com.bytesgo.littleproxy.HttpProxyServerBootstrap;
import com.bytesgo.littleproxy.TransportProtocol;

public class MitmWithUnencryptedTCPChainedProxyTest extends MitmWithChainedProxyTest {
    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(TCP);
    }

    @Override
    protected ChainedProxy newChainedProxy() {
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
