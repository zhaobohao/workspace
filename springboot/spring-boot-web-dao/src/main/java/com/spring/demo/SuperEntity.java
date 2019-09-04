package com.spring.demo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 实体父类
 *
 */
@SuppressWarnings("rawtypes")
//@KeySequence("SEQ_USER")  //如果是oracle 这样的sequence
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class SuperEntity implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -1607304369302044242L;
    /**
     * 主键ID
     */
    //@TableId(value = "id", type = IdType.INPUT)//如果是oracle 这样的sequence
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    public Long id;



}
