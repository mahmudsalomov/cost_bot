package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Translations {

    ADD_REQUEST("\uD83D\uDCDD Yangi zayavka"),
    ADD_ITEM("Yangi mahsulot"),
    DELETE_ITEM("❌"),
    SEND_REQUEST("\uD83D\uDCE4 Zayavkani yuborish"),
    BACK("\uD83D\uDD19 EXIT"),
    CONFIRM_REQUEST("✅ Confirm"),
    CANCEL_REQUEST("❌ Cancel"),
    DRAFT_REQUEST("❗Sizda  yuborilmagan zayavka bor!"),
    BACK_SECTION("\uD83D\uDD19 EXIT");


    private final String ru;

    public String getRu() {
        return ru;
    }


}
