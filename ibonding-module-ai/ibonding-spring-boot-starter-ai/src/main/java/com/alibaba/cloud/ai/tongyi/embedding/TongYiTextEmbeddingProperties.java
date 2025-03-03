package com.alibaba.cloud.ai.tongyi.embedding;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * @author Agaru
 */

@ConfigurationProperties(TongYiTextEmbeddingProperties.CONFIG_PREFIX)
public class TongYiTextEmbeddingProperties {

	/**
	 * Prefix of TongYi Text Embedding properties.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "embedding";

	private boolean enabled = true;

	public boolean isEnabled() {

		return this.enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

}
