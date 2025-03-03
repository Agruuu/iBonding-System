package com.alibaba.cloud.ai.tongyi.metadata.audio;

import org.springframework.ai.model.ResultMetadata;

/**
 * @author Agaru
 */

public interface TongYiAudioTranscriptionMetadata extends ResultMetadata {

	/**
	 * A constant instance of {@link TongYiAudioTranscriptionMetadata} that represents a null or empty metadata.
	 */
	TongYiAudioTranscriptionMetadata NULL = TongYiAudioTranscriptionMetadata.create();

	/**
	 * Factory method for creating a new instance of {@link TongYiAudioTranscriptionMetadata}.
	 * @return a new instance of {@link TongYiAudioTranscriptionMetadata}
	 */
	static TongYiAudioTranscriptionMetadata create() {
		return new TongYiAudioTranscriptionMetadata() {
		};
	}

}
