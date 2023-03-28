package com.bytesgo.littleproxy;

import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import com.bytesgo.littleproxy.server.HttpProxyServerBootstrap;
import com.bytesgo.littleproxy.ssl.SelfSignedSslEngineSource;
import com.bytesgo.littleproxy.ssl.SslEngineSource;

public class EncryptedTCPChainedProxyTest extends BaseChainedProxyTest {
  private final SslEngineSource sslEngineSource = new SelfSignedSslEngineSource("chain_proxy_keystore_1.jks");

  @Override
  protected HttpProxyServerBootstrap upstreamProxy() {
    return super.upstreamProxy().withTransportProtocol(TransportProtocol.TCP).withSslEngineSource(sslEngineSource);
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
        return sslEngineSource.newSslEngine();
      }
    };
  }
}
