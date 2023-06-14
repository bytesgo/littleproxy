package com.bytesgo.littleproxy.server;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bytesgo.littleproxy.auth.ProxyAuthenticator;
import com.bytesgo.littleproxy.chain.ProxyChainManager;
import com.bytesgo.littleproxy.config.ProxyServerConfiguration;
import com.bytesgo.littleproxy.dns.DnsSecServerResolver;
import com.bytesgo.littleproxy.filter.HttpFilterSource;
import com.bytesgo.littleproxy.filter.HttpFilterSourceAdapter;
import com.bytesgo.littleproxy.host.DefaultHostResolver;
import com.bytesgo.littleproxy.host.HostResolver;
import com.bytesgo.littleproxy.mitm.MitmManager;
import com.bytesgo.littleproxy.model.enums.TransportProtocol;
import com.bytesgo.littleproxy.ssl.SslEngineSource;
import com.bytesgo.littleproxy.tracker.ActivityTracker;
import com.bytesgo.littleproxy.util.ProxyUtil;

public class DefaultHttpProxyServerBootstrap implements HttpProxyServerBootstrap {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultHttpProxyServerBootstrap.class);
  private static final int MAX_INITIAL_LINE_LENGTH_DEFAULT = 8192;
  private static final int MAX_HEADER_SIZE_DEFAULT = 8192 * 2;
  private static final int MAX_CHUNK_SIZE_DEFAULT = 8192 * 2;
  private String name = "LittleProxy";
  private ServerGroup serverGroup = null;
  private TransportProtocol transportProtocol = TransportProtocol.TCP;
  private InetSocketAddress requestedAddress;
  private int port = 8080;
  private boolean allowLocalOnly = true;
  private SslEngineSource sslEngineSource = null;
  private boolean authenticateSslClients = true;
  private ProxyAuthenticator proxyAuthenticator = null;
  private ProxyChainManager chainProxyManager = null;
  private MitmManager mitmManager = null;
  private HttpFilterSource filtersSource = new HttpFilterSourceAdapter();
  private boolean transparent = false;
  private int idleConnectionTimeout = 70;
  private Collection<ActivityTracker> activityTrackers = new ConcurrentLinkedQueue<ActivityTracker>();
  private int connectTimeout = 40000;
  private HostResolver serverResolver = new DefaultHostResolver();
  private long readThrottleBytesPerSecond;
  private long writeThrottleBytesPerSecond;
  private InetSocketAddress localAddress;
  private String proxyAlias;
  private ProxyServerConfiguration proxyServerConfiguration;
  private int maxInitialLineLength = MAX_INITIAL_LINE_LENGTH_DEFAULT;
  private int maxHeaderSize = MAX_HEADER_SIZE_DEFAULT;
  private int maxChunkSize = MAX_CHUNK_SIZE_DEFAULT;
  private boolean allowRequestToOriginServer = false;

  public DefaultHttpProxyServerBootstrap() {}

  public DefaultHttpProxyServerBootstrap(ServerGroup serverGroup, TransportProtocol transportProtocol, InetSocketAddress requestedAddress,
      SslEngineSource sslEngineSource, boolean authenticateSslClients, ProxyAuthenticator proxyAuthenticator,
      ProxyChainManager chainProxyManager, MitmManager mitmManager, HttpFilterSource filtersSource, boolean transparent,
      int idleConnectionTimeout, Collection<ActivityTracker> activityTrackers, int connectTimeout, HostResolver serverResolver,
      long readThrottleBytesPerSecond, long writeThrottleBytesPerSecond, InetSocketAddress localAddress, String proxyAlias,
      int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean allowRequestToOriginServer) {
    this.serverGroup = serverGroup;
    this.transportProtocol = transportProtocol;
    this.requestedAddress = requestedAddress;
    this.port = requestedAddress.getPort();
    this.sslEngineSource = sslEngineSource;
    this.authenticateSslClients = authenticateSslClients;
    this.proxyAuthenticator = proxyAuthenticator;
    this.chainProxyManager = chainProxyManager;
    this.mitmManager = mitmManager;
    this.filtersSource = filtersSource;
    this.transparent = transparent;
    this.idleConnectionTimeout = idleConnectionTimeout;
    if (activityTrackers != null) {
      this.activityTrackers.addAll(activityTrackers);
    }
    this.connectTimeout = connectTimeout;
    this.serverResolver = serverResolver;
    this.readThrottleBytesPerSecond = readThrottleBytesPerSecond;
    this.writeThrottleBytesPerSecond = writeThrottleBytesPerSecond;
    this.localAddress = localAddress;
    this.proxyAlias = proxyAlias;
    this.maxInitialLineLength = maxInitialLineLength;
    this.maxHeaderSize = maxHeaderSize;
    this.maxChunkSize = maxChunkSize;
    this.allowRequestToOriginServer = allowRequestToOriginServer;
  }

  public DefaultHttpProxyServerBootstrap(Properties props) {
    this.withUseDnsSec(ProxyUtil.extractBooleanDefaultFalse(props, "dnssec"));
    this.transparent = ProxyUtil.extractBooleanDefaultFalse(props, "transparent");
    this.idleConnectionTimeout = ProxyUtil.extractInt(props, "idle_connection_timeout");
    this.connectTimeout = ProxyUtil.extractInt(props, "connect_timeout", 0);
    this.maxInitialLineLength = ProxyUtil.extractInt(props, "max_initial_line_length", MAX_INITIAL_LINE_LENGTH_DEFAULT);
    this.maxHeaderSize = ProxyUtil.extractInt(props, "max_header_size", MAX_HEADER_SIZE_DEFAULT);
    this.maxChunkSize = ProxyUtil.extractInt(props, "max_chunk_size", MAX_CHUNK_SIZE_DEFAULT);
  }

  @Override
  public HttpProxyServerBootstrap withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withTransportProtocol(TransportProtocol transportProtocol) {
    this.transportProtocol = transportProtocol;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withAddress(InetSocketAddress address) {
    this.requestedAddress = address;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withPort(int port) {
    this.requestedAddress = null;
    this.port = port;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withNetworkInterface(InetSocketAddress inetSocketAddress) {
    this.localAddress = inetSocketAddress;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withProxyAlias(String alias) {
    this.proxyAlias = alias;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withAllowLocalOnly(boolean allowLocalOnly) {
    this.allowLocalOnly = allowLocalOnly;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withSslEngineSource(SslEngineSource sslEngineSource) {
    this.sslEngineSource = sslEngineSource;
    if (this.mitmManager != null) {
      LOG.warn("Enabled encrypted inbound connections with man in the middle. "
          + "These are mutually exclusive - man in the middle will be disabled.");
      this.mitmManager = null;
    }
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withAuthenticateSslClients(boolean authenticateSslClients) {
    this.authenticateSslClients = authenticateSslClients;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withProxyAuthenticator(ProxyAuthenticator proxyAuthenticator) {
    this.proxyAuthenticator = proxyAuthenticator;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withChainProxyManager(ProxyChainManager chainProxyManager) {
    this.chainProxyManager = chainProxyManager;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withManInTheMiddle(MitmManager mitmManager) {
    this.mitmManager = mitmManager;
    if (this.sslEngineSource != null) {
      LOG.warn("Enabled man in the middle with encrypted inbound connections. "
          + "These are mutually exclusive - encrypted inbound connections will be disabled.");
      this.sslEngineSource = null;
    }
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withFiltersSource(HttpFilterSource filtersSource) {
    this.filtersSource = filtersSource;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withUseDnsSec(boolean useDnsSec) {
    if (useDnsSec) {
      this.serverResolver = new DnsSecServerResolver();
    } else {
      this.serverResolver = new DefaultHostResolver();
    }
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withTransparent(boolean transparent) {
    this.transparent = transparent;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withIdleConnectionTimeout(int idleConnectionTimeout) {
    this.idleConnectionTimeout = idleConnectionTimeout;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withServerResolver(HostResolver serverResolver) {
    this.serverResolver = serverResolver;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap plusActivityTracker(ActivityTracker activityTracker) {
    activityTrackers.add(activityTracker);
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withThrottling(long readThrottleBytesPerSecond, long writeThrottleBytesPerSecond) {
    this.readThrottleBytesPerSecond = readThrottleBytesPerSecond;
    this.writeThrottleBytesPerSecond = writeThrottleBytesPerSecond;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withMaxInitialLineLength(int maxInitialLineLength) {
    this.maxInitialLineLength = maxInitialLineLength;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withMaxHeaderSize(int maxHeaderSize) {
    this.maxHeaderSize = maxHeaderSize;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withMaxChunkSize(int maxChunkSize) {
    this.maxChunkSize = maxChunkSize;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withAllowRequestToOriginServer(boolean allowRequestToOriginServer) {
    this.allowRequestToOriginServer = allowRequestToOriginServer;
    return this;
  }

  @Override
  public HttpProxyServerBootstrap withThreadPoolConfiguration(ProxyServerConfiguration proxyServerConfiguration) {
    this.proxyServerConfiguration = proxyServerConfiguration;
    return this;
  }

  @Override
  public HttpProxyServer build() {
    final ServerGroup serverGroup;

    if (this.serverGroup != null) {
      serverGroup = this.serverGroup;
    } else {
      serverGroup = new ServerGroup(name, proxyServerConfiguration);
    }

    return new DefaultHttpProxyServer(serverGroup, transportProtocol, determineListenAddress(), sslEngineSource, authenticateSslClients,
        proxyAuthenticator, chainProxyManager, mitmManager, filtersSource, transparent, idleConnectionTimeout, activityTrackers,
        connectTimeout, serverResolver, readThrottleBytesPerSecond, writeThrottleBytesPerSecond, localAddress, proxyAlias,
        maxInitialLineLength, maxHeaderSize, maxChunkSize, allowRequestToOriginServer);
  }

  private InetSocketAddress determineListenAddress() {
    if (requestedAddress != null) {
      return requestedAddress;
    } else {
      // Binding only to localhost can significantly improve the
      // security of the proxy.
      if (allowLocalOnly) {
        return new InetSocketAddress("127.0.0.1", port);
      } else {
        return new InetSocketAddress(port);
      }
    }
  }
}
