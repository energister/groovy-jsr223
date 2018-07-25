package org.iperlon.groovy;

import org.iperlon.groovy.domain.Person;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static org.iperlon.groovy.Utils.getScript;

public class MethodWithExternalDependencyScriptExecutor {
    private static final String SCRIPT_RESOURCE = "/groovy/externalDependency.groovy";

    interface ScriptInterface {
        com.google.common.base.Optional<Person> getPersonMaybe(int age);
    }

    private final ScriptInterface script;

    public MethodWithExternalDependencyScriptExecutor() throws ScriptException {
        ScriptEngine engine = ScriptEngineFactory.produceGroovyScriptEngine();
        engine.eval(getScript(SCRIPT_RESOURCE));

        script = ((Invocable) engine).getInterface(ScriptInterface.class);
    }

    public com.google.common.base.Optional<Person> tryGetPerson(int age) {
        return script.getPersonMaybe(age);
    }
}
