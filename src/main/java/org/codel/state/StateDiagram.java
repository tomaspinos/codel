package org.codel.state;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class StateDiagram<T> {

    private final List<State<T>> states;
    private final State<T> firstState;
    private final State<T> exitPoint;
    private final Function<State<T>, List<Transition<T>>> transitionFunction;
}
