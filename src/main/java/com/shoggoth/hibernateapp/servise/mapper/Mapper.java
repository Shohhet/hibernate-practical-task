package com.shoggoth.hibernateapp.servise.mapper;

public interface Mapper<F, T> {
    T mapFrom(F dto);
}
