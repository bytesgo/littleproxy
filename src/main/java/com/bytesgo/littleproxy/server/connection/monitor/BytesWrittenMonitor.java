package com.bytesgo.littleproxy.server.connection.monitor;

import com.bytesgo.littleproxy.logging.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Utility handler for monitoring bytes written on this connection.
 */
@Sharable
public abstract class BytesWrittenMonitor extends ChannelOutboundHandlerAdapter {

  private final Logger LOG;

  public BytesWrittenMonitor(Logger log) {
    this.LOG = log;
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    try {
      if (msg instanceof ByteBuf) {
        bytesWritten(((ByteBuf) msg).readableBytes());
      }
    } catch (Throwable t) {
      LOG.warn("Unable to record bytesRead", t);
    } finally {
      super.write(ctx, msg, promise);
    }
  }

  protected abstract void bytesWritten(int numberOfBytes);
}
