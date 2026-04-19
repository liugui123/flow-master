
package org.lg.engine.core.client.exception;


//import org.lg.engine.core.client.utils.ApiException;

import org.lg.engine.core.client.utils.ApiException;

public class WfException extends ApiException {

    public WfException(String msg) {
        super(msg);
    }

    public WfException(Throwable e) {
        super(e);
    }

    public WfException(Integer code, String msg) {
        super(msg, code);
    }

    public WfException(String msg,Integer code) {
        super(msg, code);
    }

    public WfException(String msg,Exception e) {
        super(msg, e);
    }
}
