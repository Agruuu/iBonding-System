package com.alibaba.cloud.ai.tongyi.audio.speech.api;

import org.springframework.ai.model.ResultMetadata;

/**
 * @author Agaru
 */

public interface SpeechMetadata extends ResultMetadata {

	/**
	 * Null Object.
	 */
	SpeechMetadata NULL = SpeechMetadata.create();

	/**
	 * Factory method used to construct a new {@link SpeechMetadata}.
	 * @return a new {@link SpeechMetadata}
	 */
	static SpeechMetadata create() {
		return new SpeechMetadata() {
		};
	}

}
