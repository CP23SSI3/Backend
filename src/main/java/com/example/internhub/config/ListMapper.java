package com.example.internhub.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListMapper {
    private static final ListMapper listMapper = new ListMapper();
    private ListMapper() {
    }

    public static ListMapper getInstance() {return listMapper;}

    public <S,T> List mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper){
        return source.stream().map(entity -> modelMapper.map(entity, targetClass)).collect(Collectors.toList());
    }
}
