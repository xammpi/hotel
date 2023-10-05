package org.calisto.hotel.util.converters;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class BaseConverter {
    private final Class<?> dtoClass;
    private final Class<?> entityClass;

    private BaseConverter(Class<?> dtoClass, Class<?> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public static BaseConverter create(Class<?> dtoClass, Class<?> entityClass) {
        return new BaseConverter(dtoClass, entityClass);
    }

    public <D, E> D convertToDTO(E entity) {
        Objects.requireNonNull(entity, "Input entity cannot be null");

        try {
            D dto = createInstance(dtoClass);
            mapFields(entity, dto);
            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to convert entity to DTO", e);
        }
    }

    public <D, E> E convertToEntity(D dto) {
        Objects.requireNonNull(dto, "Input DTO cannot be null");
        try {
            E entity = createInstance(entityClass);
            mapFields(dto, entity);
            return entity;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to convert DTO to entity", e);
        }
    }

    public <D, E> List<D> convertToDTOList(List<E> entityList) {
        return (List<D>) entityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public <D, E> List<E> convertToEntityList(List<D> dtoList) {
        return (List<E>) dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    private <T> T createInstance(Class<?> clazz) throws ReflectiveOperationException {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return (T) instance;
    }

    private void mapFields(Object source, Object target) {
        Map<String, Field> sourceFields = getFieldMap(source.getClass());
        Map<String, Field> targetFields = getFieldMap(target.getClass());

        sourceFields.forEach((fieldName, sourceField) -> {
            Field targetField = targetFields.get(fieldName);
            if (Objects.nonNull(targetField)) {
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                try {
                    Object value = sourceField.get(source);
                    if (Objects.nonNull(value)) {
                        targetField.set(target, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to map field: " + fieldName, e);
                }
            }
        });
    }

    private Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        while (Objects.nonNull(clazz)) {
            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> fieldMap.put(field.getName(), field));
            clazz = clazz.getSuperclass();
        }
        return Collections.unmodifiableMap(fieldMap);
    }
}
