package uz.maniac4j.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    FIRST("birinchi"),
    SECOND("ikkinchi"),
    THIRD("uchinchi");



    private final String ru;

    public String getRu() {
        return ru;
    }
}
