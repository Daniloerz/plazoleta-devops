package com.plazoletapowerUp.infrastructure.out.feing.restClient;

import com.plazoletapowerUp.infrastructure.out.feing.dto.MessageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "twilio-powerup", url = "http://f68ws5vdf4.execute-api.us-east-1.amazonaws.com/plazoleta/twilio")
public interface ITwilioRestClient {

    @PostMapping("/twilio/send-message")
    Boolean sendMessage (MessageRequestDto messageRequestDto);
}
