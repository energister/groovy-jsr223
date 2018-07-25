package org.iperlon.scriptexecutors.kotlin;

import org.iperlon.ScriptEngineEnvironment;
import org.iperlon.ScriptEngineFactory;
import org.iperlon.ScriptLanguage;
import org.iperlon.domain.Person;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.InputStreamReader;

public class JustBodyCompiledScriptExecutor {

    private static final String SCRIPT_RESOURCE_NAME = "modifyParameter";

    private final CompiledScript compiledScript;

    public JustBodyCompiledScriptExecutor(ScriptLanguage language) throws ScriptException {

        ScriptEngineEnvironment environment = ScriptEngineFactory.produceScriptEngine(language);

        Compilable compilable = (Compilable) environment.getScriptEngine();
        final InputStreamReader script = environment.getScript(SCRIPT_RESOURCE_NAME);

        compiledScript = compilable.compile(script);
    }

    public Person getPerson(int age) throws ScriptException {
        Person person = new Person();
        person.setAge(age);

        ScriptContext scriptContext = new SimpleScriptContext();
        scriptContext.setAttribute("_person", person, ScriptContext.ENGINE_SCOPE);
        compiledScript.getEngine().put("_person", person);
        compiledScript.eval();

        return person;
    }
}
