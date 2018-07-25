package org.iperlon;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static org.iperlon.Utils.getScript;

public class SimpleMethodScriptExecutor {
    private static final String SCRIPT_RESOURCE = "/{lang}/function.{ext}";

    private static final String FUNCTION_NAME = "sayHello";

    interface ScriptInterface {
        String sayHello(String name);
    }

    private final Invocable invocable;

    public SimpleMethodScriptExecutor(ScriptLanguage language) throws ScriptException {

        LanguageEnvironment languageEnvironment =
                ScriptEngineFactory.produceScriptEngine(language);

        final String scriptResource = SCRIPT_RESOURCE
                .replace("{lang}", language.name().toLowerCase())
                .replace("{ext}", languageEnvironment.getFileExtension());

        final ScriptEngine engine = languageEnvironment.getScriptEngine();

        engine.eval(getScript(scriptResource));
        invocable = (Invocable) engine;
    }

    public String sayHelloInLooselyTypedFashion(String name) throws ScriptException, NoSuchMethodException {

        Object result = invocable.invokeFunction(FUNCTION_NAME, name);
        return (String) result;
    }

    public String sayHelloInStronglyTypedFashion(String name) {

        ScriptInterface script = invocable.getInterface(ScriptInterface.class);
        return script.sayHello(name);
    }
}
