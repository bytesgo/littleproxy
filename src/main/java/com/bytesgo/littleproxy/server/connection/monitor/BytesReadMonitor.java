package com.bytesgo.littleproxy.server.connection.monitor;

import com.bytesgo.littleproxy.logging.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Utility handler for monitoring bytes read on this connection.
 */
@Sharable
public abstract class BytesReadMonitor extends ChannelInboundHandlerAdapter {

  private final Logger LOG;

  public BytesReadMonitor(Logger log) {
    this.LOG = log;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      if (msg instanceof ByteBuf) {
        bytesRead(((ByteBuf) msg).readableBytes());
      }
    } catch (Throwable t) {
      LOG.warn("Unable to record bytesRead", t);
    } finally {
      super.channelRead(ctx, msg);
    }
  }

  protected abstract void bytesRead(int numberOfBytes);
}
