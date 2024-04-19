package com.tacm.tabooksapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Thiết lập cấu hình cho ModelMapper để tránh vòng lặp vô hạn
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Bật khớp trường
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // Thiết lập quyền truy cập trường là PRIVATE
                .setSkipNullEnabled(true); // Bỏ qua các trường null

        return modelMapper;
    }
}