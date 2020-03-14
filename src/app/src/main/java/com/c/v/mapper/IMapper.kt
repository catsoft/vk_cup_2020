package com.c.v.mapper

interface IMapper<TSource, TDestination> {
    fun map(from : TSource) : TDestination
}