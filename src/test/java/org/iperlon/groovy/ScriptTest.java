package org.iperlon.groovy;

import org.iperlon.groovy.domain.Person;
import org.junit.Assert;
import org.junit.Test;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.iperlon.groovy.Utils.getScript;

/**
 * Created by rodriguezc on 11.12.2015.
 */
public class ScriptTest {

    static final String SCRIPT_RESOURCE = "/groovy/modifyParameter.groovy";

    static final int MIN_AGE = 0;
    static final int MAX_AGE = 100;

    final CompiledScript compiledScript = compileGroovyScript(SCRIPT_RESOURCE);

    public ScriptTest() throws ScriptException {
    }

    public static CompiledScript compileGroovyScript(String path) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Compilable compilable = (Compilable) engine;
        return compilable.compile(getScript(path));
    }

    @Test
    public void singleThread() throws ScriptException {
        for (int i = MIN_AGE; i < MAX_AGE; i++) {
            assertScriptInvocationForAge(i, compiledScript);
        }
    }

    @Test
    public void multipleThreads() throws Exception {
        final List<Thread> threadList = new ArrayList<>();

        final AtomicInteger nbThreadExecuted = new AtomicInteger(0);

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {

            final int randomAge = r.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE;
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
