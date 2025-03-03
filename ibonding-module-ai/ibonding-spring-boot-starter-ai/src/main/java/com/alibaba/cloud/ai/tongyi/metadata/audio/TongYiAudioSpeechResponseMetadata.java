package com.alibaba.cloud.ai.tongyi.metadata.audio;

import com.alibaba.dashscope.audio.tts.SpeechSynthesisResult;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisUsage;
import com.alibaba.dashscope.audio.tts.timestamp.Sentence;
import org.springframework.ai.chat.metadata.EmptyRateLimit;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.model.ResponseMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;



/**
 * @author Agaru
 */

public class TongYiAudioSpeechResponseMetadata extends HashMap<String, Object>  implements ResponseMetadata {

	private SpeechSynthesisUsage usage;

	private String requestId;

	private Sentence time;

	protected static final String AI_METADATA_STRING = "{ @type: %1$s, requestsLimit: %2$s }";

	/**
	 * NULL objects.
	 */
	public static final TongYiAudioSpeechResponseMetadata NULL = new TongYiAudioSpeechResponseMetadata() {
	};

	public static TongYiAudioSpeechResponseMetadata from(SpeechSynthesisResult result) {

		Assert.notNull(result, "TongYi AI speech must not be null");
		TongYiAudioSpeechResponseMetadata speechResponseMetadata = new TongYiAudioSpeechResponseMetadata();



		return speechResponseMetadata;
	}

	public static TongYiAudioSpeechResponseMetadata from(String result) {

		Assert.notNull(result, "TongYi AI speech must not be null");
		TongYiAudioSpeechResponseMetadata speechResponseMetadata = new TongYiAudioSpeechResponseMetadata();

		return speechResponseMetadata;
	}

	@Nullable
	private RateLimit rateLimit;

	public TongYiAudioSpeechResponseMetadata() {

		this(null);
	}

	public TongYiAudioSpeechResponseMetadata(@Nullable RateLimit rateLimit) {

		this.rateLimit = rateLimit;
	}

	@Nullable
	public RateLimit getRateLimit() {

		RateLimit rateLimit = this.rateLimit;
		return rateLimit != null ? rateLimit : new EmptyRateLimit();
	}

	public TongYiAudioSpeechResponseMetadata withRateLimit(RateLimit rateLimit) {

		this.rateLimit = rateLimit;
		return this;
	}

	public TongYiAudioSpeechResponseMetadata withUsage(SpeechSynthesisUsage usage) {

		this.usage = usage;
		return this;
	}

	public TongYiAudioSpeechResponseMetadata withRequestId(String id) {

		this.requestId = id;
		return this;
	}

	public TongYiAudioSpeechResponseMetadata withSentence(Sentence sentence) {

		this.time = sentence;
		return this;
	}

	public SpeechSynthesisUsage getUsage() {
		return usage;
	}

	public String getRequestId() {
		return requestId;
	}

	public Sentence getTime() {
		return time;
	}

	@Override
	public String toString() {
		return AI_METADATA_STRING.formatted(getClass().getName(), getRateLimit());
	}

}
