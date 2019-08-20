
package org.springclouddev.auth.granter;

import lombok.Data;
import org.springclouddev.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author firewan
 */
@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
