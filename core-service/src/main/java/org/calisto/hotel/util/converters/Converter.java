package org.calisto.hotel.util.converters;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface Converter<D, E> {

    E convertToEntity(D dto);

    D convertToDTO(E entity);

    List<D> convertToDTOList(Collection<? extends E> entityList);

    List<E> convertToEntityList(Collection<? extends D> dtoList);

    Function<D, E> convertToEntity(Class<E> entityClass);

    Function<E, D> convertToDTO(Class<D> dtoClass);
}
