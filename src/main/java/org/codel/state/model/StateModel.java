package org.codel.state.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StateModel<T> {

    private final InitialState<T> initialState;
    private final FinalState<T> finalState;
    @Getter
    private final List<State<T>> allStates;
    private final Function<State<T>, List<Transition<T>>> transitionFunction;

    public boolean contains(T stateObject) {
        return allStates.stream().anyMatch(state -> state.matches(stateObject));
    }

    public boolean containsTransition(T fromStateObject, T toStateObject) {
        return containsTransition(getState(fromStateObject), getState(toStateObject));
    }

    public boolean containsTransition(T fromStateObject, State<T> toState) {
        return containsTransition(getState(fromStateObject), toState);
    }

    public boolean containsTransition(State<T> fromState, T toStateObject) {
        return containsTransition(fromState, getState(toStateObject));
    }

    public boolean containsTransition(State<T> fromState, State<T> toState) {
        return getTransitions(fromState).stream()
                .anyMatch(transition -> Objects.equals(transition.getToState(), toState));
    }

    public List<Transition<T>> getAllTransitions() {
        return allStates.stream()
                .flatMap(state -> getTransitions(state).stream())
                .collect(Collectors.toList());
    }

    public Optional<FinalState<T>> getFinalState() {
        return Optional.ofNullable(finalState);
    }

    public Optional<InitialState<T>> getInitialState() {
        return Optional.ofNullable(initialState);
    }

    public List<Transition<T>> getTransitions(T fromStateObject) {
        return getTransitions(getState(fromStateObject));
    }

    public List<Transition<T>> getTransitions(State<T> fromState) {
        return MoreObjects.firstNonNull(transitionFunction.apply(fromState), ImmutableList.of());
    }

    private State<T> getState(T stateObject) {
        return allStates.stream().filter(state -> state.matches(stateObject)).findFirst()
                .orElseThrow(() -> new IllegalStateException("State not found: " + stateObject));
    }
}
