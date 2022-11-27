package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Translations {

    ADD_REQUEST("\uD83D\uDCDD Новая заявка"),
    ADD_ITEM("Новый продукт"),
    DELETE_ITEM("❌"),
    SEND_REQUEST("\uD83D\uDCE4 Отправить заявку"),
    BACK("\uD83D\uDD19 Выход"),
    CONFIRM_REQUEST("✅ Подтверждать"),
    CANCEL_REQUEST("❌ Отмена"),
    DRAFT_REQUEST("❗У вас есть неотправленный заявку!"),
    BACK_SECTION("\uD83D\uDD19 Выход"),

    TXT_MAIN("Бот для онлайн заявок. Компания Командор. Выбирайте свой цех и напишите что вам нужно."),
    TXT_SECTION("Выберите раздел"),
    TXT_SECTION_TYPE("Выберите вид"),
    ADD("Добавить продукт"),
//    TXT_PRODUCT("Отправьте товар в этот раздел следующим образом:\n*имя:количество:примечание*"),
//    TXT_PRODUCT_ANOTHER("Отправьте товар в этот раздел следующим образом:\n*имя:количество:примечание*"),
    TXT_PRODUCT("Отправьте товар в этот раздел следующим образом:\n*Сумма:Примечание*"),
    TXT_PRODUCT_ANOTHER("Отправьте товар в этот раздел следующим образом:\n*Сумма:Номер заказа*"),
    TXT_REQUEST_QUESTION("\n\n*Подтвердить заявку?*"),
    TXT_SEND_REQUEST("Заявка отправлена!");


    private final String ru;

    public String getRu() {
        return ru;
    }


}
