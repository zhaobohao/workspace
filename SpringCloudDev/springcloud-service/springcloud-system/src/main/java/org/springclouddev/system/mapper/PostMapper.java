package org.springclouddev.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.system.entityst;
import org.springclouddev.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 Mapper 接口
 *
 * @author zhaobohao
 */
public interface PostMapper extends SuperMapper<Post> {

    /**
     * 自定义分页
     *
     * @param page
     * @param post
     * @return
     */
    List<PostVO> selectPostPage(IPage page, PostVO post);

    /**
     * 获取岗位名
     *
     * @param ids
     * @return
     */
    List<String> getPostNames(Long[] ids);

}
