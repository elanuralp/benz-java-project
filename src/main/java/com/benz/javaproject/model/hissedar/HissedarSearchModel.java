package com.benz.javaproject.model.hissedar;

import com.benz.javaproject.enums.YatirimciTipi;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HissedarSearchModel {


    private String unvan;
    private String adres;
    private String telefon;
    private YatirimciTipi yatirimciTipi;
    private String sicilNumarasi;
}
