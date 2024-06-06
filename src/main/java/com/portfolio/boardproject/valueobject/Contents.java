package com.portfolio.boardproject.valueobject;

import java.util.Objects;

public class Contents {
    private String value;

    public Contents(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contents title = (Contents) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
