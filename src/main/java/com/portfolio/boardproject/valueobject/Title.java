package com.portfolio.boardproject.valueobject;

import java.util.Objects;

public class Title {
    private String value;

    public Title(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
