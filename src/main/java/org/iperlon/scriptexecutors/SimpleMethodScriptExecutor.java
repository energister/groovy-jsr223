package org.iperlon.scriptexecutors;

import org.iperlon.ScriptEngineEnvironment;
import org.iperlon.ScriptEngineFactory;
import org.iperlon.ScriptLanguage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class SimpleMethodScriptExecutor {
    private static final String SCRIPT_RESOURCE_NAME = "function";

    private static final String FUNCTION_NAME = "sayHello";

    interface ScriptInterface {
        String sayHello(String name);
    }

    private final Invocable invocable;

    public SimpleMethodScriptExecutor(ScriptLanguage language) throws ScriptException {

        ScriptEngineEnvironment environment = ScriptEngineFactory.produceScriptEngine(language);

        final ScriptEngine engine = environment.getScriptEngine();
        final InputStreamReader script = environment.getScript(SCRIPT_RESOURCE_NAME);

        engine.eval(script);
        invocable = (Invocable) engine;
    }

    public String sayHelloInLooselyTypedFashion(String name) throws ScriptException, NoSuchMethodException {

        Object result = invocable.invokeFunction(FUNCTION_NAME, name);
        return (String) result;
    }

    public String sayHelloInStronglyTypedFashion(String name) {

        ScriptInterface script = invocable.getInterface(ScriptInterface.class);
        return script.sayHello(name);
    }
}
