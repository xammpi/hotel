package org.calisto.hotel.util.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate convert(String source) {
        if (source != null && !source.isEmpty()) {
            return LocalDate.parse(source, formatter);
        }
        return null;
    }
}
