package org.iperlon.groovy;

import org.junit.Assert;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.iperlon.groovy.Utils.getScript;

public class InvokeMethodTest {

    static final String SCRIPT_RESOURCE = "/groovy/function.groovy";

    final Invocable invocable = prepareInvocableGroovyScript(SCRIPT_RESOURCE);

    public InvokeMethodTest() throws ScriptException {
    }

    public static Invocable prepareInvocableGroovyScript(String path) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        engine.eval(getScript(path));
        return (Invocable) engine;
    }

    interface ScriptInterface {
        String sayHello(String name);
    }

    @Test
    public void looselyTypedInvocation() throws Exception {
        // Arrange
        final String FUNCTION_NAME = "sayHello";
        final String parameter = "Vasya";

        // Act
        Object result = invocable.invokeFunction(FUNCTION_NAME, parameter);

        // Assert
        @SuppressWarnings("unchecked")
        String greeting = (String) result;

        Assert.assertEquals("Hello, Vasya!", greeting);
    }

    @Test
    public void stronglyTypedInvocation() {
        // Arrange
        ScriptInterface script = invocable.getInterface(ScriptInterface.class);

        // Act
        String greeting = script.sayHello("Vasya");

        // Assert
        Assert.assertEquals("Hello, Vasya!", greeting);
    }
}
