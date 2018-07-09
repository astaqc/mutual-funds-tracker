package com.fmillone.fci.fundStatus

enum RentType {
    VARIABLE(2),
    FIX(3),
    MIX(0),
    FIXED_TERM(0)

    final int code

    RentType(int code) {
        this.code = code
    }

}