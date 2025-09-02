/**
 * 
 */
package com.bytesgo.littleproxy.server.connection;

import com.bytesgo.littleproxy.model.enums.ConnectionState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 
 */
public interface ConnectionLifecycle<I> {

  /**
   * Implement this to handle reading the initial object (e.g. {@link HttpRequest}
   * or {@link HttpResponse}).
   * 
   * @param httpObject
   * @return ConnectionState
   */
  ConnectionState readHTTPInitial(I httpObject);

  /**
   * Implement this to handle reading a chunk in a chunked transfer.
   * 
   * @param chunk
   */
  void readHTTPChunk(HttpContent chunk);

  /**
   * Implement this to handle reading a raw buffer as they are used in HTTP
   * tunneling.
   * 
   * @param buf
   */
  void readRaw(ByteBuf buf);

  ChannelFuture writeToChannel(final Object msg);

  void disconnected();
}
