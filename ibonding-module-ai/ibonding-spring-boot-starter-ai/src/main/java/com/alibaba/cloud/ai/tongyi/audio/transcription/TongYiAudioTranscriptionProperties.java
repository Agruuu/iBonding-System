package com.alibaba.cloud.ai.tongyi.audio.transcription;

import com.alibaba.cloud.ai.tongyi.audio.AudioTranscriptionModels;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * @author Agaru
 */

@ConfigurationProperties(TongYiAudioTranscriptionProperties.CONFIG_PREFIX)
public class TongYiAudioTranscriptionProperties {

	/**
	 * Spring Cloud Alibaba AI configuration prefix.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "audio.transcription";

	/**
	 * Default TongYi Chat model.
	 */
	public static final String DEFAULT_AUDIO_MODEL_NAME = AudioTranscriptionModels.Paraformer_V1;

	/**
	 * Enable TongYiQWEN ai audio client.
	 */
	private boolean enabled = true;

	@NestedConfigurationProperty
	private TongYiAudioTranscriptionOptions options = TongYiAudioTranscriptionOptions
			.builder().withModel(DEFAULT_AUDIO_MODEL_NAME).build();

	public TongYiAudioTranscriptionOptions getOptions() {

		return this.options;
	}

	public void setOptions(TongYiAudioTranscriptionOptions options) {

		this.options = options;
	}

	public boolean isEnabled() {

		return this.enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}
}
