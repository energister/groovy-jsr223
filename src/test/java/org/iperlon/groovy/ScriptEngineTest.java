package org.iperlon.groovy;

import org.junit.Assert;
import org.junit.Test;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.iperlon.groovy.ScriptUtils.compileGroovyScript;

/**
 * Created by rodriguezc on 11.12.2015.
 */
public class ScriptEngineTest {

    static final String scriptResource = "/groovy/modifyParameter.groovy";

    static final int minAge = 0;
    static final int maxAge = 100;

    final CompiledScript compiledScript = compileGroovyScript(scriptResource);

    public ScriptEngineTest() throws ScriptException {
    }

    @Test
    public void singleThread() throws ScriptException {
        for (int i = minAge; i < maxAge; i++) {
            assertScriptInvocationForAge(i, compiledScript);
        }
    }

    @Test
    public void multipleThreads() throws Exception {
        final List<Thread> threadList = new ArrayList<>();

        final AtomicInteger nbThreadExecuted = new AtomicInteger(0);

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {

            final int randomAge = r.nextInt(maxAge-minAge) + minAge;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        assertScriptInvocationForAge(randomAge, compiledScript);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }
                    nbThreadExecuted.incrementAndGet();
                }
            };
            threadList.add(thread);
        }
        for(Thread thread : threadList) {
            thread.start();
        }

        while (nbThreadExecuted.get() < 1000) {
           Thread.sleep(1000);
        }
    }

    private void assertScriptInvocationForAge(int age, CompiledScript compiledScript) throws ScriptException {
        ScriptContext scriptContext = new SimpleScriptContext();
        Person person = new Person();
        person.setAge(age);
        scriptContext.setAttribute("_person", person, ScriptContext.ENGINE_SCOPE);
        compiledScript.eval(scriptContext);

        Assert.assertEquals(age > 17, person.isAdult());
    }
}
