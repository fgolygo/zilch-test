package com.payzilch.card.model;

import com.payzilch.card.validation.EnumValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCardDto {

    @NotNull(message = "ZILCH_001")
    @Size(min = 3, max = 30, message = "ZILCH_002")
    private String name;

    @NotNull(message = "ZILCH_001")
    @Size(min = 3, max = 30, message = "ZILCH_002")
    private String surname;

    @NotNull(message = "ZILCH_001")
    @EnumValue(enumType = CardType.class, message = "ZILCH_005")
    private String type;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public CardType getType() {
        return CardType.valueOf(type);
    }

}
