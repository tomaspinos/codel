package org.codel.state.model;

import lombok.Data;

import java.util.Optional;

@Data
public class InitialState<T> implements State<T> {

    @Override
    public Optional<String> getDescription() {
        return Optional.empty();
    }

    @Override
    public String getName() {
        return "initial";
    }

    @Override
    public StateType getType() {
        return StateType.INITIAL;
    }

    @Override
    public boolean matches(T stateObject) {
        return false;
    }
}
