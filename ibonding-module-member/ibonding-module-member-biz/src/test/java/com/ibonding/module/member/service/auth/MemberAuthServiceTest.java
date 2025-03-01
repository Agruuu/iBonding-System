package com.ibonding.module.member.service.auth;

import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.util.collection.ArrayUtils;
import com.ibonding.framework.redis.config.IbondingRedisAutoConfiguration;
import com.ibonding.framework.test.core.ut.BaseDbAndRedisUnitTest;
import com.ibonding.module.member.dal.dataobject.user.MemberUserDO;
import com.ibonding.module.member.dal.mysql.user.MemberUserMapper;
import com.ibonding.module.member.service.user.MemberUserService;
import com.ibonding.module.system.api.logger.LoginLogApi;
import com.ibonding.module.system.api.oauth2.OAuth2TokenApi;
import com.ibonding.module.system.api.sms.SmsCodeApi;
import com.ibonding.module.system.api.social.SocialUserApi;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.Resource;
import java.util.function.Consumer;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.ibonding.framework.test.core.util.RandomUtils.randomPojo;
import static com.ibonding.framework.test.core.util.RandomUtils.randomString;

// TODO @Agaru：单测的 review，等逻辑都达成一致后
/**
 * {@link MemberAuthService} 的单元测试类
 *
 * @author 宋天
 */
@Import({MemberAuthServiceImpl.class, IbondingRedisAutoConfiguration.class})
public class MemberAuthServiceTest extends BaseDbAndRedisUnitTest {

    // TODO @Agaru：登录相关的单测，待补全

    @Resource
    private MemberAuthServiceImpl authService;

    @MockBean
    private MemberUserService userService;
    @MockBean
    private SmsCodeApi smsCodeApi;
    @MockBean
    private LoginLogApi loginLogApi;
    @MockBean
    private OAuth2TokenApi oauth2TokenApi;
    @MockBean
    private SocialUserApi socialUserApi;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Resource
    private MemberUserMapper memberUserMapper;

    // TODO Agaru：后续重构这个单测
//    @Test
//    public void testUpdatePassword_success(){
//        // 准备参数
//        MemberUserDO userDO = randomUserDO();
//        memberUserMapper.insert(userDO);
//
//        // 新密码
//        String newPassword = randomString();
//
//        // 请求实体
//        AppMemberUserUpdatePasswordReqVO reqVO = AppMemberUserUpdatePasswordReqVO.builder()
//                .oldPassword(userDO.getPassword())
//                .password(newPassword)
//                .build();
//
//        // 测试桩
//        // 这两个相等是为了返回ture这个结果
//        when(passwordEncoder.matches(reqVO.getOldPassword(),reqVO.getOldPassword())).thenReturn(true);
//        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
//
//        // 更新用户密码
//        authService.updatePassword(userDO.getId(), reqVO);
//        assertEquals(memberUserMapper.selectById(userDO.getId()).getPassword(),newPassword);
//    }

    // TODO Agaru：后续重构这个单测
//    @Test
//    public void testResetPassword_success(){
//        // 准备参数
//        MemberUserDO userDO = randomUserDO();
//        memberUserMapper.insert(userDO);
//
//        // 随机密码
//        String password = randomNumbers(11);
//        // 随机验证码
//        String code = randomNumbers(4);
//
//        // mock
//        when(passwordEncoder.encode(password)).thenReturn(password);
//
//        // 更新用户密码
//        AppMemberUserResetPasswordReqVO reqVO = new AppMemberUserResetPasswordReqVO();
//        reqVO.setMobile(userDO.getMobile());
//        reqVO.setPassword(password);
//        reqVO.setCode(code);
//
//        authService.resetPassword(reqVO);
//        assertEquals(memberUserMapper.selectById(userDO.getId()).getPassword(),password);
//    }

    // ========== 随机对象 ==========

    @SafeVarargs
    private static MemberUserDO randomUserDO(Consumer<MemberUserDO>... consumers) {
        Consumer<MemberUserDO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
            o.setPassword(randomString());
        };
        return randomPojo(MemberUserDO.class, ArrayUtils.append(consumer, consumers));
    }


}
