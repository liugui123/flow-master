package org.lg.engine.core.client.utils;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class Page<T> implements Serializable {

    private T data;

    private Collection<T> rows;

    private Integer total = 0;
}
