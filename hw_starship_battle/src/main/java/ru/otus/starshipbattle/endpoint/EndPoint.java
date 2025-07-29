package ru.otus.starshipbattle.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.gameauth.service.GameAuthorizerService;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.InterpretCommand;
import ru.otus.starshipbattle.exception.AuthException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class EndPoint {
    private final GameAuthorizerService gameAuthorizerService;

    @MessageMapping("/action")
    @SendTo("/state")
    public AgentResponse onMessage(@Header(AUTHORIZATION) String auth,
                                   @Payload AgentMessage message) {

        if (gameAuthorizerService.authorize(auth, message.gameId(), "GAMER")) {
            Command interpretCommand = new InterpretCommand(message.gameId(), message.objectId(), message.operationId(),
                    message.args());
            interpretCommand.execute();
            return new AgentResponse("Response with game state");
        } else {
            throw new AuthException();
        }
    }

}
