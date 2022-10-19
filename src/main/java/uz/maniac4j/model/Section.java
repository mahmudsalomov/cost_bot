package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Section {
    FIRST("birinchi"),
    SECOND("ikkinchi"),
    THIRD("uchinchi"),
    FOURTH("to'rtinchi"),
    FIFTH("beshinchi"),
    SIXTH("oltinchi");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
