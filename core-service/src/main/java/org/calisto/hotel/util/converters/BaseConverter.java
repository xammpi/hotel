package org.calisto.hotel.util.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public abstract class BaseConverter<D, E> implements Converter<D, E> {
    private final ModelMapper modelMapper;

    @Autowired
    public BaseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected abstract Class<D> dtoClass();

    protected abstract Class<E> entityClass();

    @Override
    public E convertToEntity(D dto) {
        return modelMapper.map(dto, entityClass());
    }

    @Override
    public D convertToDTO(E entity) {
        return modelMapper.map(entity, dtoClass());
    }

    @Override
    public List<D> convertToDTOList(Collection<? extends E> entityList) {
        return entityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> convertToEntityList(Collection<? extends D> dtoList) {
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(toList());
    }

    public Function<D, E> convertToEntity(Class<E> entityClass) {
        return this::convertToEntity;
    }

    public Function<E, D> convertToDTO(Class<D> dtoClass) {
        return this::convertToDTO;
    }

}
