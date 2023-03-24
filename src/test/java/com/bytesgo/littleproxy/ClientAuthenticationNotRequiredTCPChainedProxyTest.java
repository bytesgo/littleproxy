package com.bytesgo.littleproxy;

import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.enums.TransportProtocol;
import com.bytesgo.littleproxy.extras.SelfSignedSslEngineSource;

/**
 * Tests that when client authentication is not required, it doesn't matter what certs the client sends.
 */
public class ClientAuthenticationNotRequiredTCPChainedProxyTest extends BaseChainedProxyTest {
  private final SslEngineSource serverSslEngineSource = new SelfSignedSslEngineSource("chain_proxy_keystore_1.jks");

  private final SslEngineSource clientSslEngineSource = new SelfSignedSslEngineSource("chain_proxy_keystore_1.jks", false, false);

  @Override
  protected HttpProxyServerBootstrap upstreamProxy() {
    return super.upstreamProxy().withTransportProtocol(TransportProtocol.TCP).withSslEngineSource(serverSslEngineSource)
        .withAuthenticateSslClients(false);
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
        return true;
      }

      @Override
      public SSLEngine newSslEngine() {
        return clientSslEngineSource.newSslEngine();
      }
    };
  }
}
