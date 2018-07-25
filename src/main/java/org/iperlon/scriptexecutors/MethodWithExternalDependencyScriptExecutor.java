package org.iperlon.scriptexecutors;

import org.iperlon.ScriptEngineEnvironment;
import org.iperlon.ScriptEngineFactory;
import org.iperlon.ScriptLanguage;
import org.iperlon.domain.Person;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class MethodWithExternalDependencyScriptExecutor {
    private static final String SCRIPT_RESOURCE_NAME = "externalDependency";

    interface ScriptInterface {
        com.google.common.base.Optional<Person> getPersonMaybe(int age);
    }

    private final ScriptInterface script;

    public MethodWithExternalDependencyScriptExecutor(ScriptLanguage language) throws ScriptException {

        ScriptEngineEnvironment environment = ScriptEngineFactory.produceScriptEngine(language);

        final ScriptEngine engine = environment.getScriptEngine();
        final InputStreamReader scriptResource = environment.getScript(SCRIPT_RESOURCE_NAME);

        engine.eval(scriptResource);
        script = ((Invocable) engine).getInterface(ScriptInterface.class);
    }

    public com.google.common.base.Optional<Person> tryGetPerson(int age) {
        return script.getPersonMaybe(age);
    }
}
