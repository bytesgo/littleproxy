package com.bytesgo.littleproxy;

import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.enums.TransportProtocol;
import com.bytesgo.littleproxy.extras.SelfSignedSslEngineSource;

public class EncryptedUDTChainedProxyTest extends BaseChainedProxyTest {
  private final SslEngineSource sslEngineSource = new SelfSignedSslEngineSource("chain_proxy_keystore_1.jks");

  @Override
  protected HttpProxyServerBootstrap upstreamProxy() {
    return super.upstreamProxy().withTransportProtocol(TransportProtocol.UDT).withSslEngineSource(sslEngineSource);
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
