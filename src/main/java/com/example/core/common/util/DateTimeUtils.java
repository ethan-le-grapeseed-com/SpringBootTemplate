package com.example.core.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time operations.
 * Provides common methods for date/time formatting and conversion.
 */
public final class DateTimeUtils {
    
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SIMPLE_TIME_FORMAT = "HH:mm:ss";
    
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATE_TIME_FORMAT);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_TIME_FORMAT);
    
    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Get current instant.
     */
    public static Instant now() {
        return Instant.now();
    }
    
    /**
     * Convert Instant to ISO formatted string.
     */
    public static String toIsoString(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(ISO_FORMATTER);
    }
    
    /**
     * Convert LocalDateTime to ISO formatted string.
     */
    public static String toIsoString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(ISO_FORMATTER);
    }
    
    /**
     * Convert Instant to simple date string (yyyy-MM-dd).
     */
    public static String toDateString(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(DATE_FORMATTER);
    }
    
    /**
     * Convert Instant to simple time string (HH:mm:ss).
     */
    public static String toTimeString(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(TIME_FORMATTER);
    }
    
    /**
     * Parse ISO formatted string to Instant.
     */
    public static Instant parseIsoString(String isoString) {
        if (isoString == null || isoString.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(isoString, ISO_FORMATTER).toInstant(ZoneOffset.UTC);
    }
    
    /**
     * Check if an instant is in the past.
     */
    public static boolean isPast(Instant instant) {
        return instant != null && instant.isBefore(Instant.now());
    }
    
    /**
     * Check if an instant is in the future.
     */
    public static boolean isFuture(Instant instant) {
        return instant != null && instant.isAfter(Instant.now());
    }
    
    /**
     * Convert Instant to LocalDateTime in system default timezone.
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    
    /**
     * Convert LocalDateTime to Instant using system default timezone.
     */
    public static Instant toInstant(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
