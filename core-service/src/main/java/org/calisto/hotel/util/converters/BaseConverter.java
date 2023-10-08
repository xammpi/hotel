package org.calisto.hotel.util.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public abstract class BaseConverter<D, E> implements Converter<D, E> {
    private final ModelMapper modelMapper;
    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    @Autowired
    public BaseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.dtoClass = getDtoClass();
        this.entityClass = getEntityClass();
    }

    @Override
    public E convertToEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    @Override
    public D convertToDTO(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public List<D> convertToDTOList(Collection<? extends E> entityList) {
        return entityList.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<E> convertToEntityList(Collection<? extends D> dtoList) {
        return dtoList.stream()
                .map(this::convertToEntity)
                .toList();
    }

}
