package ru.otus.starshipbattletests.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.otus.starshipbattle.StarShipBattleApplication;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.endpoint.AgentMessage;
import ru.otus.starshipbattle.endpoint.AgentResponse;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = StarShipBattleApplication.class)
@Slf4j
@Import(AuthTestConfig.class)
class EndPointTest {
    @LocalServerPort
    private int port;
    private WebSocketStompClient webSocketStompClient;
    private final BlockingQueue<Command> commandsQueue = new ArrayBlockingQueue<>(1);
    private final BlockingQueue<AgentResponse> answerQueue = new ArrayBlockingQueue<>(1);

    @BeforeEach
    void setup() {
        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }
        Function<Object[], Object> func = (args) -> "testGame";
        ((Command) IoC.resolve("IoC.Register", "IoC.Scope.Id", func)).execute();
        Function<Object[], Object> findGameFunc = args -> {
            String gameId = IoC.resolve("IoC.Scope.Id");
            if (!Objects.equals(gameId, args[0])) {
                return IoC.resolve("IoC.Scope.Current");
            } else {
                return null;
            }
        };
        ((Command) IoC.resolve("IoC.Register", "IoC.Scope.Get", findGameFunc)).execute();

        Movable gameObject = mock(Movable.class);
        Function<Object[], Object> getGameObjectFunc = args -> gameObject;
        ((Command) IoC.resolve("IoC.Register", "testObjectId", getGameObjectFunc)).execute();

        Command c = mock(Command.class);
        Function<Object[], Object> setVelocityFunc = args -> c;
        ((Command) IoC.resolve("IoC.Register", "testOperationId", setVelocityFunc)).execute();

        Function<Object[], Object> addCommandFunc = args -> commandsQueue.add((Command) args[0]);
        ((Command) IoC.resolve("IoC.Register", "CommandsQueue.add", addCommandFunc)).execute();
    }

    @Test
    void sendCommandTest() throws ExecutionException, InterruptedException, TimeoutException {
        Object[] args = {new Vector(0, 0)};
        AgentMessage agentMessage = new AgentMessage("testGame", "testObjectId", "testOperationId",
                args);

        StompSession session = webSocketStompClient.connectAsync(String.format("ws://localhost:%d/ws", port),
                        new StompSessionHandlerAdapter() {
                        })
                .get(1, SECONDS);


        session.subscribe("/state", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return AgentResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                answerQueue.add((AgentResponse) payload);
            }
        });

        StompHeaders headers = new StompHeaders();
        headers.add(StompHeaders.DESTINATION, "/game/action");
        headers.add(AUTHORIZATION,
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                        ".eyJpc3MiOiJBdXRob3JpemF0aW9uIG1vZHVsZSIsInN1YiI6IkdhbWVyIGRldGFpbHMiLCJnYW1lcklkIjoidGVzdEdhbWVyIiwiZ2FtZUlkIjoidGVzdEdhbWUiLCJyb2xlcyI6WyJHQU1FUiJdLCJpYXQiOjE3NTMyODk0NDEsImp0aSI6IjhjMDgzZGNmLWI0MTYtNDBhZi1hMmYyLTJhZDMwNTUzZGVhMyIsIm5iZiI6MTc1MzI4OTQ0Mn0" +
                        ".QJESm5fdO7nZD_V4NULXDyPTB2lS_Av9NiJBaXi3v1o"
        );

        session.send(headers, agentMessage);

        await()
                .atMost(1, SECONDS)
                .untilAsserted(() -> assertFalse(answerQueue.isEmpty()));
    }
}

