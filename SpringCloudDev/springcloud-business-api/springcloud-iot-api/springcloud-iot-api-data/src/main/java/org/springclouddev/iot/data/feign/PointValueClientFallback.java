package org.springclouddev.iot.data.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.data.dto.PointValueDto;
import org.springclouddev.iot.data.entityintValue;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PointValueClientFallback implements PointValueClient {

    @Override
    public R<String> status(@NotNull Long deviceId) {
        return R.fail("获取设备状态失败");
    }

    @Override
    public R<List<PointValue>> realtime(@NotNull Long deviceId) {
        return  R.fail("获取实时数据失败");
    }

    @Override
    public R<PointValue> realtime(@NotNull Long deviceId, @NotNull Long pointId) {
        return  R.fail("获取实时数据失败");
    }

    @Override
    public R<PointValue> latest(@NotNull Long deviceId) {
        return  R.fail("获取最新数据失败");
    }

    @Override
    public R<PointValue> latest(@NotNull Long deviceId, @NotNull Long pointId) {
        return  R.fail("获取最新数据失败");
    }

    @Override
    public R<IPage<PointValue>> list(PointValueDto pointValueDto) {
        return  R.fail("分页查询任务失败");
    }
}
