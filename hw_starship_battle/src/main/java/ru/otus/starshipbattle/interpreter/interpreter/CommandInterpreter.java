package ru.otus.starshipbattle.interpreter.interpreter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.interpreter.exception.InterpretException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class CommandInterpreter implements Interpreter {

    private final String gameId;

    @Override
    public Command interpret(UObject order) {

        Object actionObj = order.getProperty("action");
        Class<Command> action = null;
        if (actionObj != null) {
            try {
                action = IoC.resolve(String.format("Games.%s.Actions.Types.Get", gameId), actionObj.toString());
            } catch (Exception e) {
                log.info("Interpret scope already exists");
            }
        }
        if (action == null) {
            throw new InterpretException("Action not found");
        }

        Object idObj = order.getProperty("id");
        UObject object;
        if (idObj != null) {
            object = IoC.resolve(String.format("Games.%s.Objects.Get", gameId), idObj.toString());
            if (object == null) {
                throw new InterpretException("Object not found");
            }
        } else {
            object = null;
        }

        Constructor<?>[] constructors = action.getConstructors();
        Field[] fields = action.getDeclaredFields();
        List<Object> actionParameters = new ArrayList<>();
        if (constructors.length > 0) {
            for (Parameter parameter : constructors[0].getParameters()) {
                Optional<Field> field = Arrays.stream(fields).filter(f -> f.getType() == parameter.getType()).findFirst();
                field.ifPresent(f -> {
                    if (f.getName().equalsIgnoreCase("object")) {
                        actionParameters.add(object);
                    } else {
                        actionParameters.add(order.getProperty(f.getName()));
                    }
                });
            }
        }

        Command command = IoC.resolve(String.format("Games.%s.Actions.Commands.Get", gameId), actionObj.toString(), actionParameters.toArray());
        if (command == null) {
            throw new InterpretException("Command not found");
        }

        return command;
    }
}