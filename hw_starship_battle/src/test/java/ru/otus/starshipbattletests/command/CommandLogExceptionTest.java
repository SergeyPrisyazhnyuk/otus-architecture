package ru.otus.starshipbattletests.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ru.otus.starshipbattle.command.impl.ExceptionLogCommand;

@ExtendWith(MockitoExtension.class)
public class CommandLogExceptionTest {

    private Appender<ILoggingEvent> mockedAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> loggingEventCaptor;

    @BeforeEach
    public void initCommandLogExceptionTest() {
        mockedAppender = mock(Appender.class);
        Logger root = (Logger) LoggerFactory.getLogger(ExceptionLogCommand.class);
        root.addAppender(mockedAppender);
        root.setLevel(Level.ERROR);
    }

    @Test
    void logCommandTest() {
        doNothing().when(mockedAppender).doAppend(loggingEventCaptor.capture());
        new ExceptionLogCommand(new RuntimeException("Command execute exception")).execute();

        ILoggingEvent loggingEvent = loggingEventCaptor.getAllValues().get(0);
        assertEquals("Command execute exception", loggingEvent.getMessage());
        assertEquals(Level.ERROR, loggingEvent.getLevel());
    }
}
