package org.calisto.hotel.util.converters;

import org.springframework.core.ResolvableType;

import java.util.Collection;
import java.util.List;

public interface Converter<D, E> {

    default Class<D> getDtoClass() {
        return (Class<D>) getTypes(0);
    }

    default Class<E> getEntityClass() {
        return (Class<E>) getTypes(1);
    }

    private Class<?> getTypes(int index) {
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(BaseConverter.class);
        return resolvableType.resolveGenerics(BaseConverter.class)[index];
    }

    E convertToEntity(D dto);

    D convertToDTO(E entity);

    List<D> convertToDTOList(Collection<? extends E> entityList);

    List<E> convertToEntityList(Collection<? extends D> dtoList);

}
