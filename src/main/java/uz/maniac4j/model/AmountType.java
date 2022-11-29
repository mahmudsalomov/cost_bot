package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AmountType {
    USD("Доллар"),
    UZS("Сум");

    private final String ru;

    public String getRu() {
        return ru;
    }
}
