package com.plazoletapowerUp.infrastructure.out.feing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {

    private String phoneNumber;
    private String securityCode;
}

