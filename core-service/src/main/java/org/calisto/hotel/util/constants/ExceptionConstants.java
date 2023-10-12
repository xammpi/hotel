package org.calisto.hotel.util.constants;

public class ExceptionConstants {
    public static final String NOT_FOUND_EXCEPTION_TEMPLATE = "The %s not found with %s:'%s'.";
    public static final String CONFLICT_EXCEPTION_TEMPLATE = "The %s with %s: %s is already exist.";
    public static final String CONFLICT_RESERVATION_TEMPLATE = "The room: %s is occupied for selected dates:" +
            " Check-in date: %s, Check-out date: %s";
}
