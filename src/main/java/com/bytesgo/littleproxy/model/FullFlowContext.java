package com.bytesgo.littleproxy.model;

import com.bytesgo.littleproxy.chain.ProxyChain;
import com.bytesgo.littleproxy.server.connection.ClientToProxyConnection;
import com.bytesgo.littleproxy.server.connection.ProxyToServerConnection;

/**
 * Extension of {@link FlowContext} that provides additional information (which we know after actually processing the
 * request from the client).
 */
public class FullFlowContext extends FlowContext {
  private final String serverHostAndPort;
  private final ProxyChain proxyChain;

  public FullFlowContext(ClientToProxyConnection clientConnection, ProxyToServerConnection serverConnection) {
    super(clientConnection);
    this.serverHostAndPort = serverConnection.getServerHostAndPort();
    this.proxyChain = serverConnection.getChainedProxy();
  }

  /**
   * The host and port for the server (i.e. the ultimate endpoint).
   * 
   * @return String
   */
  public String getServerHostAndPort() {
    return serverHostAndPort;
  }

  /**
   * The chained proxy (if proxy chaining).
   * 
   * @return ChainedProxy
   */
  public ProxyChain getChainedProxy() {
    return proxyChain;
  }

}
