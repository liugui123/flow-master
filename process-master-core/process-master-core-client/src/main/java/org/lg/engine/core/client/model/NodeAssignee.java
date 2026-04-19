
package org.lg.engine.core.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class NodeAssignee extends Assignee {

    private String actinstName;
}
