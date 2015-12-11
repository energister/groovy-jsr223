package org.iperlon.groovy;

import org.junit.Assert;
import org.junit.Test;

import javax.script.*;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rodriguezc on 11.12.2015.
 */
public class ScriptEngineTest {

    @Test
    public void test1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Compilable compilable = (Compilable) engine;
        final CompiledScript compiledScript = compilable.compile(new InputStreamReader(ScriptEngineTest.class.getResourceAsStream("/groovy/rule1.groovy")));


        for (int i = 0; i < 100; i++) {
            ScriptContext scriptContext = new SimpleScriptContext();
            Person person = new Person();
            person.setAge(i);
            scriptContext.setAttribute("_person", person, ScriptContext.ENGINE_SCOPE);
            compiledScript.eval(scriptContext);
            Assert.assertEquals(i > 17, person.isAdult());
        }

        final List<Thread> threadList = new ArrayList();

        final AtomicInteger nbThreadExecuted = new AtomicInteger(0);

        for (int i = 0; i < 1000; i++) {
            Random r = new Random();
            int minAge = 0;
            int maxAge = 100;

            final int randomAge = r.nextInt(maxAge-minAge) + minAge;


            Thread thread = new Thread() {
                @Override
                public void run() {
                    ScriptContext scriptContext = new SimpleScriptContext();
                    Person person = new Person();

                    person.setAge(randomAge);
                    scriptContext.setAttribute("_person", person, ScriptContext.ENGINE_SCOPE);
                    try {
                        compiledScript.eval(scriptContext);
                        Assert.assertEquals(randomAge > 17, person.isAdult());
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
}
