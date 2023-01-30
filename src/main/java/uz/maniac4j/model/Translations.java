package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Translations {

    ADD_REQUEST("\uD83D\uDCDD Новые расходы"),
    ADD_ITEM("Новый расход"),
    DELETE_ITEM("❌"),
    SEND_REQUEST("\uD83D\uDCE4 Расходовать"),
    BACK("\uD83D\uDD19 Выход"),
    CONFIRM_REQUEST("✅ Подтверждать"),
    CANCEL_REQUEST("❌ Отмена"),
    DRAFT_REQUEST("❗У вас не начисленный расход!"),
    BACK_SECTION("\uD83D\uDD19 Выход"),

    TXT_MAIN("Бот для расходов. Компания Командор."),
    TXT_SECTION("Выберите раздел"),
    TXT_SECTION_TYPE("Выберите вид"),
    ADD("Добавить расход"),
//    TXT_PRODUCT("Отправьте товар в этот раздел следующим образом:\n*имя:количество:примечание*"),
//    TXT_PRODUCT_ANOTHER("Отправьте товар в этот раздел следующим образом:\n*имя:количество:примечание*"),
    TXT_PRODUCT("""
        Отправьте расход в этот раздел следующим образом:
        <b>Сумма:Сумма:Примечание</b>\s
        
        Примеры:
        $100:300000:Подоход налог
        $100:Подоход налог
        300000:Подоход налог
        $100
        300000 \s

        Ставьте знак доллара "$" перед суммой, написанной в долларах США!"""),
    TXT_PRODUCT_ANOTHER("""
            Отправьте расход в этот раздел следующим образом:
            <b>Сумма:Сумма:Заказ</b>\s
            
            Примеры:
            $100:300000:F0XXX_XX
            $100:F0XXX_XX
            300000:F0XXX_XX \s
            
            Ставьте знак доллара "$" перед суммой, написанной в долларах США!"""),
    TXT_REQUEST_QUESTION("\n\n<b>Подтвердить расход?</b>"),
    TXT_SEND_REQUEST("Расход вычислен!");


    private final String ru;

    public String getRu() {
        return ru;
    }


}
