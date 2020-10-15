package com.dc3.common.model;

import com.dc3.common.valid.Auth;
import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * User
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class, Auth.class})
    @Pattern(regexp = "^[a-zA-Z]\\w{2,15}$", message = "invalid name , /^[a-zA-Z]\\w{2,15}$/", groups = {Insert.class})
    private String name;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @NotBlank(message = "password can't be empty", groups = {Insert.class, Auth.class})
    @Pattern(regexp = "^[a-zA-Z]\\w{7,15}$", message = "invalid password , /^[a-zA-Z]\\w{7,15}$/", groups = {Insert.class, Update.class})
    private String password;

    private Boolean enable;
}
