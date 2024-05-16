package com.meraphorce.mapper;

public interface IMapper<T, D> {
	public T toEntity(D dtoValue);
	
	public D toDTO(T entityValue);
		
}
