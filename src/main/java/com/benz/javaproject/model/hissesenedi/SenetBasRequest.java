package com.benz.javaproject.model.hissesenedi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class SenetBasRequest {
    private BigDecimal nominal;
    private int adet;
}