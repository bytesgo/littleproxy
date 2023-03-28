package com.bytesgo.littleproxy.chain;

import java.net.InetSocketAddress;
import javax.net.ssl.SSLEngine;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import io.netty.handler.codec.http.HttpObject;

/**
 * Convenience base class for implementations of {@link ProxyChain}.
 */
public class ProxyChainAdapter implements ProxyChain {
  /**
   * {@link ProxyChain} that simply has the downstream proxy make a direct connection to the upstream server.
   */
  public static ProxyChain FALLBACK_TO_DIRECT_CONNECTION = new ProxyChainAdapter();

  @Override
  public InetSocketAddress getChainedProxyAddress() {
    return null;
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return null;
  }

  @Override
  public TransportProtocol getTransportProtocol() {
    return TransportProtocol.TCP;
  }

  @Override
  public boolean requiresEncryption() {
    return false;
  }

  @Override
  public SSLEngine newSslEngine() {
    return null;
  }

  @Override
  public void filterRequest(HttpObject httpObject) {}

  @Override
  public void connectionSucceeded() {}

  @Override
  public void connectionFailed(Throwable cause) {}

  @Override
  public void disconnected() {}

  @Override
  public SSLEngine newSslEngine(String peerHost, int peerPort) {
    return null;
  }
}
