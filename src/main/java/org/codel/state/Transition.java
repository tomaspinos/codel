package org.codel.state;

import lombok.Data;

@Data
public class Transition<T> {

    private final State<T> fromState;
    private final State<T> toState;
}
