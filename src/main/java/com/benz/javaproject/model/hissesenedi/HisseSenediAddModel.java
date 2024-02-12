package com.benz.javaproject.model.hissesenedi;

import com.benz.javaproject.entity.Hissedarlar;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
@Getter
@Setter
public class HisseSenediAddModel {
    private BigDecimal nominalDeger;
    private Hissedarlar hissedar;
    private int adet;
}
