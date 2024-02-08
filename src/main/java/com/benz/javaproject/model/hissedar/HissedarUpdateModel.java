package com.benz.javaproject.model.hissedar;

import com.benz.javaproject.enums.YatirimciTipi;
import lombok.Data;

@Data
public class HissedarUpdateModel {
    private String id; // Güncellenmek istenen ortağın kimliği
    private String unvan;
    private String adres;
    private String telefon;
    private YatirimciTipi yatirimciTipi;
    private String sicilNumarasi;
}
