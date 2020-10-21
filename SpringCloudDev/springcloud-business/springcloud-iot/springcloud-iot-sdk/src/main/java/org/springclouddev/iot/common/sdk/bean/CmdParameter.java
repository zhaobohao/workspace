package org.springclouddev.iot.common.sdk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.valid.Read;
import org.springclouddev.iot.common.valid.Write;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CmdParameter {
    @NotNull(message = "device id can't be empty", groups = {Read.class, Write.class})
    private Long deviceId;

    @NotNull(message = "point id can't be empty", groups = {Read.class, Write.class})
    private Long pointId;

    @NotBlank(message = "value can't be empty", groups = {Write.class})
    private String value;
}
