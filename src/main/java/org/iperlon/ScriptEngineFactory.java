package org.iperlon;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

public class ScriptEngineFactory {
    public static ScriptEngine produceGroovyScriptEngine() {
        return produceScriptEngine(ScriptLanguage.Groovy).getScriptEngine();
    }

    public static ScriptEngineEnvironment produceScriptEngine(ScriptLanguage language) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName(language.name().toLowerCase());
        List<String> extensions = scriptEngine.getFactory().getExtensions();
        return new ScriptEngineEnvironment(language, scriptEngine, extensions.get(0));
    }
}
