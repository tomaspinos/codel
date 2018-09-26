package org.codel.state;

import lombok.Data;

import java.util.Objects;

@Data
public class StateObjectBasedState<T> implements State<T> {

    private final T stateObject;
    private final String name;

    @Override
    public StateType getType() {
        return StateType.OTHER;
    }

    @Override
    public boolean matches(T stateObject) {
        return Objects.equals(this.stateObject, stateObject);
    }
}
