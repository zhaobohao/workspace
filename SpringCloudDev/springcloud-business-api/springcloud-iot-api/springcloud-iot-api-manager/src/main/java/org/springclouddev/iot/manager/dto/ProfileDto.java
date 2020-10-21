package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Profile;
import org.springframework.beans.BeanUtils;

/**
 * Profile DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends Profile implements Converter<Profile, ProfileDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Profile profile) {
        BeanUtils.copyProperties(this, profile);
    }

    @Override
    public ProfileDto convert(Profile profile) {
        BeanUtils.copyProperties(profile, this);
        return this;
    }
}