package org.codel.state;

import lombok.Data;

import java.util.Objects;

@Data
public class State<T> {

    private final T stateObject;
    private final String name;

    public boolean matches(T stateObject) {
        return Objects.equals(this.stateObject, stateObject);
    }
}
