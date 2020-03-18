package com.jdreamer.tutor;

import java.util.Objects;

public class Word {
    private String baseForm;
    private String reading;
    private String pronunciation;
    private String meaning;

    Word(String baseForm, String reading, String pronunciation, String meaning) {
        this.baseForm = baseForm;
        this.reading = reading;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public String getReading() {
        return reading;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(baseForm, word.baseForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseForm);
    }
}
