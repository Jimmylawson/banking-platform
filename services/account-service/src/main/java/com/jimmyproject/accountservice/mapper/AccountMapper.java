package com.jimmyproject.accountservice.mapper;

import com.jimmyproject.accountservice.dtos.AccountRequestDto;
import com.jimmyproject.accountservice.dtos.AccountResponseDto;
import com.jimmyproject.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between Account entity and DTOs.
 */
@Mapper(
    componentModel = "spring"
)
public interface AccountMapper {

    /**
     * Converts AccountRequestDto to Account entity.
     *
     * @param dto the DTO to convert
     * @return the converted Account entity
     */
    @Mapping(target = "id", ignore = true)
    Account toEntity(AccountRequestDto dto);

    /**
     * Converts Account entity to AccountResponseDto.
     *
     * @param account the entity to convert
     * @return the converted DTO
     */
    AccountResponseDto toResponseDto(Account account);

    /**
     * Updates Account entity from AccountRequestDto.
     *
     * @param dto    the DTO with updated values
     * @param entity the entity to update
     */

    @Mapping(target = "accountNumber", ignore = true) // Prevent updating account number
    void updateEntityFromDto(AccountRequestDto dto, @MappingTarget Account entity);
}
