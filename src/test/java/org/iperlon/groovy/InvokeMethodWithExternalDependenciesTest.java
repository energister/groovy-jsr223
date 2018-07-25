package org.iperlon.groovy;

import com.google.common.base.Optional;
import org.iperlon.groovy.domain.Person;
import org.junit.Test;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvokeMethodWithExternalDependenciesTest {

    private final MethodWithExternalDependencyScriptExecutor scriptExecutor;

    public InvokeMethodWithExternalDependenciesTest() throws ScriptException {
        scriptExecutor = new MethodWithExternalDependencyScriptExecutor();
    }

    static final int MIN_AGE = 0;
    static final int MAX_AGE = 100;

    @Test
    public void test() {
        for (int i = MIN_AGE; i < MAX_AGE; i++) {
            // Act
            Optional<Person> person = scriptExecutor.tryGetPerson(i);

            // Assert
            assertTrue(person.isPresent());
            assertEquals(i > 17, person.get().isAdult());
        }
    }
}
