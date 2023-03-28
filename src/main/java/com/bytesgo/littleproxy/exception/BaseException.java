package com.bytesgo.littleproxy.exception;

/**
 * This exception indicates that the system was asked to use a TransportProtocol that it didn't know how to handle.
 */
public abstract class BaseException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public BaseException(String message) {
    super(message);
  }
}
