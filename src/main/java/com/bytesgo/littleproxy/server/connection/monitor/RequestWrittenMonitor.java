package com.bytesgo.littleproxy.server.connection.monitor;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Utility handler for monitoring requests written on this connection.
 */
@Sharable
public abstract class RequestWrittenMonitor extends ChannelOutboundHandlerAdapter {
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    HttpRequest originalRequest = null;
    if (msg instanceof HttpRequest) {
      originalRequest = (HttpRequest) msg;
    }

    if (null != originalRequest) {
      requestWriting(originalRequest);
    }

    super.write(ctx, msg, promise);

    if (null != originalRequest) {
      requestWritten(originalRequest);
    }

    if (msg instanceof HttpContent) {
      contentWritten((HttpContent) msg);
    }
  }

  /**
   * Invoked immediately before an HttpRequest is written.
   */
  protected abstract void requestWriting(HttpRequest httpRequest);

  /**
   * Invoked immediately after an HttpRequest has been sent.
   */
  protected abstract void requestWritten(HttpRequest httpRequest);

  /**
   * Invoked immediately after an HttpContent has been sent.
   */
  protected abstract void contentWritten(HttpContent httpContent);
}
