package com.fmillone.fci.importing

import com.fmillone.fci.fundStatus.RentType
import com.fmillone.fci.fundStatus.TrustStatus
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import org.apache.poi.hssf.OldExcelFormatException
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.validator.ValidationException

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static java.time.format.DateTimeFormatter.ofPattern

@Log4j
@CompileStatic
class MapToFundStatus implements ItemProcessor<InputStream, List<TrustStatus>> {

    private static final int NAME = 0
    private static final int CURRENCY = 1
    private static final int DATE = 4
    private static final int ACTUAL_VALUE_PER_1000 = 5
    private static final DateTimeFormatter formatter = ofPattern('dd/MM/yy')

    Iterator<Row> iterator
    Row currentRow
    List<TrustStatus> fundStatuses = []
    RentType currentRentType

    @Override
    List<TrustStatus> process(InputStream item) throws Exception {
        init(item)

        skipRowsUntil('Renta Variable Peso Argentina')

        try {
            importFundStatuses()
        } catch (Exception ex) {
            log.error("Row number: $currentRow.rowNum ", ex)
            throw ex
        }
        return fundStatuses
    }

    private void importFundStatuses() {
        while (iterator.hasNext()) {
            readNextRow()

            if (getCellAt(1) != null) {
                fundStatuses << newFundStatus(currentRentType)
            } else {
                updateRentType()
            }
        }
    }

    private void updateRentType() {
        String value = valueAt(0)
        if (value.contains('Renta Variable')) {
            currentRentType = RentType.VARIABLE
        } else if (value.contains('Renta Fija')) {
            currentRentType = RentType.FIX
        } else if (value.contains('Renta Mixta')) {
            currentRentType = RentType.MIX
        } else if (value.contains('Plazo Fijo')) {
            currentRentType = RentType.FIXED_TERM
        } else {
            while (iterator.hasNext()) {
                iterator.next()
            }
        }
    }

    private String valueAt(int index) {
        getCellAt(index).stringCellValue
    }

    private void skipRowsUntil(String separator) {
        boolean done = false
        while (iterator.hasNext() && !done) {
            readNextRow()
            done = valueAt(NAME) == separator
        }
    }

    private void init(InputStream item) {
        fundStatuses = []
        try {
            iterator = new HSSFWorkbook(item)
                    .getSheetAt(0)
                    .iterator()
        } catch (OldExcelFormatException ex) {
            log.warn('skipping file due ', ex)
            throw new ValidationException('skipping file')
        }
    }

    private static LocalDate extractDate(String date) {
        LocalDate.parse(date, formatter)
    }

    private TrustStatus newFundStatus(RentType rentType) {
        new TrustStatus(
                name: getCellAt(NAME).stringCellValue,
                currency: getCellAt(CURRENCY).stringCellValue,
                date: extractDate(getCellAt(DATE).stringCellValue),
                unitaryValue: getCellAt(ACTUAL_VALUE_PER_1000).numericCellValue / 1000,
                totalValue: getCellAt(14).numericCellValue,
                amountOfPieces: getCellAt(12).numericCellValue.toLong(),
                rentType: rentType
        )
    }

    private void readNextRow() {
        currentRow = iterator.next()
    }

    private Cell getCellAt(int index) {
        currentRow.getCell(index)
    }
}
