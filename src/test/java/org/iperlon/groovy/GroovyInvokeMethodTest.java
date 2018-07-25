package org.iperlon.groovy;

import org.iperlon.ScriptLanguage;
import org.iperlon.SimpleMethodScriptExecutor;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptException;

public class GroovyInvokeMethodTest {

    private final SimpleMethodScriptExecutor scriptExecutor;

    public GroovyInvokeMethodTest() throws ScriptException {
        scriptExecutor = new SimpleMethodScriptExecutor(ScriptLanguage.Groovy);
    }

    final String name = "Vasya";

    @Test
    public void looselyTypedInvocation() throws ScriptException, NoSuchMethodException {
        // Act
        String greeting = scriptExecutor.sayHelloInLooselyTypedFashion(name);

        // Assert
        Assert.assertEquals("Hello, Vasya!", greeting);
    }

    @Test
    public void stronglyTypedInvocation() {
        // Act
        String greeting = scriptExecutor.sayHelloInStronglyTypedFashion(name);

        // Assert
        Assert.assertEquals("Hello, Vasya!", greeting);
    }
}
