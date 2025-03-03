package com.alibaba.cloud.ai.tongyi.image;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.alibaba.cloud.ai.tongyi.common.constants.TongYiConstants.SCA_AI_CONFIGURATION;

/**
 * TongYi Image API properties.
 *
 * @author Agaru
 */

@ConfigurationProperties(TongYiImagesProperties.CONFIG_PREFIX)
public class TongYiImagesProperties {

	/**
	 * Spring Cloud Alibaba AI configuration prefix.
	 */
	public static final String CONFIG_PREFIX = SCA_AI_CONFIGURATION + "images";

	/**
	 * Default TongYi Chat model.
	 */
	public static final String DEFAULT_IMAGES_MODEL_NAME = ImageSynthesis.Models.WANX_V1;

	/**
	 * Enable TongYiQWEN ai images client.
	 */
	private boolean enabled = true;

	@NestedConfigurationProperty
	private TongYiImagesOptions options = TongYiImagesOptions.builder()
			.withModel(DEFAULT_IMAGES_MODEL_NAME)
			.withN(1)
			.build();

	public TongYiImagesOptions getOptions() {

		return this.options;
	}

	public void setOptions(TongYiImagesOptions options) {

		this.options = options;
	}

	public boolean isEnabled() {

		return this.enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

}
