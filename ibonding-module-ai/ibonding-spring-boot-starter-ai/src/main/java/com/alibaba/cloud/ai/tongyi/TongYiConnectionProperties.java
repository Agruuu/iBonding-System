package com.alibaba.cloud.ai.tongyi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * Spring Cloud Alibaba AI TongYi LLM connection properties.
 *
 * @author Agaru
 */

@ConfigurationProperties(TongYiConnectionProperties.CONFIG_PREFIX)
public class TongYiConnectionProperties {

	/**
	 * Spring Cloud Alibaba AI connection configuration Prefix.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "tongyi";

	/**
	 * TongYi LLM API key.
	 */
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
