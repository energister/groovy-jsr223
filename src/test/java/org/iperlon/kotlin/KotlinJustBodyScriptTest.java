package org.iperlon.kotlin;

import org.iperlon.scriptexecutors.kotlin.JustBodyCompiledScriptExecutor;
import org.iperlon.ScriptLanguage;
import org.iperlon.domain.Person;
import org.junit.Ignore;
import org.junit.Test;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class KotlinJustBodyScriptTest {

    static final int MIN_AGE = 0;
    static final int MAX_AGE = 100;

    private final JustBodyCompiledScriptExecutor scriptExecutor;

    public KotlinJustBodyScriptTest() throws ScriptException {
        scriptExecutor = new JustBodyCompiledScriptExecutor(ScriptLanguage.Kotlin);
    }

    @Ignore("TODO fix")
    @Test
    public void singleThread() throws ScriptException {
        for (int i = MIN_AGE; i < MAX_AGE; i++) {
            assertScriptInvocationForAge(i);
        }
    }

    @Ignore("TODO fix")
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
                        assertScriptInvocationForAge(randomAge);
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

    private void assertScriptInvocationForAge(int age) throws ScriptException {
        // Act
        Person person = scriptExecutor.getPerson(age);

        // Assert
        assertEquals(age > 17, person.isAdult());
    }
}
