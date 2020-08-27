

package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * User DTO
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDto extends User implements Converter<User, UserDto> {

    private Pages page;

    @Override
    public void convertToDo(User user) {
        BeanUtils.copyProperties(this, user);
    }

    @Override
    public UserDto convert(User user) {
        BeanUtils.copyProperties(user, this);
        return this;
    }
}