package com.plazoletapowerUp.infrastructure.out.feing.adapter;

import com.plazoletapowerUp.domain.client.ITwilioClientPort;
import com.plazoletapowerUp.domain.model.MessageModel;
import com.plazoletapowerUp.infrastructure.exception.TwilioServiceException;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IMessageRequestMapper;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.ITwilioRestClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwilioClientAdapter implements ITwilioClientPort {

    private final ITwilioRestClient twilioRestClient;
    private final IMessageRequestMapper messageRequestMapper;

    @Override
    public Boolean sendMessage(MessageModel messageModel) {
        try{
            return twilioRestClient.sendMessage(messageRequestMapper.toRequestDto(messageModel));
        } catch (Exception e){
            throw new TwilioServiceException(e.getMessage());
        }
    }
}
