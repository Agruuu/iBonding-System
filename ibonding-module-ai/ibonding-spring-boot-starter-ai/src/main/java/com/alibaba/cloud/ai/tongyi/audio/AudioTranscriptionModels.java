package com.alibaba.cloud.ai.tongyi.audio;

/**
 * @author Agaru
 */

public final class AudioTranscriptionModels {

	private AudioTranscriptionModels() {
	}

	/**
	 * Paraformer Chinese and English speech recognition model supports audio or video speech recognition with a sampling rate of 16kHz or above.
	 */
	public static final String Paraformer_V1 = "paraformer-v1";
	/**
	 * Paraformer Chinese speech recognition model, support 8kHz telephone speech recognition.
	 */
	public static final String Paraformer_8K_V1 = "paraformer-8k-v1";
	/**
	 * The Paraformer multilingual speech recognition model supports audio or video speech recognition with a sample rate of 16kHz or above.
	 */
	public static final String Paraformer_MTL_V1 = "paraformer-mtl-v1";

}
