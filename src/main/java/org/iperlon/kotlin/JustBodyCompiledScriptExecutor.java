package org.iperlon.kotlin;

import org.iperlon.LanguageEnvironment;
import org.iperlon.ScriptLanguage;
import org.iperlon.domain.Person;
import org.iperlon.ScriptEngineFactory;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import static org.iperlon.Utils.getScript;

public class JustBodyCompiledScriptExecutor {

    private static final String SCRIPT_RESOURCE = "/{lang}/modifyParameter.{ext}";

    private final CompiledScript compiledScript;

    public JustBodyCompiledScriptExecutor(ScriptLanguage language) throws ScriptException {
        LanguageEnvironment languageEnvironment =
                ScriptEngineFactory.produceScriptEngine(language);

        Compilable compilable = (Compilable) languageEnvironment.getScriptEngine();
        final String scriptResource = SCRIPT_RESOURCE
                .replace("{lang}", language.name().toLowerCase())
                .replace("{ext}", languageEnvironment.getFileExtension());
        compiledScript = compilable.compile(getScript(scriptResource));
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
