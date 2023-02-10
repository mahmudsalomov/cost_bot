package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Translations {

    TXT("<b>\uD83D\uDCDD Расходы</b>\n","<b>\uD83D\uDCDD Приходы</b>\n"),
    ADD_REQUEST("\uD83D\uDCDD Новые расходы","\uD83D\uDCDD Новые приходы"),
    ADD_ITEM("Новый расход","Новый приход"),
    DELETE_ITEM("❌","❌"),
    SEND_REQUEST("\uD83D\uDCE4 Расходовать","\uD83D\uDCE4 Приходовать"),
    BACK("\uD83D\uDD19 Выход","\uD83D\uDD19 Выход"),
//    CONFIRM_REQUEST("✅ Подтверждать"),
    CONFIRM_REQUEST("✅ Подтвердить","✅ Подтвердить"),
    CANCEL_REQUEST("❌ Отмена","❌ Отмена"),
    DRAFT_REQUEST("❗У вас не начисленный расход!","❗У вас не начисленный приход!"),
    BACK_SECTION("\uD83D\uDD19 Выход","\uD83D\uDD19 Выход"),

    TXT_MAIN("Бот для расходов. Компания Командор.","Бот для расходов. Компания Командор."),
    TXT_SECTION("Выберите раздел","Выберите раздел"),
    TXT_SECTION_TYPE("Выберите вид","Выберите вид"),
    ADD("Добавить расход","Добавить приход"),
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

        Ставьте знак доллара "$" перед суммой, написанной в долларах США!""",
        """
        Отправьте приход в этот раздел следующим образом:
        <b>Сумма:Сумма:Примечание</b>\s
        
        Примеры:
        $100:300000:Подоход налог
        $100:Подоход налог
        300000:Подоход налог
        $100
        300000 \s

        Ставьте знак доллара "$" перед суммой, написанной в долларах США!"""),
//    TXT_PRODUCT_ANOTHER("""
//            Отправьте расход в этот раздел следующим образом:
//            <b>Сумма:Сумма:Заказ</b>\s
//
//            Примеры:
//            $100:300000:F0XXX_XX
//            $100:F0XXX_XX
//            300000:F0XXX_XX \s
//
//            Ставьте знак доллара "$" перед суммой, написанной в долларах США!"""),
    TXT_REQUEST_QUESTION("\n\n<b>Подтвердить расход?</b>","\n\n<b>Подтвердить приход?</b>"),
    TXT_SEND_REQUEST("Расход вычислен!","Приход зафиксирован!");
//    TXT_SEND_REQUEST_2("Приход зафиксирован!");


    private final String ru;
    private final String ru_prixod;

    public String getRu() {
        return ru;
    }

    public String getRu(TelegramUser user) {
        if (user.getSectionType()!=null&&user.getSectionType().equals(SectionType.THIRD_TYPE)) return getRu_prixod();
        return ru;
    }


    public String getRu_prixod() {
        return ru_prixod;
    }
}
