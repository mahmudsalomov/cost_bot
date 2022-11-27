package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SectionType {
    FIRST_TYPE("Постоянные расходы"),
    SECOND_TYPE("Переменные расходы");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
