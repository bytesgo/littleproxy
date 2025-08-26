/**
 * 
 */
package com.bytesgo.littleproxy.util;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCounted;

/**
 * @author leeyazhou
 */
public class ReferenceCountUtil {

	public static boolean release(HttpRequest httpRequest) {
		if (httpRequest != null && httpRequest instanceof ReferenceCounted) {
			if (((ReferenceCounted) httpRequest).refCnt() > 0) {
				((ReferenceCounted) httpRequest).release();
				return true;
			}
		}
		return false;
	}
}
