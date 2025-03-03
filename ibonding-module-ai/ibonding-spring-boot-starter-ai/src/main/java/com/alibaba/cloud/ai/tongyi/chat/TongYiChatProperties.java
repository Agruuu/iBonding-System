package com.alibaba.cloud.ai.tongyi.chat;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * @author Agaru
 */

@ConfigurationProperties(TongYiChatProperties.CONFIG_PREFIX)
public class TongYiChatProperties {

	/**
	 * Spring Cloud Alibaba AI configuration prefix.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "chat";

	/**
	 * Default TongYi Chat model.
	 */
	public static final String DEFAULT_DEPLOYMENT_NAME = Generation.Models.QWEN_TURBO;

	/**
	 * Default temperature speed.
	 */
	private static final Double DEFAULT_TEMPERATURE = 0.8;

	/**
	 * Enable TongYiQWEN ai chat client.
	 */
	private boolean enabled = true;

	@NestedConfigurationProperty
	private TongYiChatOptions options = TongYiChatOptions.builder()
			.withModel(DEFAULT_DEPLOYMENT_NAME)
			.withTemperature(DEFAULT_TEMPERATURE)
			.withEnableSearch(true)
			.withResultFormat(GenerationParam.ResultFormat.MESSAGE)
			.build();

	public TongYiChatOptions getOptions() {

		return this.options;
	}

	public void setOptions(TongYiChatOptions options) {

		this.options = options;
	}

	public boolean isEnabled() {

		return this.enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

}
