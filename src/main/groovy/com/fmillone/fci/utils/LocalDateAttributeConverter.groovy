package com.fmillone.fci.utils

import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.sql.Date
import java.time.LocalDate

@Converter(autoApply = true)
class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    Date convertToDatabaseColumn(LocalDate locDate) {
        return (locDate == null ? null : Date.valueOf(locDate))
    }

    @Override
    LocalDate convertToEntityAttribute(Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDate())
    }
}