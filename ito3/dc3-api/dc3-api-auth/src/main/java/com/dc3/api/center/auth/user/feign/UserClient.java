

package com.dc3.api.center.auth.user.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.auth.user.hystrix.UserClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.UserDto;
import com.dc3.common.model.User;
import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * 用户 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_AUTH_USER_URL_PREFIX, name = Common.Service.DC3_AUTH_SERVICE_NAME, fallbackFactory = UserClientHystrix.class)
public interface UserClient {

    /**
     * 新增 User
     *
     * @param user User
     * @return User
     */
    @PostMapping("/add")
    R<User> add(@Validated(Insert.class) @RequestBody User user);

    /**
     * 根据 ID 删除 User
     *
     * @param id User Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 User
     * <p>
     * 支  持: Enable,Password
     * 不支持: Name
     *
     * @param user User
     * @return User
     */
    @PostMapping("/update")
    R<User> update(@Validated(Update.class) @RequestBody User user);

    /**
     * 根据 ID 重置 User 密码
     *
     * @param id User Id
     * @return Boolean
     */
    @PostMapping("/rest/{id}")
    R<Boolean> restPassword(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 ID 查询 User
     *
     * @param id User Id
     * @return User
     */
    @GetMapping("/id/{id}")
    R<User> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 Name 查询 User
     *
     * @param name User Name
     * @return User
     */
    @GetMapping("/name/{name}")
    R<User> selectByName(@NotNull @PathVariable(value = "name") String name);

    /**
     * 分页查询 User
     *
     * @param userDto Dto
     * @return Page<User>
     */
    @PostMapping("/list")
    R<Page<User>> list(@RequestBody(required = false) UserDto userDto);

    /**
     * 检测用户是否存在
     *
     * @param name User Name
     * @return Boolean
     */
    @GetMapping("/check/{name}")
    R<Boolean> checkUserValid(@NotNull @PathVariable(value = "name") String name);

}
