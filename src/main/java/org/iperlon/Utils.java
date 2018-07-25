package org.iperlon;

import java.io.InputStreamReader;

public class Utils {
    public static InputStreamReader getScript(String resource) {
        return new InputStreamReader(Utils.class.getResourceAsStream(resource));
    }
}
