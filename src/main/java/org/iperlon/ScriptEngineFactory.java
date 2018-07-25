package org.iperlon;

import org.iperlon.LanguageEnvironment;
import org.iperlon.ScriptLanguage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

public class ScriptEngineFactory {
    public static ScriptEngine produceGroovyScriptEngine() {
        return produceScriptEngine(ScriptLanguage.Groovy).getScriptEngine();
    }

    public static LanguageEnvironment produceScriptEngine(ScriptLanguage language) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName(language.name().toLowerCase());
        List<String> extensions = scriptEngine.getFactory().getExtensions();
        return new LanguageEnvironment(scriptEngine, extensions.get(0));
    }
}
