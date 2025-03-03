package com.alibaba.cloud.ai.tongyi.audio.transcription;

import com.alibaba.cloud.ai.tongyi.audio.AudioTranscriptionModels;
import org.springframework.ai.model.ModelOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Agaru
 */

public class TongYiAudioTranscriptionOptions implements ModelOptions {

	private String model = AudioTranscriptionModels.Paraformer_V1;

	private List<String> fileUrls = new ArrayList<>();

	private String phraseId = null;

	private List<Integer> channelId = Collections.singletonList(0);

	private Boolean diarizationEnabled = false;

	private Integer speakerCount = null;

	private Boolean disfluencyRemovalEnabled = false;

	private Boolean timestampAlignmentEnabled = false;

	private String specialWordFilter = "";

	private Boolean audioEventDetectionEnabled = false;

	public static Builder builder() {

		return new Builder();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<String> getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(List<String> fileUrls) {
		this.fileUrls = fileUrls;
	}

	public String getPhraseId() {
		return phraseId;
	}

	public void setPhraseId(String phraseId) {
		this.phraseId = phraseId;
	}

	public List<Integer> getChannelId() {
		return channelId;
	}

	public void setChannelId(List<Integer> channelId) {
		this.channelId = channelId;
	}

	public Boolean getDiarizationEnabled() {
		return diarizationEnabled;
	}

	public void setDiarizationEnabled(Boolean diarizationEnabled) {
		this.diarizationEnabled = diarizationEnabled;
	}

	public Integer getSpeakerCount() {
		return speakerCount;
	}

	public void setSpeakerCount(Integer speakerCount) {
		this.speakerCount = speakerCount;
	}

	public Boolean getDisfluencyRemovalEnabled() {
		return disfluencyRemovalEnabled;
	}

	public void setDisfluencyRemovalEnabled(Boolean disfluencyRemovalEnabled) {
		this.disfluencyRemovalEnabled = disfluencyRemovalEnabled;
	}

	public Boolean getTimestampAlignmentEnabled() {
		return timestampAlignmentEnabled;
	}

	public void setTimestampAlignmentEnabled(Boolean timestampAlignmentEnabled) {
		this.timestampAlignmentEnabled = timestampAlignmentEnabled;
	}

	public String getSpecialWordFilter() {
		return specialWordFilter;
	}

	public void setSpecialWordFilter(String specialWordFilter) {
		this.specialWordFilter = specialWordFilter;
	}

	public Boolean getAudioEventDetectionEnabled() {
		return audioEventDetectionEnabled;
	}

	public void setAudioEventDetectionEnabled(Boolean audioEventDetectionEnabled) {
		this.audioEventDetectionEnabled = audioEventDetectionEnabled;
	}

	/**
	 * Builder class for constructing TongYiAudioTranscriptionOptions instances.
	 */
	public static class Builder {

		private final TongYiAudioTranscriptionOptions options = new TongYiAudioTranscriptionOptions();

		public Builder withModel(String model) {
			options.model = model;
			return this;
		}

		public Builder withFileUrls(List<String> fileUrls) {
			options.fileUrls = fileUrls;
			return this;
		}

		public Builder withPhraseId(String phraseId) {
			options.phraseId = phraseId;
			return this;
		}

		public Builder withChannelId(List<Integer> channelId) {
			options.channelId = channelId;
			return this;
		}

		public Builder withDiarizationEnabled(Boolean diarizationEnabled) {
			options.diarizationEnabled = diarizationEnabled;
			return this;
		}

		public Builder withSpeakerCount(Integer speakerCount) {
			options.speakerCount = speakerCount;
			return this;
		}

		public Builder withDisfluencyRemovalEnabled(Boolean disfluencyRemovalEnabled) {
			options.disfluencyRemovalEnabled = disfluencyRemovalEnabled;
			return this;
		}

		public Builder withTimestampAlignmentEnabled(Boolean timestampAlignmentEnabled) {
			options.timestampAlignmentEnabled = timestampAlignmentEnabled;
			return this;
		}

		public Builder withSpecialWordFilter(String specialWordFilter) {
			options.specialWordFilter = specialWordFilter;
			return this;
		}

		public Builder withAudioEventDetectionEnabled(
				Boolean audioEventDetectionEnabled) {
			options.audioEventDetectionEnabled = audioEventDetectionEnabled;
			return this;
		}

		public TongYiAudioTranscriptionOptions build() {
			// Perform any necessary validation here before returning the built object
			return options;
		}
	}

}
