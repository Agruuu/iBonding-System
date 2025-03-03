package com.alibaba.cloud.ai.tongyi.metadata;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.GenerationUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.util.Assert;

/**
 * {@link Usage} implementation for {@literal Alibaba DashScope}.
 *
 * @author Agaru
 */

public class TongYiAiUsage implements Usage {

	private final GenerationUsage usage;

	public TongYiAiUsage(GenerationUsage usage) {

		Assert.notNull(usage, "GenerationUsage must not be null");
		this.usage = usage;
	}

	public static TongYiAiUsage from(GenerationResult chatCompletions) {

		Assert.notNull(chatCompletions, "ChatCompletions must not be null");
		return from(chatCompletions.getUsage());
	}

	public static TongYiAiUsage from(GenerationUsage usage) {

		return new TongYiAiUsage(usage);
	}

	protected GenerationUsage getUsage() {

		return this.usage;
	}

	@Override
	public Long getPromptTokens() {

		throw new UnsupportedOperationException("Unimplemented method 'getPromptTokens'");
	}

	@Override
	public Long getGenerationTokens() {

		return this.getUsage().getOutputTokens().longValue();
	}

	@Override
	public Long getTotalTokens() {

		return this.getUsage().getTotalTokens().longValue();
	}

	@Override
	public String toString() {

		return this.getUsage().toString();
	}
}
