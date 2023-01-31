package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SectionType {
    FIRST_TYPE("\uD83C\uDFDB Постоянные расходы"),
    SECOND_TYPE("\uD83D\uDCC8 Переменные расходы"),
    THIRD_TYPE("Приход");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
