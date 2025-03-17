package com.ibonding.framework.ai.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ibonding.framework.ai.core.factory.AiModelFactory;
import com.ibonding.framework.ai.core.factory.AiModelFactoryImpl;
import com.ibonding.framework.ai.core.model.deepseek.DeepSeekChatModel;
import com.ibonding.framework.ai.core.model.doubao.DouBaoChatModel;
import com.ibonding.framework.ai.core.model.hunyuan.HunYuanChatModel;
import com.ibonding.framework.ai.core.model.midjourney.api.MidjourneyApi;
import com.ibonding.framework.ai.core.model.siliconflow.SiliconFlowChatModel;
import com.ibonding.framework.ai.core.model.suno.api.SunoApi;
import com.ibonding.framework.ai.core.model.xinghuo.XingHuoChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.autoconfigure.vectorstore.milvus.MilvusServiceClientProperties;
import org.springframework.ai.autoconfigure.vectorstore.milvus.MilvusVectorStoreProperties;
import org.springframework.ai.autoconfigure.vectorstore.qdrant.QdrantVectorStoreProperties;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreProperties;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * AI 自动配置
 *
 * @author Agaru
 */
@AutoConfiguration
@EnableConfigurationProperties({ IbondingAiProperties.class,
        QdrantVectorStoreProperties.class, // 解析 Qdrant 配置
        RedisVectorStoreProperties.class, // 解析 Redis 配置
        MilvusVectorStoreProperties.class, MilvusServiceClientProperties.class // 解析 Milvus 配置
})
@Slf4j
public class IbondingAiAutoConfiguration {

    @Bean
    public AiModelFactory aiModelFactory() {
        return new AiModelFactoryImpl();
    }

    // ========== 各种 AI Client 创建 ==========

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.deepseek.enable", havingValue = "true")
    public DeepSeekChatModel deepSeekChatModel(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.DeepSeekProperties properties = ibondingAiProperties.getDeepseek();
        return buildDeepSeekChatModel(properties);
    }

    public DeepSeekChatModel buildDeepSeekChatModel(IbondingAiProperties.DeepSeekProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(DeepSeekChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(DeepSeekChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new DeepSeekChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.doubao.enable", havingValue = "true")
    public DouBaoChatModel douBaoChatClient(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.DouBaoProperties properties = ibondingAiProperties.getDoubao();
        return buildDouBaoChatClient(properties);
    }

    public DouBaoChatModel buildDouBaoChatClient(IbondingAiProperties.DouBaoProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(DouBaoChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(DouBaoChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new DouBaoChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.siliconflow.enable", havingValue = "true")
    public SiliconFlowChatModel siliconFlowChatClient(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.SiliconFlowProperties properties = ibondingAiProperties.getSiliconflow();
        return buildSiliconFlowChatClient(properties);
    }

    public SiliconFlowChatModel buildSiliconFlowChatClient(IbondingAiProperties.SiliconFlowProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(SiliconFlowChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(SiliconFlowChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new SiliconFlowChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.hunyuan.enable", havingValue = "true")
    public HunYuanChatModel hunYuanChatClient(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.HunYuanProperties properties = ibondingAiProperties.getHunyuan();
        return buildHunYuanChatClient(properties);
    }

    public HunYuanChatModel buildHunYuanChatClient(IbondingAiProperties.HunYuanProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(HunYuanChatModel.MODEL_DEFAULT);
        }
        // 特殊：由于混元大模型不提供 deepseek，而是通过知识引擎，所以需要区分下 URL
        if (StrUtil.isEmpty(properties.getBaseUrl())) {
            properties.setBaseUrl(
                    StrUtil.startWithIgnoreCase(properties.getModel(), "deepseek") ? HunYuanChatModel.DEEP_SEEK_BASE_URL
                            : HunYuanChatModel.BASE_URL);
        }
        // 创建 OpenAiChatModel、HunYuanChatModel 对象
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(properties.getBaseUrl())
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new HunYuanChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "ibonding.ai.xinghuo.enable", havingValue = "true")
    public XingHuoChatModel xingHuoChatClient(IbondingAiProperties ibondingAiProperties) {
        IbondingAiProperties.XingHuoProperties properties = ibondingAiProperties.getXinghuo();
        return buildXingHuoChatClient(properties);
    }

    public XingHuoChatModel buildXingHuoChatClient(IbondingAiProperties.XingHuoProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(XingHuoChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(XingHuoChatModel.BASE_URL)
                        .apiKey(properties.getAppKey() + ":" + properties.getSecretKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new XingHuoChatModel(openAiChatModel);
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

    // ========== RAG 相关 ==========

    @Bean
    public TokenCountEstimator tokenCountEstimator() {
        return new JTokkitTokenCountEstimator();
    }

    @Bean
    public BatchingStrategy batchingStrategy() {
        return new TokenCountBatchingStrategy();
    }

    private static ToolCallingManager getToolCallingManager() {
        return SpringUtil.getBean(ToolCallingManager.class);
    }

}