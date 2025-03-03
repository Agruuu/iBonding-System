package com.alibaba.cloud.ai.tongyi.metadata.audio;

import com.alibaba.dashscope.audio.asr.transcription.TranscriptionResult;
import com.google.gson.JsonObject;
import org.springframework.ai.chat.metadata.EmptyRateLimit;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.model.ResponseMetadata;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.HashMap;



/**
 * @author Agaru
 */

public class TongYiAudioTranscriptionResponseMetadata extends HashMap<String, Object>  implements ResponseMetadata {

	@Nullable
	private RateLimit rateLimit;

	private JsonObject usage;

	protected static final String AI_METADATA_STRING = "{ @type: %1$s, rateLimit: %4$s }";

	/**
	 * NULL objects.
	 */
	public static final TongYiAudioTranscriptionResponseMetadata NULL = new TongYiAudioTranscriptionResponseMetadata() {
	};

	protected TongYiAudioTranscriptionResponseMetadata() {

		this(null, new JsonObject());
	}

	protected TongYiAudioTranscriptionResponseMetadata(JsonObject usage) {

		this(null, usage);
	}

	protected TongYiAudioTranscriptionResponseMetadata(@Nullable RateLimit rateLimit, JsonObject usage) {

		this.rateLimit = rateLimit;
		this.usage = usage;
	}

	public static TongYiAudioTranscriptionResponseMetadata from(TranscriptionResult result) {

		Assert.notNull(result, "TongYi Transcription must not be null");
		return new TongYiAudioTranscriptionResponseMetadata(result.getUsage());
	}

	@Nullable
	public RateLimit getRateLimit() {

		return this.rateLimit != null ? this.rateLimit : new EmptyRateLimit();
	}

	public void setRateLimit(@Nullable RateLimit rateLimit) {
		this.rateLimit = rateLimit;
	}

	public JsonObject getUsage() {
		return usage;
	}

	public void setUsage(JsonObject usage) {
		this.usage = usage;
	}

	@Override
	public String toString() {

		return AI_METADATA_STRING.formatted(getClass().getName(), getRateLimit());
	}

}
