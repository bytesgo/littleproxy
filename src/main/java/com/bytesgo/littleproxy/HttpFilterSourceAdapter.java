package com.bytesgo.littleproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Convenience base class for implementations of {@link HttpFilterSource}.
 */
public class HttpFilterSourceAdapter implements HttpFilterSource {

  public HttpFilter filterRequest(HttpRequest originalRequest) {
    return new HttpFilterAdapter(originalRequest, null);
  }

  @Override
  public HttpFilter filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
    return filterRequest(originalRequest);
  }

  @Override
  public int getMaximumRequestBufferSizeInBytes() {
    return 0;
  }

  @Override
  public int getMaximumResponseBufferSizeInBytes() {
    return 0;
  }

}
