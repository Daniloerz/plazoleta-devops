package com.plazoletapowerUp.infrastructure.out.feing.adapter;

import com.plazoletapowerUp.domain.model.MessageModel;
import com.plazoletapowerUp.infrastructure.exception.TwilioServiceException;
import com.plazoletapowerUp.infrastructure.out.feing.dto.MessageRequestDto;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IMessageRequestMapper;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.ITwilioRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TwilioClientAdapterTest {
    private ITwilioRestClient twilioRestClient;
    private IMessageRequestMapper messageRequestMapper;
    private TwilioClientAdapter twilioClientAdapter;

    @BeforeEach
    public void setUp() {
        twilioRestClient = Mockito.mock(ITwilioRestClient.class);
        messageRequestMapper = Mockito.mock(IMessageRequestMapper.class);
        twilioClientAdapter = new TwilioClientAdapter(twilioRestClient, messageRequestMapper);
    }

    @Test
    public void testSendMessage_Success() {
        MessageModel messageModel = new MessageModel();
        MessageRequestDto messageRequestDto = new MessageRequestDto();

        Mockito.when(messageRequestMapper.toRequestDto(messageModel)).thenReturn(messageRequestDto);
        Mockito.when(twilioRestClient.sendMessage(messageRequestDto)).thenReturn(true);

        Boolean result = twilioClientAdapter.sendMessage(messageModel);

        Mockito.verify(twilioRestClient).sendMessage(messageRequestDto);
        Mockito.verify(messageRequestMapper).toRequestDto(messageModel);
        Assertions.assertTrue(result);
    }

    @Test
    public void testSendMessage_Exception() {
        MessageModel messageModel = new MessageModel();
        MessageRequestDto messageRequestDto = new MessageRequestDto();

        Mockito.when(messageRequestMapper.toRequestDto(messageModel)).thenReturn(messageRequestDto);
        Mockito.when(twilioRestClient.sendMessage(messageRequestDto)).thenThrow(new RuntimeException("Error"));

        Assertions.assertThrows(TwilioServiceException.class, () -> {
            twilioClientAdapter.sendMessage(messageModel);
        });

        Mockito.verify(twilioRestClient).sendMessage(messageRequestDto);
        Mockito.verify(messageRequestMapper).toRequestDto(messageModel);
    }

}