package com.meraphorce.mapper;

/**
 * Interface to implement convertions 
 * @param <T> 
 * @param <D>
 * @author Juan Chavez
 * @since May/14/2024
 */
public interface IMapper<T, D> {
	/**
	 * Converts DTO to Entity
	 * @param dtoValue DTO to convert
	 * @return Entity converted
	 * @author Juan Chavez
     * @since May/14/2024
	 */
	public T toEntity(D dtoValue);
	
	/**
	 * Convert Entity to DTO
	 * @param entityValue Entity to convert
	 * @return DTO converted
	 * @author Juan Chavez
     * @since May/14/2024
	 */
	public D toDTO(T entityValue);		
}
