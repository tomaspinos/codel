package org.codel.state.model;

import lombok.Data;

import java.util.Optional;

@Data
public class FinalState<T> implements State<T> {

    @Override
    public Optional<String> getDescription() {
        return Optional.empty();
    }

    @Override
    public String getName() {
        return "final";
    }

    @Override
    public StateType getType() {
        return StateType.FINAL;
    }

    @Override
    public boolean matches(T stateObject) {
        return false;
    }
}
