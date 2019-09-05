
package org.springclouddev.auth.granter;

import lombok.Data;
import org.springclouddev.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author zhaobohao
 */
@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
