package org.iperlon;

import javax.script.ScriptEngine;
import java.io.InputStreamReader;

public class ScriptEngineEnvironment {

    private ScriptLanguage language;
    private ScriptEngine scriptEngine;
    private String fileExtension;

    public ScriptEngineEnvironment(ScriptLanguage language, ScriptEngine scriptEngine, String fileExtension) {
        this.language = language;
        this.scriptEngine = scriptEngine;
        this.fileExtension = fileExtension;
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getScriptResourcePath(String scriptName) {
        return "/" + language.name().toLowerCase() +
                "/" + scriptName + "." + fileExtension;
    }

    public InputStreamReader getScript(String scriptName) {
        String scriptResourcePath = getScriptResourcePath(scriptName);
        return new InputStreamReader(this.getClass().getResourceAsStream(scriptResourcePath));
    }
}
