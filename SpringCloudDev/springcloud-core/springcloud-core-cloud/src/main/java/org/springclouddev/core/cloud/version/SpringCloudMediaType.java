package org.springclouddev.core.cloud.version;

import lombok.Getter;
import org.springframework.http.MediaType;

/**
 * Media Types，application/vnd.github.VERSION+json
 *
 * <p>
 * https://developer.github.com/v3/media/
 * </p>
 *
 * @author zhaobo
 */
@Getter
public class SpringCloudMediaType {
	private static final String MEDIA_TYPE_TEMP = "application/vnd.%s.%s+json";

	private final String appName = "springdclouddev";
	private final String version;
	private final MediaType mediaType;

	public SpringCloudMediaType(String version) {
		this.version = version;
		this.mediaType = MediaType.valueOf(String.format(MEDIA_TYPE_TEMP, appName, version));
	}

	@Override
	public String toString() {
		return mediaType.toString();
	}
}
