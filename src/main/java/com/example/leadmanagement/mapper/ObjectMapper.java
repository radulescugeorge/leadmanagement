package com.example.leadmanagement.mapper;

public interface ObjectMapper<T, R> {
    T mapToDto(R r);
    R mapToEntity(T t);
}
