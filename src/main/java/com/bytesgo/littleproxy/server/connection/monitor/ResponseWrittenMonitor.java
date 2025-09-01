package com.bytesgo.littleproxy.server.connection.monitor;

import com.bytesgo.littleproxy.logging.Logger;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Utility handler for monitoring responses written on this connection.
 */
@Sharable
public abstract class ResponseWrittenMonitor extends ChannelOutboundHandlerAdapter {

  private final Logger LOG;

  public ResponseWrittenMonitor(Logger log) {
    this.LOG = log;
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    try {
      if (msg instanceof HttpResponse) {
        responseWritten(((HttpResponse) msg));
      }
    } catch (Throwable t) {
      LOG.warn("Error while invoking responseWritten callback", t);
    } finally {
      super.write(ctx, msg, promise);
    }
  }

  protected abstract void responseWritten(HttpResponse httpResponse);
}
