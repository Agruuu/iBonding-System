package com.alibaba.cloud.ai.tongyi.audio.speech;

import com.alibaba.cloud.ai.tongyi.audio.AudioSpeechModels;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisAudioFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * TongYi audio speech configuration properties.
 *
 * @author Agaru
 */

@ConfigurationProperties(TongYiAudioSpeechProperties.CONFIG_PREFIX)
public class TongYiAudioSpeechProperties {

	/**
	 * Spring Cloud Alibaba AI configuration prefix.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "audio.speech";
	/**
	 * Default TongYi Chat model.
	 */
	public static final String DEFAULT_AUDIO_MODEL_NAME = AudioSpeechModels.SAMBERT_ZHICHU_V1;

	/**
	 * Enable TongYiQWEN ai audio client.
	 */
	private boolean enabled = true;

	@NestedConfigurationProperty
	private TongYiAudioSpeechOptions options = TongYiAudioSpeechOptions.builder()
			.withModel(DEFAULT_AUDIO_MODEL_NAME)
			.withFormat(SpeechSynthesisAudioFormat.WAV)
			.build();

	public TongYiAudioSpeechOptions getOptions() {

		return this.options;
	}

	public void setOptions(TongYiAudioSpeechOptions options) {

		this.options = options;
	}

	public boolean isEnabled() {

		return this.enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

}
