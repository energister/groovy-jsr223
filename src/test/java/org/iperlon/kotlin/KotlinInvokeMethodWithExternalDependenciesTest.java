package org.iperlon.kotlin;

import com.google.common.base.Optional;
import org.iperlon.ScriptLanguage;
import org.iperlon.domain.Person;
import org.iperlon.scriptexecutors.MethodWithExternalDependencyScriptExecutor;
import org.junit.Test;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KotlinInvokeMethodWithExternalDependenciesTest {

    private final MethodWithExternalDependencyScriptExecutor scriptExecutor;

    public KotlinInvokeMethodWithExternalDependenciesTest() throws ScriptException {
        scriptExecutor = new MethodWithExternalDependencyScriptExecutor(ScriptLanguage.Kotlin);
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
