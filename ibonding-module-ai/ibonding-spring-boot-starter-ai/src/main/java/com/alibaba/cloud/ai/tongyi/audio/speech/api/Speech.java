package com.alibaba.cloud.ai.tongyi.audio.speech.api;

import org.springframework.ai.model.ModelResult;
import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Agaru
 */

public class Speech implements ModelResult<ByteBuffer> {

	private final ByteBuffer audio;

	private SpeechMetadata speechMetadata;

	public Speech(ByteBuffer audio) {
		this.audio = audio;
	}

	@Override
	public ByteBuffer getOutput() {
		return this.audio;
	}

	@Override
	public SpeechMetadata getMetadata() {

		return speechMetadata != null ? speechMetadata : SpeechMetadata.NULL;
	}

	public Speech withSpeechMetadata(@Nullable SpeechMetadata speechMetadata) {

		this.speechMetadata = speechMetadata;
		return this;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {

			return true;
		}
		if (!(o instanceof Speech that)) {

			return false;
		}

		return Arrays.equals(audio.array(), that.audio.array())
				&& Objects.equals(speechMetadata, that.speechMetadata);
	}

	@Override
	public int hashCode() {

		return Objects.hash(Arrays.hashCode(audio.array()), speechMetadata);
	}

	@Override
	public String toString() {

		return "Speech{" + "text=" + audio + ", speechMetadata=" + speechMetadata + '}';
	}

}
