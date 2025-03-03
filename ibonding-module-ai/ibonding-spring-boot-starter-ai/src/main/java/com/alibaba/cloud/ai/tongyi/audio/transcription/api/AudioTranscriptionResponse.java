package com.alibaba.cloud.ai.tongyi.audio.transcription.api;

import com.alibaba.cloud.ai.tongyi.metadata.audio.TongYiAudioTranscriptionResponseMetadata;
import org.springframework.ai.model.ModelResponse;
import org.springframework.ai.model.ResponseMetadata;

import java.util.List;

/**
 * @author Agaru
 */

public class AudioTranscriptionResponse implements ModelResponse<AudioTranscriptionResult> {

	private List<AudioTranscriptionResult> resultList;

	private TongYiAudioTranscriptionResponseMetadata transcriptionResponseMetadata;

	public AudioTranscriptionResponse(List<AudioTranscriptionResult> result) {

		this(result, TongYiAudioTranscriptionResponseMetadata.NULL);
	}

	public AudioTranscriptionResponse(List<AudioTranscriptionResult> result,
			TongYiAudioTranscriptionResponseMetadata transcriptionResponseMetadata) {

		this.resultList = List.copyOf(result);
		this.transcriptionResponseMetadata = transcriptionResponseMetadata;
	}

	@Override
	public AudioTranscriptionResult getResult() {

		return this.resultList.get(0);
	}

	@Override
	public List<AudioTranscriptionResult> getResults() {

		return this.resultList;
	}

	@Override
	public ResponseMetadata getMetadata() {

		return this.transcriptionResponseMetadata;
	}

}
