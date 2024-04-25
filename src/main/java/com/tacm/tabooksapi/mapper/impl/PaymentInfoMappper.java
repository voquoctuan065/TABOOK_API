package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.PaymentInfoDto;
import com.tacm.tabooksapi.domain.entities.PaymentInfo;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInfoMappper implements Mapper<PaymentInfo, PaymentInfoDto> {
    private ModelMapper modelMapper;

    @Autowired
    public PaymentInfoMappper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentInfoDto mapTo(PaymentInfo paymentInfo) {
        return modelMapper.map(paymentInfo, PaymentInfoDto.class);
    }

    @Override
    public PaymentInfo mapFrom(PaymentInfoDto paymentInfoDto) {
        return modelMapper.map(paymentInfoDto, PaymentInfo.class);
    }
}
