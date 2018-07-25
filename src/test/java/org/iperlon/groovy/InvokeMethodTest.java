package org.iperlon.groovy;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.iperlon.groovy.Utils.getScript;

public class InvokeMethodTest {

    static final String SCRIPT_RESOURCE = "/groovy/function.groovy";
    static final String FUNCTION_NAME = "getPersonMaybe";

    static final int MIN_AGE = 0;
    static final int MAX_AGE = 100;

    @Test
    public void test() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        engine.eval(getScript(SCRIPT_RESOURCE));

        for (int i = MIN_AGE; i < MAX_AGE; i++) {
            assertScriptInvocationForAge(i, (Invocable) engine);
        }
    }

    private void assertScriptInvocationForAge(int age, Invocable engine) throws ScriptException, NoSuchMethodException {

        Object result = engine.invokeFunction(FUNCTION_NAME, age);

        @SuppressWarnings("unchecked")
        com.google.common.base.Optional<Person> person = (Optional<Person>) result;

        Assert.assertTrue(person.isPresent());
        Assert.assertEquals(age > 17, person.get().isAdult());
    }
}
