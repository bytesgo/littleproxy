package com.bytesgo.littleproxy.server;

import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import com.bytesgo.littleproxy.util.concurrent.NamedThreadFactory;
import com.google.common.collect.ImmutableList;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Encapsulates the thread pools used by the proxy. Contains the acceptor thread pool as well as the client-to-proxy and
 * proxy-to-server thread pools.
 */
public class ProxyThreadPool {
  /**
   * These {@link EventLoopGroup}s accept incoming connections to the proxies. A different EventLoopGroup is used for each
   * TransportProtocol, since these have to be configured differently.
   */
  private final NioEventLoopGroup clientToProxyAcceptor;

  /**
   * These {@link EventLoopGroup}s process incoming requests to the proxies. A different EventLoopGroup is used for each
   * TransportProtocol, since these have to be configured differently.
   */
  private final NioEventLoopGroup clientToProxyWorker;

  /**
   * These {@link EventLoopGroup}s are used for making outgoing connections to servers. A different EventLoopGroup is used
   * for each TransportProtocol, since these have to be configured differently.
   */
  private final NioEventLoopGroup proxyToServerWorker;

  public ProxyThreadPool(SelectorProvider selectorProvider, int incomingAcceptorThreadSize, int incomingWorkerThreadSize,
      int outgoingWorkerThreadSize, String serverGroupName, int serverGroupId) {
    this.clientToProxyAcceptor = new NioEventLoopGroup(incomingAcceptorThreadSize,
        new NamedThreadFactory(serverGroupName, "ClientToProxyAcceptor", serverGroupId), selectorProvider);

    this.clientToProxyWorker = new NioEventLoopGroup(incomingWorkerThreadSize,
        new NamedThreadFactory(serverGroupName, "ClientToProxyWorker", serverGroupId), selectorProvider);
    this.clientToProxyWorker.setIoRatio(90);

    this.proxyToServerWorker = new NioEventLoopGroup(outgoingWorkerThreadSize,
        new NamedThreadFactory(serverGroupName, "ProxyToServerWorker", serverGroupId), selectorProvider);
    this.proxyToServerWorker.setIoRatio(90);
  }

  /**
   * Returns all event loops (acceptor and worker thread pools) in this pool.
   */
  public List<EventLoopGroup> getAllEventLoops() {
    return ImmutableList.<EventLoopGroup>of(clientToProxyAcceptor, clientToProxyWorker, proxyToServerWorker);
  }

  public NioEventLoopGroup getClientToProxyAcceptor() {
    return clientToProxyAcceptor;
  }

  public NioEventLoopGroup getClientToProxyWorker() {
    return clientToProxyWorker;
  }

  public NioEventLoopGroup getProxyToServerWorker() {
    return proxyToServerWorker;
  }
}
