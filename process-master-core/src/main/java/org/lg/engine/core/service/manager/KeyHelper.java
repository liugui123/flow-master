package org.lg.engine.core.service.manager;

import org.lg.engine.core.client.utils.Utils;

public class KeyHelper {

    public static String randomKey(String prx) {

        return prx + System.currentTimeMillis() + Utils.getRandomNum(10);
    }
}
