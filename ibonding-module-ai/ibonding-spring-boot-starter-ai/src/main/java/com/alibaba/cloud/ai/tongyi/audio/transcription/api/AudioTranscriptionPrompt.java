package com.alibaba.cloud.ai.tongyi.audio.transcription.api;

import com.alibaba.cloud.ai.tongyi.audio.transcription.TongYiAudioTranscriptionOptions;
import org.springframework.ai.model.ModelRequest;
import org.springframework.core.io.Resource;

/**
 * @author Agaru
 */

public class AudioTranscriptionPrompt implements ModelRequest<Resource> {

	private Resource audioResource;

	private TongYiAudioTranscriptionOptions transcriptionOptions;

	public AudioTranscriptionPrompt(Resource resource) {
		this.audioResource = resource;
	}

	public AudioTranscriptionPrompt(Resource resource, TongYiAudioTranscriptionOptions transcriptionOptions) {
		this.audioResource = resource;
		this.transcriptionOptions = transcriptionOptions;
	}

	@Override
	public Resource getInstructions() {

		return audioResource;
	}

	@Override
	public TongYiAudioTranscriptionOptions getOptions() {

		return transcriptionOptions;
	}

}
