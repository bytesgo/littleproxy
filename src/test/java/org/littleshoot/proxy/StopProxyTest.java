package org.littleshoot.proxy;

import org.junit.Test;
import com.bytesgo.littleproxy.HttpProxyServer;
import com.bytesgo.littleproxy.impl.DefaultHttpProxyServer;

public class StopProxyTest {
    @Test
    public void testStop() {
        HttpProxyServer proxyServer = DefaultHttpProxyServer.bootstrap()
                .withPort(0)
                .start();

        proxyServer.stop();
    }

    @Test
    public void testAbort() {
        HttpProxyServer proxyServer = DefaultHttpProxyServer.bootstrap()
                .withPort(0)
                .start();

        proxyServer.abort();
    }
}
