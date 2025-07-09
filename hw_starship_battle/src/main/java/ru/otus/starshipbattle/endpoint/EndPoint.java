package ru.otus.starshipbattle.endpoint;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.InterpretCommand;

@Controller
public class EndPoint {

    @MessageMapping("/action")
    @SendTo("/state")
    public AgentResponse onMessage(@Payload AgentMessage message) {
            Command interpretCommand = new InterpretCommand(message.gameId(), message.objectId(), message.operationId(),
                    message.args());
            interpretCommand.execute();
            return new AgentResponse("Response with game state");
    }

}
