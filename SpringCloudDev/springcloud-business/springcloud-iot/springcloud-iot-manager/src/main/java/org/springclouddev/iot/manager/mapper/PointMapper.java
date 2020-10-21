package org.springclouddev.iot.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.iot.manager.entity.Point;

/**
 * Mapper
 */
@Mapper
public interface PointMapper extends BaseMapper<Point> {
}
