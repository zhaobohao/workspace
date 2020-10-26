package org.springclouddev.system.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.system.entityst;
import org.springclouddev.system.service.IDictService;
import org.springclouddev.system.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

    private static IDictService dictService;

    static {
        dictService = SpringUtil.getBean(IDictService.class);
    }

    public static PostWrapper build() {
        return new PostWrapper();
    }

    @Override
    public PostVO entityVO(Post post) {
        PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
        String categoryName = dictService.getValue("post_category",String.valueOf( post.getCategory()));
        postVO.setCategoryName(categoryName);
        return postVO;
    }

}