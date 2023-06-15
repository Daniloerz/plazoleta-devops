package com.plazoletapowerUp.domain.client;

import com.plazoletapowerUp.domain.model.MessageModel;

public interface ITwilioClientPort {
    Boolean sendMessage(MessageModel messageModel);
}
