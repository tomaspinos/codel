package org.codel.state.model;

import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class StateObjectBasedState<T> implements State<T> {

    private final T stateObject;
    private final String name;
    private final String description;

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public StateType getType() {
        return StateType.OTHER;
    }

    @Override
    public boolean matches(T stateObject) {
        return Objects.equals(this.stateObject, stateObject);
    }
}
