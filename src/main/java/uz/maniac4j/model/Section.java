package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Section {
    FIRST("Тех отдел"),
    SECOND("Распил"),
    THIRD("Столярка"),
    FOURTH("Малярка"),
    FIFTH("Контр.сборка"),
    SIXTH("Монтаж"),
    SEVENTH("Роверный"),
    EIGHTH("Каменный"),
    NINTH("Бухгалтерия"),
    TENTH("Хоз.двор");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
