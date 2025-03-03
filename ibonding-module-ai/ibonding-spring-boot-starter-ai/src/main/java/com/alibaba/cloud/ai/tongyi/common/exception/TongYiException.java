package com.alibaba.cloud.ai.tongyi.common.exception;

/**
 * TongYi models runtime exception.
 *
 * @author Agaru
 */

public class TongYiException extends RuntimeException {

	public TongYiException(String message) {

		super(message);
	}

	public TongYiException(String message, Throwable cause) {

		super(message, cause);
	}
}
