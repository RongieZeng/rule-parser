package org.zrz.util;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 执行脚本工具
 *
 * @author zrz
 */
public class ScriptUtil {
    /**
     * 脚本语言groovy
     */
    public static final String SCRIPT_GROOVY = "groovy";
    /**
     * 脚本语言javascript
     */
    public static final String SCRIPT_JAVASCRIPT = "javaScript";

    private static final ScriptEngineManager ENGINE_MANAGER = new ScriptEngineManager();
    /**
     * 脚本引擎缓存
     */
    private static final Map<String, ScriptEngine> SCRIPT_ENGINE_MAP = new HashMap<>();

    /**
     * 获取脚本引擎
     *
     * @param scriptLang 脚本语言 (groovy, javaScript, etc.)
     * @return 返回引擎对象
     */
    public static ScriptEngine getEngine(String scriptLang) {
        ScriptEngine engine = SCRIPT_ENGINE_MAP.get(scriptLang);
        if (engine == null) {
            engine = ENGINE_MANAGER.getEngineByName(scriptLang);
            SCRIPT_ENGINE_MAP.put(scriptLang, engine);
        }
        return engine;
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param script 需要执行的脚本文本
     * @param args 执行脚本方法传入的参数
     * @param functionName 执行的方法名
     * @return 返回执行结果
     * @throws ScriptException 脚本异常
     * @throws NoSuchMethodException 找不到定义的函数
     */
    public static Object invokeScriptFunction(String scriptLang, String script, String functionName, Object... args)
            throws ScriptException, NoSuchMethodException {

        ScriptEngine engine = getEngine(scriptLang);
        engine.eval(script);
        return ((Invocable) engine).invokeFunction(functionName, args);
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param bindings 给脚本设置的全局变量数据
     * @param script 需要执行的脚本文本
     * @param args 执行脚本方法传入的参数
     * @param functionName 执行的方法名
     * @return 返回执行结果
     * @throws ScriptException ScriptException
     * @throws NoSuchMethodException NoSuchMethodException
     */
    public static Object invokeScriptFunction(String scriptLang, Map<String, Object> bindings, String script,
                                              String functionName,
                                              Object... args) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = getEngine(scriptLang);
        Bindings data = engine.createBindings();
        for (String key : bindings.keySet()) {
            data.put(key, bindings.get(key));
        }

        engine.eval(script, data);
        return ((Invocable) engine).invokeFunction(functionName, args);
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param reader 需要执行的脚本文件
     * @param bindings 给脚本设置的全局变量数据
     * @param functionName 执行的方法名
     * @return 执行结果
     * @throws ScriptException ScriptException
     * @throws NoSuchMethodException NoSuchMethodException
     */
    public static Object invokeFileScriptFunction(String scriptLang, Reader reader, String functionName,
                                                  Map<String, Object> bindings)
            throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = getEngine(scriptLang);
        Bindings data = engine.createBindings();
        for (String key : bindings.keySet()) {
            data.put(key, bindings.get(key));
        }
        engine.eval(reader, data);
        return ((Invocable) engine).invokeFunction(functionName);
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param reader 需要执行的脚本文件
     * @param args 执行脚本方法传入的参数
     * @param functionName 执行的方法名
     * @return 执行结果
     * @throws ScriptException ScriptException
     * @throws NoSuchMethodException NoSuchMethodException
     */
    public static Object invokeFileScriptFunction(String scriptLang, Reader reader, String functionName, Object... args)
            throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = getEngine(scriptLang);
        engine.eval(reader);
        return ((Invocable) engine).invokeFunction(functionName, args);
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param bindings 给脚本设置的全局变量数据
     * @param reader 需要执行的脚本文件
     * @param args 执行脚本方法传入的参数
     * @param functionName 执行的方法名
     * @return 返回执行结果
     * @throws ScriptException ScriptException
     * @throws NoSuchMethodException NoSuchMethodException
     */
    public static Object invokeFileScriptFunction(String scriptLang, Map<String, Object> bindings, Reader reader,
                                                  String functionName,
                                                  Object... args) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = getEngine(scriptLang);
        Bindings data = engine.createBindings();
        for (String key : bindings.keySet()) {
            data.put(key, bindings.get(key));
        }
        engine.eval(reader, data);
        return ((Invocable) engine).invokeFunction(functionName, args);
    }
}