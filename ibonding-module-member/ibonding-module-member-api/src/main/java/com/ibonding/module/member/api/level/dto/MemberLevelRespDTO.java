package com.ibonding.module.member.api.level.dto;

import com.ibonding.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * 会员等级 Resp DTO
 *
 * @author Agaru
 */
@Data
public class MemberLevelRespDTO {

    /**
     * 编号
     */
    private Long id;
    /**
     * 等级名称
     */
    private String name;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 升级经验
     */
    private Integer experience;
    /**
     * 享受折扣
     */
    private Integer discountPercent;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
