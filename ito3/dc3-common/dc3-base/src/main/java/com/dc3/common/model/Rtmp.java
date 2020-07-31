

package com.dc3.common.model;

import com.dc3.common.valid.Insert;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Rtmp
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Rtmp extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    private String name;

    @NotBlank(message = "rtsp url can't be empty", groups = {Insert.class})
    private String rtspUrl;

    @NotBlank(message = "rtmp url can't be empty", groups = {Insert.class})
    private String rtmpUrl;

    @NotBlank(message = "command can't be empty", groups = {Insert.class})
    private String command;

    private Short videoType;
    private Boolean run;
    private Boolean autoStart;

    public Rtmp(long id, boolean run) {
        super.setId(id);
        this.run = run;
    }

    public Rtmp(boolean autoStart) {
        this.autoStart = autoStart;
    }

}