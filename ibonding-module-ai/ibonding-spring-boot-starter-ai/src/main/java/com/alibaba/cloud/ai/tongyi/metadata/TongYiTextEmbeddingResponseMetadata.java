package com.alibaba.cloud.ai.tongyi.metadata;

import com.alibaba.dashscope.embeddings.TextEmbeddingUsage;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

/**
 * @author Agaru
 */

public class TongYiTextEmbeddingResponseMetadata extends EmbeddingResponseMetadata {

	private Integer totalTokens;

	protected TongYiTextEmbeddingResponseMetadata(Integer totalTokens) {

		this.totalTokens = totalTokens;
	}

	public static TongYiTextEmbeddingResponseMetadata from(TextEmbeddingUsage usage) {

		return new TongYiTextEmbeddingResponseMetadata(usage.getTotalTokens());
	}

	public Integer getTotalTokens() {

		return totalTokens;
	}

	public void setTotalTokens(Integer totalTokens) {

		this.totalTokens = totalTokens;
	}

}
