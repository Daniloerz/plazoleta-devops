package com.plazoletapowerUp.infrastructure.out.feing.mapper;

import com.plazoletapowerUp.domain.model.MessageModel;
import com.plazoletapowerUp.infrastructure.out.feing.dto.MessageRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IMessageRequestMapper {
    MessageRequestDto toRequestDto(MessageModel messageModel);
    MessageModel toModel(MessageRequestDto messageRequestDto);
}
