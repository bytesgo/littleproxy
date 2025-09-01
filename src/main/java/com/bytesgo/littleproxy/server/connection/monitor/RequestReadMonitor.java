package com.bytesgo.littleproxy.server.connection.monitor;

import com.bytesgo.littleproxy.logging.Logger;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Utility handler for monitoring requests read on this connection.
 */
@Sharable
public abstract class RequestReadMonitor extends ChannelInboundHandlerAdapter {
  
  private final Logger LOG;

  public RequestReadMonitor(Logger log) {
    this.LOG = log;
  }
  
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      if (msg instanceof HttpRequest) {
        requestRead((HttpRequest) msg);
      }
    } catch (Throwable t) {
      LOG.warn("Unable to record bytesRead", t);
    } finally {
      super.channelRead(ctx, msg);
    }
  }

  protected abstract void requestRead(HttpRequest httpRequest);
}
