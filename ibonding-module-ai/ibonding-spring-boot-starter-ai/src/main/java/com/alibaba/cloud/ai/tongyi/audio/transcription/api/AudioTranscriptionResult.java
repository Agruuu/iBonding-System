package com.alibaba.cloud.ai.tongyi.audio.transcription.api;

import com.alibaba.cloud.ai.tongyi.metadata.audio.TongYiAudioTranscriptionMetadata;
import org.springframework.ai.model.ModelResult;
import org.springframework.ai.model.ResultMetadata;

import java.util.Objects;

/**
 * @author Agaru
 */
public class AudioTranscriptionResult implements ModelResult<String> {

	private String text;

	private TongYiAudioTranscriptionMetadata transcriptionMetadata;

	public AudioTranscriptionResult(String text) {
		this.text = text;
	}

	@Override
	public String getOutput() {

		return this.text;
	}

	@Override
	public ResultMetadata getMetadata() {

		return transcriptionMetadata != null ? transcriptionMetadata : TongYiAudioTranscriptionMetadata.NULL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AudioTranscriptionResult that = (AudioTranscriptionResult) o;
		return Objects.equals(text, that.text) && Objects.equals(transcriptionMetadata, that.transcriptionMetadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, transcriptionMetadata);
	}
}
