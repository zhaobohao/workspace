
package org.springbootdev.modules.auth.granter;

import lombok.Data;
import org.springbootdev.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author merryChen
 */
@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
