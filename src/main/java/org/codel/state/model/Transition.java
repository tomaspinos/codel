package org.codel.state.model;

import lombok.Data;

import java.util.Optional;

@Data
public class Transition<T> {

    private final State<T> fromState;
    private final State<T> toState;
    private final String trigger;

    public Optional<String> getTrigger() {
        return Optional.ofNullable(trigger);
    }
}
