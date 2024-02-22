package com.tacm.tabooksapi.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);
}
