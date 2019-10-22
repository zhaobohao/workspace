package com.spring.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.spring.web.SuperEntity;
import com.spring.web.entity.enums.AgeEnum;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private AgeEnum age;

    /**
     * 邮箱
     */
    private String email;

    @TableField(exist = false)
    private Integer count;

    public User setId(Long id)
    {
        this.id=id;
        return this;
    }

    /**
     * fill 属性是配合MyMetaObjectHandler 这个元数据注入使用的，确实方便很多
     */
    @TableField(exist = false,fill = FieldFill.INSERT_UPDATE)
    private String operator;

    /**
     * 使用下面这个注解来匹配逻辑删除
     */
   // @TableLogic
   // private Integer isDelete;
}
