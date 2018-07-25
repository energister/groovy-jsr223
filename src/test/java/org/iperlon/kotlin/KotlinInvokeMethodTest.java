package org.iperlon.kotlin;

import org.iperlon.ScriptLanguage;
import org.iperlon.scriptexecutors.SimpleMethodScriptExecutor;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptException;

public class KotlinInvokeMethodTest {

    private final SimpleMethodScriptExecutor scriptExecutor;

    public KotlinInvokeMethodTest() throws ScriptException {
        scriptExecutor = new SimpleMethodScriptExecutor(ScriptLanguage.Kotlin);
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
