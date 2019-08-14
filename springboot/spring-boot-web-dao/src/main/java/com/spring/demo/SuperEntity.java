package com.spring.demo;


import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId("id")
    //@TableId(value = "id", type = IdType.INPUT)//如果是oracle 这样的sequence
    protected Long id;



}
