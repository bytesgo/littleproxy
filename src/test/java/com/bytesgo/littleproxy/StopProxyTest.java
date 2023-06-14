package com.bytesgo.littleproxy;

import org.junit.Test;
import com.bytesgo.littleproxy.server.DefaultHttpProxyServer;
import com.bytesgo.littleproxy.server.HttpProxyServer;

public class StopProxyTest {
  @Test
  public void testStop() {
    HttpProxyServer proxyServer = DefaultHttpProxyServer.bootstrap().withPort(0).build();
    proxyServer.start();

    proxyServer.stop();
  }

  @Test
  public void testAbort() {
    HttpProxyServer proxyServer = DefaultHttpProxyServer.bootstrap().withPort(0).build();
    proxyServer.start();

    proxyServer.abort();
  }
}
