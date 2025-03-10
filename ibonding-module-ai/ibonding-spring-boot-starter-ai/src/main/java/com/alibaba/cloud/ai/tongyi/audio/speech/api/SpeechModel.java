package com.alibaba.cloud.ai.tongyi.audio.speech.api;

import org.springframework.ai.model.Model;

import java.nio.ByteBuffer;

/**
 * @author Agaru
 */

@FunctionalInterface
public interface SpeechModel extends Model<SpeechPrompt, SpeechResponse> {

	/**
	 * Generates spoken audio from the provided text message.
	 * @param message the text message to be converted to audio.
	 * @return the resulting audio bytes.
	 */
	default ByteBuffer call(String message) {

		SpeechPrompt prompt = new SpeechPrompt(message);

		return call(prompt).getResult().getOutput();
	}

	/**
	 * Sends a speech request to the TongYi TTS API and returns the resulting speech response.
	 * @param request the speech prompt containing the input text and other parameters.
	 * @return the speech response containing the generated audio.
	 */
	SpeechResponse call(SpeechPrompt request);

}
