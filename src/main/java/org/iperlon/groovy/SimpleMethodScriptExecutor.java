package org.iperlon.groovy;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.iperlon.groovy.Utils.getScript;

public class SimpleMethodScriptExecutor {
    private static final String SCRIPT_RESOURCE = "/groovy/function.groovy";

    private static final String FUNCTION_NAME = "sayHello";

    interface ScriptInterface {
        String sayHello(String name);
    }

    private final Invocable invocable;

    public SimpleMethodScriptExecutor() throws ScriptException {
        ScriptEngine engine = ScriptEngineFactory.produceGroovyScriptEngine();
        engine.eval(getScript(SCRIPT_RESOURCE));

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
