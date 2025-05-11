package ec.com.bank.domain.model.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final  class FunctionUtils {

    public static Instant now() {
        return Instant.now();
    }

    public static Instant maxDate() {
        return Instant.parse("9999-12-31T23:59:59.999Z");
    }

    public static Instant parseDateDdMmYyyy(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.atStartOfDay(ZoneOffset.UTC).toInstant();
    }

}
