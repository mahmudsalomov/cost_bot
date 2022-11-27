package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Section {
    FIRST("Коммуникация"),
    SECOND("Коммуналка"),
    THIRD("Зарплата"),
    FOURTH("Налог"),
    FIFTH("Хозяйственный"),
    SIXTH("Другие"),



    SEVENTH("Строительства"),
    EIGHTH("Зарплата по объему"),
    NINTH("Для заказать сырья и фурнитуры"),
    TENTH("Перевозка"),
    ELEVENTH("Другие");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
