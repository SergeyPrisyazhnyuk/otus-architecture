package ru.otus.starshipbattle.core;

import lombok.extern.slf4j.Slf4j;
import ru.otus.starshipbattle.command.Command;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class SourceCodeGenerator {
    private static final String EMPTY = "";
    private static final String VOID = "void";
    private static final String RETURN = "return";
    private static final String VAR = "var";
    private static final String VAR_SPACE = " var";
    private static final String DELIMITER = ", ";
    private static final String COMMAND = "Command";
    private static final String EXECUTE = ".execute()";
    private static final String ADAPTER = "Adapter";
    private static final Map<String, String> LABEL_TO_FINAL_STRING = Map.of(
            "start", """
                    import ru.otus.starshipbattle.core.IoC;
                    import ru.otus.starshipbattle.model.impl.Vector;
                    import ru.otus.starshipbattle.model.Movable;
                    import ru.otus.starshipbattle.command.impl.UObject;
                    import ru.otus.starshipbattle.command.Command;
                    
                    public class M{0}Adapter implements M{0} '{'
                    protected final UObject o;
                    public M{0}Adapter(UObject o) '{'
                        this.o = o;
                    '}'
                    """,
            "method", """
                    public {0} {1}({2}) {7} '{' {3} (({4}) IoC.resolve("{1}", o{5})){6}; '}'
                    """,
            "end", "}"
    );

    public String generateAdapterSourceCodeFromInterface(Class<?> interfaceClass) {
        StringBuilder sourceCodeBuilder = new StringBuilder();

        String interfaceName = interfaceClass.getSimpleName();
        sourceCodeBuilder.append(MessageFormat.format(LABEL_TO_FINAL_STRING.get("start"), interfaceName.substring(1)));

        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(method -> {
            String[] arguments = new String[8];
            Class<?> returnType = method.getReturnType();
            boolean voidMethod = returnType == Void.TYPE;
            if (voidMethod) {
                arguments[0] = VOID;
                arguments[3] = EMPTY;
                arguments[4] = COMMAND;
                arguments[6] = EXECUTE;
                arguments[7] = EMPTY;

            } else {
                arguments[0] = returnType.getSimpleName();
                arguments[3] = RETURN;
                arguments[4] = returnType.getSimpleName();
                arguments[6] = EMPTY;
                arguments[7] = EMPTY;
            }
            arguments[1] = method.getName();

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                arguments[2] = EMPTY;
                arguments[5] = EMPTY;
            } else {
                StringBuilder methodArgumentsWithClassesStringBuilder = new StringBuilder();
                StringBuilder methodArgumentsStringBuilder = new StringBuilder(DELIMITER);
                for (int i = 0; i < parameterTypes.length; i++) {
                    String methodArgumentsWithClasses = parameterTypes[i].getSimpleName().concat(VAR_SPACE).concat(String.valueOf(i));
                    String methodArguments = VAR.concat(String.valueOf(i));
                    if (i != parameterTypes.length - 1) {
                        methodArgumentsWithClasses = methodArgumentsWithClasses.concat(DELIMITER);
                        methodArguments = methodArguments.concat(DELIMITER);
                    }
                    methodArgumentsWithClassesStringBuilder.append(methodArgumentsWithClasses);
                    methodArgumentsStringBuilder.append(methodArguments);
                }
                arguments[2] = methodArgumentsWithClassesStringBuilder.toString();
                arguments[5] = methodArgumentsStringBuilder.toString();
            }
            sourceCodeBuilder.append(MessageFormat.format(LABEL_TO_FINAL_STRING.get("method"), (Object[]) arguments));
        });

        sourceCodeBuilder.append(LABEL_TO_FINAL_STRING.get("end"));

        return sourceCodeBuilder.toString();
    }

    public <T> void generateAdapterClassFromInterface(Class<T> interfaceClass) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(javaCompiler.getStandardFileManager(null, null, null));

        String sourceCode = generateAdapterSourceCodeFromInterface(interfaceClass);
        String adapterName = generateAdapterName(interfaceClass);
        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(adapterName, sourceCode));

        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics().forEach(diagnostic -> log.error(String.valueOf(diagnostic)));
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            try {
                Class<?> adapterClass = classLoader.loadClass(adapterName);
                ((Command) IoC.resolve("IoC.Register", adapterName, (Function<Object[], Object>) (Object[] args) -> {
                    try {
                        return adapterClass.getDeclaredConstructor(new Class[]{(Class<?>) args[0]}).newInstance(new Object[]{args[1]});
                    } catch (Exception e) {
                        log.error("Exception while object creating of {} class", adapterClass.getSimpleName(), e);
                    }
                    return null;
                })).execute();
            } catch (Exception e) {
                log.error("Exception while {} class loading", adapterName, e);
            }
        }
    }

    private String generateAdapterName(Class<?> interfaceClass) {
        return interfaceClass.getSimpleName().substring(0) + ADAPTER;
    }
}