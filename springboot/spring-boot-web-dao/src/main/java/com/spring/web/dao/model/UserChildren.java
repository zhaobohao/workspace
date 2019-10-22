package com.spring.web.dao.model;


import com.spring.web.dao.entity.Children;
import com.spring.web.dao.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author miemie
 * @since 2019-06-12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserChildren extends User {

    private List<Children> c;
}
