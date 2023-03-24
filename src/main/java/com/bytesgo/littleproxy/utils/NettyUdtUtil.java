/**
 * 
 */
package com.bytesgo.littleproxy.utils;

import java.nio.channels.spi.SelectorProvider;
import io.netty.channel.ChannelFactory;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtServerChannel;
import io.netty.channel.udt.nio.NioUdtProvider;

/**
 * @author leeyazhou
 *
 */
@SuppressWarnings("deprecation")
public class NettyUdtUtil {

  public static SelectorProvider getUdtProvider() {
    return NioUdtProvider.BYTE_PROVIDER;
  }

  public static ChannelFactory<UdtChannel> getChannel() {
    return NioUdtProvider.BYTE_CONNECTOR;
  }

  public static ChannelFactory<UdtServerChannel> getServerChannel() {
    return NioUdtProvider.BYTE_ACCEPTOR;
  }


}
