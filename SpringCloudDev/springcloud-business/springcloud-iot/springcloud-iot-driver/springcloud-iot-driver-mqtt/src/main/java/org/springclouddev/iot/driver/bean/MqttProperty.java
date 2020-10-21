package org.springclouddev.iot.driver.bean;

import lombok.Getter;
import lombok.Setter;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Setter
@Getter
@Validated({Insert.class, Update.class})
@ConfigurationProperties(prefix = "driver.mqtt")
public class MqttProperty {
    @NotBlank(message = "username can't be empty")
    private String username;

    @NotBlank(message = "password can't be empty")
    private String password;

    @NotBlank(message = "url can't be empty")
    private String url;

    @Size(min = 1, message = "at least one qos")
    private List<Integer> qos;

    @NotNull(message = "keep alive interval can't be empty")
    private Integer keepAlive;

    @NotNull(message = "completion timeout can't be empty")
    private Integer completionTimeout;

    @Size(min = 1, message = "at least one topic")
    private List<String> topics;

}
