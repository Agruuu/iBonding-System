package com.ibonding.module.system.api.dept;

import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.system.api.dept.dto.PostRespDTO;
import com.ibonding.module.system.dal.dataobject.dept.PostDO;
import com.ibonding.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author Agaru
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
