package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Group;
import org.springframework.beans.BeanUtils;

/**
 * Group DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupDto extends Group implements Converter<Group, GroupDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Group group) {
        BeanUtils.copyProperties(this, group);
    }

    @Override
    public GroupDto convert(Group group) {
        BeanUtils.copyProperties(group, this);
        return this;
    }
}