package com.alibaba.cloud.ai.tongyi.metadata;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisTaskMetrics;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisUsage;
import org.springframework.ai.image.ImageResponseMetadata;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Agaru
 */

public class TongYiImagesResponseMetadata  extends HashMap<String, Object> implements ImageResponseMetadata {

	private final Long created;

	private String taskId;

	private ImageSynthesisTaskMetrics metrics;

	private ImageSynthesisUsage usage;

	public static TongYiImagesResponseMetadata from(ImageSynthesisResult synthesisResult) {

		Assert.notNull(synthesisResult, "TongYiAiImageResponse must not be null");

		return new TongYiImagesResponseMetadata(
				System.currentTimeMillis(),
				synthesisResult.getOutput().getTaskMetrics(),
				synthesisResult.getOutput().getTaskId(),
				synthesisResult.getUsage()
		);
	}

	protected TongYiImagesResponseMetadata(
			Long created,
			ImageSynthesisTaskMetrics metrics,
			String taskId,
			ImageSynthesisUsage usage
	) {

		this.taskId = taskId;
		this.metrics = metrics;
		this.created = created;
		this.usage = usage;
	}

	public ImageSynthesisUsage getUsage() {
		return usage;
	}

	public void setUsage(ImageSynthesisUsage usage) {
		this.usage = usage;
	}

	@Override
	public Long getCreated() {
		return created;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ImageSynthesisTaskMetrics getMetrics() {
		return metrics;
	}

	void setMetrics(ImageSynthesisTaskMetrics metrics) {
		this.metrics = metrics;
	}


	public Long created() {
		return this.created;
	}

	@Override
	public String toString() {
		return "TongYiImagesResponseMetadata {" + "created=" + created + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {

			return true;
		}
		if (o == null || getClass() != o.getClass()) {

			return false;
		}

		TongYiImagesResponseMetadata that = (TongYiImagesResponseMetadata) o;

		return Objects.equals(created, that.created)
				&& Objects.equals(taskId, that.taskId)
				&& Objects.equals(metrics, that.metrics);
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, taskId, metrics);
	}

}
