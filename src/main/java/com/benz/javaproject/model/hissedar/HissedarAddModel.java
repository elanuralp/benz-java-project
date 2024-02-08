package com.benz.javaproject.model.hissedar;

import com.benz.javaproject.enums.YatirimciTipi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HissedarAddModel {


    private String unvan;
    private String adres;
    private String telefon;
    private YatirimciTipi yatirimciTipi;
    private String sicilNumarasi;

}

