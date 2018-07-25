package org.iperlon.groovy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineFactory {
    public static ScriptEngine produceGroovyScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        return manager.getEngineByName("groovy");
    }
}
