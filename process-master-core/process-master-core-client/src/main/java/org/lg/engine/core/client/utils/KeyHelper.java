package org.lg.engine.core.client.utils;

//import org.lg.engine.core.common.util.WfUtils;

public class KeyHelper {

    public static String randomKey(String prx){

        return prx+ System.currentTimeMillis() + Utils.getRandomNum(6);
    }
}
