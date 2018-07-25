package org.iperlon;

import javax.script.ScriptEngine;

public class LanguageEnvironment {

    private ScriptEngine scriptEngine;
    private String fileExtension;

    public LanguageEnvironment(ScriptEngine scriptEngine, String fileExtension) {
        this.scriptEngine = scriptEngine;
        this.fileExtension = fileExtension;
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
