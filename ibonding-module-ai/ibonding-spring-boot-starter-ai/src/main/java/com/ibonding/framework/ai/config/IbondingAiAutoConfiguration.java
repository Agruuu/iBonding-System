package com.ibonding.framework.ai.config;

import com.ibonding.framework.ai.core.factory.AiModelFactory;
import com.ibonding.framework.ai.core.factory.AiModelFactoryImpl;
import com.ibonding.framework.ai.core.model.deepseek.DeepSeekChatModel;
import com.ibonding.framework.ai.core.model.deepseek.DeepSeekChatOptions;
import com.ibonding.framework.ai.core.model.midjourney.api.MidjourneyApi;
import com.ibonding.framework.ai.core.model.suno.api.SunoApi;
import com.ibonding.framework.ai.core.model.xinghuo.XingHuoChatModel;
import com.ibonding.framework.ai.core.model.xinghuo.XingHuoChatOptions;
import com.alibaba.cloud.ai.tongyi.TongYiAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

/**
 * Agaru AI 自动配置
 *
 * @author Agaru
 */
@AutoConfiguration
@EnableConfigurationProperties(IbondingAiProperties.class)
@Slf4j
@Import(TongYiAutoConfiguration.class)
public class IbondingAiAutoConfiguration {

    @Bean
    public AiModelFactory aiModelFactory() {
        return new AiModelFactoryImpl();
    }


    // ========== 各种 AI Client 创建 ==========

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.deepseek.enable", havingValue = "true")
    public DeepSeekChatModel deepSeekChatModel(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.DeepSeekProperties properties = ibondingAiProperties.getDeepSeek();
        DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                .model(properties.getModel())
                .temperature(properties.getTemperature())
                .maxTokens(properties.getMaxTokens())
                .topP(properties.getTopP())
                .build();
        return new DeepSeekChatModel(properties.getApiKey(), options);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.xinghuo.enable", havingValue = "true")
    public XingHuoChatModel xingHuoChatClient(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.XingHuoProperties properties = ibondingAiProperties.getXinghuo();
        XingHuoChatOptions options = XingHuoChatOptions.builder()
                .model(properties.getModel())
                .temperature(properties.getTemperature())
                .maxTokens(properties.getMaxTokens())
                .topK(properties.getTopK())
                .build();
        return new XingHuoChatModel(properties.getAppKey(), properties.getSecretKey(), options);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.midjourney.enable", havingValue = "true")
    public MidjourneyApi midjourneyApi(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.MidjourneyProperties config = ibondingAiProperties.getMidjourney();
        return new MidjourneyApi(config.getBaseUrl(), config.getApiKey(), config.getNotifyUrl());
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.suno.enable", havingValue = "true")
    public SunoApi sunoApi(IbondingAiProperties ibondingAiProperties) {
        return new SunoApi(ibondingAiProperties.getSuno().getBaseUrl());
    }

    // ========== rag 相关 ==========
    // TODO @xin 免费版本
//    @Bean
//    @Lazy // TODO Agaru：临时注释，避免无法启动」
//    public TransformersEmbeddingModel transformersEmbeddingClient() {
//        return new TransformersEmbeddingModel(MetadataMode.EMBED);
//    }

    /**
     * TODO @xin 默认版本先不弄，目前都先取对应的 EmbeddingModel
     */
//    @Bean
//    @Lazy // TODO Agaru：临时注释，避免无法启动
//    public RedisVectorStore vectorStore(TransformersEmbeddingModel embeddingModel, RedisVectorStoreProperties properties,
//                                        RedisProperties redisProperties) {
//        var config = RedisVectorStore.RedisVectorStoreConfig.builder()
//                .withIndexName(properties.getIndex())
//                .withPrefix(properties.getPrefix())
//                .withMetadataFields(new RedisVectorStore.MetadataField("knowledgeId", Schema.FieldType.NUMERIC))
//                .build();
//
//        RedisVectorStore redisVectorStore = new RedisVectorStore(config, embeddingModel,
//                new JedisPooled(redisProperties.getHost(), redisProperties.getPort()),
//                properties.isInitializeSchema());
//        redisVectorStore.afterPropertiesSet();
//        return redisVectorStore;
//    }
    @Bean
    @Lazy // TODO Agaru：临时注释，避免无法启动
    public TokenTextSplitter tokenTextSplitter() {
        //TODO  @xin 配置提取
        return new TokenTextSplitter(500, 100, 5, 10000, true);
    }

    @Bean
    @Lazy // TODO Agaru：临时注释，避免无法启动
    public TokenCountEstimator tokenCountEstimator() {
        return new JTokkitTokenCountEstimator();
    }

}