package com.jimmyproject.transactionservice.dtos;

import com.jimmyproject.transactionservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransferMapper {
    

    Transaction toEntity(TransactionRequestDto dto);
    
    TransactionResponseDto toResponseDto(Transaction transaction);
    
    List<TransactionResponseDto> toResponseDtoList(List<Transaction> transactions);
    

    void updateEntityFromDto(TransactionRequestDto dto, @org.mapstruct.MappingTarget Transaction entity);
}
