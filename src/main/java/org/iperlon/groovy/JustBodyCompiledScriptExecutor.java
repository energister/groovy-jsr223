package org.iperlon.groovy;

import org.iperlon.groovy.domain.Person;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import static org.iperlon.groovy.Utils.getScript;

public class JustBodyCompiledScriptExecutor {

    private static final String SCRIPT_RESOURCE = "/groovy/modifyParameter.groovy";

    private final CompiledScript compiledScript;

    public JustBodyCompiledScriptExecutor() throws ScriptException {
        ScriptEngine engine = ScriptEngineFactory.produceGroovyScriptEngine();
        Compilable compilable = (Compilable) engine;
        compiledScript = compilable.compile(getScript(SCRIPT_RESOURCE));
    }

    public Person getPerson(int age) throws ScriptException {
        Person person = new Person();
        person.setAge(age);

        ScriptContext scriptContext = new SimpleScriptContext();
        scriptContext.setAttribute("_person", person, ScriptContext.ENGINE_SCOPE);
        compiledScript.eval(scriptContext);

        return person;
    }
}
