package com.bytesgo.littleproxy.config;

import com.bytesgo.littleproxy.server.ServerGroup;

/**
 * Configuration object for the proxy's thread pools. Controls the number of acceptor and worker threads in the Netty
 * {@link io.netty.channel.EventLoopGroup} used by the proxy.
 */
public class ThreadPoolConfiguration {
  private int acceptorThreadSize = ServerGroup.DEFAULT_INCOMING_ACCEPTOR_THREADS;
  private int clientToProxyWorkerThreadSize = ServerGroup.DEFAULT_INCOMING_WORKER_THREADS;
  private int proxyToServerWorkerThreadSize = ServerGroup.DEFAULT_OUTGOING_WORKER_THREADS;

  public int getClientToProxyWorkerThreadSize() {
    return clientToProxyWorkerThreadSize;
  }

  /**
   * Set the number of client-to-proxy worker threads to create. Worker threads perform the actual processing of client
   * requests. The default value is {@link ServerGroup#DEFAULT_INCOMING_WORKER_THREADS}.
   *
   * @param clientToProxyWorkerThreads number of client-to-proxy worker threads to create
   * @return this thread pool configuration instance, for chaining
   */
  public ThreadPoolConfiguration withClientToProxyWorkerThreadSize(int clientToProxyWorkerThreadSize) {
    this.clientToProxyWorkerThreadSize = clientToProxyWorkerThreadSize;
    return this;
  }

  public int getAcceptorThreadSize() {
    return acceptorThreadSize;
  }

  /**
   * Set the number of acceptor threads to create. Acceptor threads accept HTTP connections from the client and queue them
   * for processing by client-to-proxy worker threads. The default value is
   * {@link ServerGroup#DEFAULT_INCOMING_ACCEPTOR_THREADS}.
   *
   * @param acceptorThreadSize number of acceptor threads to create
   * @return this thread pool configuration instance, for chaining
   */
  public ThreadPoolConfiguration withAcceptorThreadSize(int acceptorThreadSize) {
    this.acceptorThreadSize = acceptorThreadSize;
    return this;
  }

  public int getProxyToServerWorkerThreadSize() {
    return proxyToServerWorkerThreadSize;
  }

  /**
   * Set the number of proxy-to-server worker threads to create. Proxy-to-server worker threads make requests to upstream
   * servers and process responses from the server. The default value is
   * {@link ServerGroup#DEFAULT_OUTGOING_WORKER_THREADS}.
   *
   * @param proxyToServerWorkerThreadSize number of proxy-to-server worker threads to create
   * @return this thread pool configuration instance, for chaining
   */
  public ThreadPoolConfiguration withProxyToServerWorkerThreadSize(int proxyToServerWorkerThreadSize) {
    this.proxyToServerWorkerThreadSize = proxyToServerWorkerThreadSize;
    return this;
  }

}
