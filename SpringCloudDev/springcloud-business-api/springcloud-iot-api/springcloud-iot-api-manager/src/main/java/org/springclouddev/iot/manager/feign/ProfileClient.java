package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.dto.ProfileDto;
import org.springclouddev.iot.manager.entity.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * <p>模板 FeignClient
 */
@FeignClient(path = Common.Service.DC3_MANAGER_PROFILE_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = ProfileClientFallback.class)
public interface ProfileClient {

    /**
     * 新增 Profile
     *
     * @param profile Profile
     * @return Profile
     */
    @PostMapping("/add")
    R<Profile> add(@Validated(Insert.class) @RequestBody Profile profile);

    /**
     * 根据 ID 删除 Profile
     *
     * @param id profile Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Profile
     *
     * @param profile Profile
     * @return Profile
     */
    @PostMapping("/update")
    R<Profile> update(@Validated(Update.class) @RequestBody Profile profile);

    /**
     * 根据 ID 查询 Profile
     *
     * @param id Profile Id
     * @return Profile
     */
    @GetMapping("/id/{id}")
    R<Profile> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 Profile
     *
     * @param profileDto Profile Dto
     * @return Page<Profile>
     */
    @PostMapping("/list")
    R<Page<Profile>> list(@RequestBody(required = false) ProfileDto profileDto);

}
