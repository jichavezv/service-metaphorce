package com.meraphorce.config;

import com.meraphorce.dtos.UserRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfig {

    public static void main(String[] args) {

    }

    public void hacer(){
        System.out.println("hola");
    }

    @Bean()
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<UserRequest, User>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setEmail(source.getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserResponse>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setEmail(source.getEmail());
            }
        });

        return modelMapper;
    }
}
