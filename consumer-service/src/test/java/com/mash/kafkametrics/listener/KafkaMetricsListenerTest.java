package com.mash.kafkametrics.listener;

import com.mash.kafkametrics.listener.consumer.MetricsMessagesConsumer;
import com.mash.kafkametrics.model.MetricsData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class KafkaMetricsListenerTest {
    @Mock
    MetricsMessagesConsumer consumer;

    @InjectMocks
    KafkaMetricsListener listener;

    @Test
    void handleListen_shouldCallMessagesConsumer() {
        MessageHeaderAccessor accessor = new MessageHeaderAccessor();
        accessor.setHeader("foo", "bar");
        MessageHeaders headers = accessor.getMessageHeaders();
        Message<MetricsData> message1 = MessageBuilder.createMessage(MetricsData.builder().build(), headers);
        Message<MetricsData> message2 = MessageBuilder.createMessage(MetricsData.builder().build(), headers);
        List<Message<MetricsData>> messages = List.of(message1, message2);
        ArgumentCaptor<List<Message<MetricsData>>> captor = ArgumentCaptor.captor();
        Mockito.doNothing().when(this.consumer).accept(captor.capture());

        this.listener.listen(messages);

        Assertions.assertEquals(messages, captor.getValue());
    }
}