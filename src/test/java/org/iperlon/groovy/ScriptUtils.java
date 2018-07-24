package org.iperlon.groovy;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class ScriptUtils {
    public static CompiledScript compileGroovyScript(String path) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Compilable compilable = (Compilable) engine;
        return compilable.compile(new InputStreamReader(ScriptEngineTest.class.getResourceAsStream(path)));
    }
}
