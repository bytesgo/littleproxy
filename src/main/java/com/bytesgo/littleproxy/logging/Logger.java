package com.bytesgo.littleproxy.logging;

/**
 * <p>
 * A helper class that logs messages for ProxyConnections. All it does is make sure that the Channel and current state
 * are always included in the log messages (if available).
 * </p>
 *
 * <p>
 * Note that this depends on us using a LocationAwareLogger so that we can report the line numbers of the caller rather
 * than this helper class. If the SLF4J binding does not provide a LocationAwareLogger, then a fallback to Logger is
 * provided.
 * </p>
 */
public interface Logger {

  void error(String message, Object... params);

  void error(String message, Throwable t);

  void warn(String message, Object... params);

  void warn(String message, Throwable t);

  void info(String message, Object... params);

  void info(String message, Throwable t);

  void debug(String message, Object... params);

  void debug(String message, Throwable t);

  void log(int level, String message, Object... params);

  void log(int level, String message, Throwable t);


}
