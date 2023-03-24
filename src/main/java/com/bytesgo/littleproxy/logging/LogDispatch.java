package com.bytesgo.littleproxy.logging;

/**
 * 
 * @author leeyazhou
 *
 */
public interface LogDispatch {
  void doLog(int level, String message, Object[] params, Throwable t);
}
