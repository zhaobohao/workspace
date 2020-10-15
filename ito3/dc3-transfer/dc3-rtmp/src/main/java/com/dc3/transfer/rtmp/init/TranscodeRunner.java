package com.dc3.transfer.rtmp.init;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.common.bean.Pages;
import com.dc3.common.dto.RtmpDto;
import com.dc3.common.model.Rtmp;
import com.dc3.transfer.rtmp.service.RtmpService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.getProperty;

/**
 * 启动服务，自动加载自启任务
 *

 */
@Slf4j
@Setter
@Order(10)
@Component
public class TranscodeRunner implements ApplicationRunner {
    public static String ffmpeg;

    @Value("${rtmp.ffmpeg.window}")
    private String window;
    @Value("${rtmp.ffmpeg.unix}")
    private String unix;

    @Resource
    private RtmpService rtmpService;

    @Override
    public void run(ApplicationArguments args) {
        ffmpeg = getProperty("os.name").toLowerCase().startsWith("win") ? window : unix;
        if (StringUtils.isBlank(ffmpeg)) {
            log.error("FFmpeg path is null,Please fill absolute path!");
            System.exit(1);
        }
        if (!FileUtil.isFile(ffmpeg)) {
            log.error("{} does not exist,Please fill absolute path!", ffmpeg);
            System.exit(1);
        }
        list().forEach(rtmp -> rtmpService.start(rtmp.getId()));
    }

    public List<Rtmp> list() {
        Page<Rtmp> page = rtmpService.list(new RtmpDto(true).setPage(new Pages().setSize(-1L)));
        return Optional.ofNullable(page.getRecords()).orElse(new ArrayList<>());
    }

}
