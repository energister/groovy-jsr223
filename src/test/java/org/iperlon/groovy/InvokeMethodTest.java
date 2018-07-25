package org.iperlon.groovy;

import org.junit.Assert;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static org.iperlon.groovy.Utils.getScript;

public class InvokeMethodTest {

    static final String SCRIPT_RESOURCE = "/groovy/function.groovy";
    static final String FUNCTION_NAME = "sayHello";

    @Test
    public void invokeMethodAndCheckResult() throws Exception {
        // Arrange
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        engine.eval(getScript(SCRIPT_RESOURCE));

        final String name = "Vasya";

        // Act
        Object result = ((Invocable)engine).invokeFunction(FUNCTION_NAME, name);

        // Assert
        @SuppressWarnings("unchecked")
        String greeting = (String) result;

        Assert.assertEquals("Hello, Vasya!", greeting);
    }
}
