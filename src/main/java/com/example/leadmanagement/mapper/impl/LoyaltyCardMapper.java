package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.LoyaltyCardDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.LoyaltyCard;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyCardMapper implements ObjectMapper<LoyaltyCardDto, LoyaltyCard> {
    @Override
    public LoyaltyCardDto mapToDto(LoyaltyCard loyaltyCard) {
        return new LoyaltyCardDto(
                loyaltyCard.getId(),
                loyaltyCard.getSerialNumber(),
                loyaltyCard.getDiscount()
        );
    }

    @Override
    public LoyaltyCard mapToEntity(LoyaltyCardDto loyaltyCardDto) {
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setSerialNumber(loyaltyCardDto.getSerialNumber());
        loyaltyCard.setDiscount(loyaltyCardDto.getDiscount());
        return loyaltyCard;
    }
}
