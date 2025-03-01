package com.ibonding.module.ai.service.knowledge;

import cn.hutool.core.util.ObjUtil;
import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeCreateReqVO;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeUpdateReqVO;
import com.ibonding.module.ai.dal.dataobject.knowledge.AiKnowledgeDO;
import com.ibonding.module.ai.dal.dataobject.model.AiChatModelDO;
import com.ibonding.module.ai.dal.mysql.knowledge.AiKnowledgeMapper;
import com.ibonding.module.ai.service.model.AiApiKeyService;
import com.ibonding.module.ai.service.model.AiChatModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import static com.ibonding.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ibonding.module.ai.enums.ErrorCodeConstants.KNOWLEDGE_NOT_EXISTS;

/**
 * AI 知识库-基础信息 Service 实现类
 *
 * @author xiaoxin
 */
@Service
@Slf4j
public class AiKnowledgeServiceImpl implements AiKnowledgeService {

    @Resource
    private AiKnowledgeMapper knowledgeMapper;

    @Resource
    private AiChatModelService chatModelService;
    @Resource
    private AiApiKeyService apiKeyService;

    @Override
    public Long createKnowledge(AiKnowledgeCreateReqVO createReqVO, Long userId) {
        // 1. 校验模型配置
        AiChatModelDO model = chatModelService.validateChatModel(createReqVO.getModelId());

        // 2. 插入知识库
        AiKnowledgeDO knowledgeBase = BeanUtils.toBean(createReqVO, AiKnowledgeDO.class)
                .setModel(model.getModel()).setUserId(userId).setStatus(CommonStatusEnum.ENABLE.getStatus());
        knowledgeMapper.insert(knowledgeBase);
        return knowledgeBase.getId();
    }

    @Override
    public void updateKnowledge(AiKnowledgeUpdateReqVO updateReqVO, Long userId) {
        // 1.1 校验知识库存在
        AiKnowledgeDO knowledgeBaseDO = validateKnowledgeExists(updateReqVO.getId());
        if (ObjUtil.notEqual(knowledgeBaseDO.getUserId(), userId)) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
        // 1.2 校验模型配置
        AiChatModelDO model = chatModelService.validateChatModel(updateReqVO.getModelId());

        // 2. 更新知识库
        AiKnowledgeDO updateDO = BeanUtils.toBean(updateReqVO, AiKnowledgeDO.class);
        updateDO.setModel(model.getModel());
        knowledgeMapper.updateById(updateDO);
    }

    @Override
    public AiKnowledgeDO validateKnowledgeExists(Long id) {
        AiKnowledgeDO knowledgeBase = knowledgeMapper.selectById(id);
        if (knowledgeBase == null) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
        return knowledgeBase;
    }

    @Override
    public PageResult<AiKnowledgeDO> getKnowledgePage(Long userId, AiKnowledgePageReqVO pageReqVO) {
        return knowledgeMapper.selectPage(userId, pageReqVO);
    }

    @Override
    public VectorStore getVectorStoreById(Long id) {
        AiKnowledgeDO knowledge = validateKnowledgeExists(id);
        AiChatModelDO model = chatModelService.validateChatModel(knowledge.getModelId());
        // 创建或获取 VectorStore 对象
        return apiKeyService.getOrCreateVectorStore(model.getKeyId());
    }

}
