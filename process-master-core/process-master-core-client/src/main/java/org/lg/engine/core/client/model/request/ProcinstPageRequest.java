
package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.utils.Query;

@Getter
@Setter
public class ProcinstPageRequest extends Query {

    private String procName;

    private Long minGmtCreate;

    private Long maxGmtCreate;
}
