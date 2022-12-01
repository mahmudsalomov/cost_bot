package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Section {
    FIRST("\uD83D\uDCDE Коммуникация"),
    SECOND("\uD83D\uDEB0 Коммуналка"),
    THIRD("\uD83D\uDCB5 Зарплата"),
    FOURTH("\uD83C\uDFEC Налог"),
    FIFTH("\uD83E\uDE93 Хозяйственный"),
    SIXTH("\uD83D\uDDD3 Другие"),



    SEVENTH("\uD83E\uDE9A Строительства"),
    EIGHTH("\uD83D\uDCB0 Зарплата по объему"),
    NINTH("\uD83D\uDCD0 Для заказать сырья и фурнитуры"),
    TENTH("\uD83D\uDE9B Перевозка"),
    ELEVENTH("\uD83D\uDDD3 Другие");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
