package org.codel.state.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedListMultimap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StateModelBuilder {

    public static <T extends Enum> EnumerationBuilder<T> fromEnum(Class<T> enumType) {
        return new EnumerationBuilder<>(enumType);
    }

    public static <T extends Enum> EnumerationBuilder<T> fromEnum(Class<T> enumType, Function<T, Optional<String>> stateDescriptionFunction) {
        return new EnumerationBuilder<>(enumType, stateDescriptionFunction);
    }

    public static class EnumerationBuilder<T extends Enum> {

        private final List<State<T>> allStates;
        private InitialState<T> initialState;
        private FinalState<T> finalState;
        private LinkedListMultimap<State<T>, Transition<T>> transitions;

        private EnumerationBuilder(Class<T> enumType) {
            this(enumType, enumConst -> Optional.empty());
        }

        private EnumerationBuilder(Class<T> enumType, Function<T, Optional<String>> stateDescriptionFunction) {
            Preconditions.checkArgument(enumType.isEnum(), "Argument must be enum");

            this.allStates = Arrays.stream(enumType.getEnumConstants())
                    .map(enumConstant ->
                            new StateObjectBasedState<>(enumConstant, enumConstant.name(), stateDescriptionFunction.apply(enumConstant).orElse(null)))
                    .collect(Collectors.toList());
            this.transitions = LinkedListMultimap.create();
        }

        public StateModel<T> build() {
            return new StateModel<>(initialState, finalState, allStates, fromState -> transitions.get(fromState));
        }

        public EnumerationBuilder<T> finalState() {
            Preconditions.checkState(finalState == null, "Final state initialized already");

            finalState = new FinalState<>();
            allStates.add(finalState);
            return this;
        }

        public EnumerationBuilder<T> initialState() {
            Preconditions.checkState(initialState == null, "Initial state initialized already");

            initialState = new InitialState<>();
            allStates.add(initialState);
            return this;
        }

        public EnumerationBuilder<T> transition(T fromEnumConstant, T toEnumConstant) {
            return transition(fromEnumConstant, toEnumConstant, null);
        }

        public EnumerationBuilder<T> transition(T fromEnumConstant, T toEnumConstant, String trigger) {
            State<T> fromState = getState(fromEnumConstant);
            State<T> toState = getState(toEnumConstant);
            transitions.put(fromState, new Transition<>(fromState, toState, trigger));
            return this;
        }

        public EnumerationBuilder<T> transitionFromInitialState(T toEnumConstant) {
            return transitionFromInitialState(toEnumConstant, null);
        }

        public EnumerationBuilder<T> transitionFromInitialState(T toEnumConstant, String trigger) {
            Preconditions.checkState(initialState != null, "No initial state");

            State<T> toState = getState(toEnumConstant);
            transitions.put(initialState, new Transition<>(initialState, toState, trigger));
            return this;
        }

        public EnumerationBuilder<T> transitionToFinalState(T fromEnumConstant) {
            return transitionToFinalState(fromEnumConstant, null);
        }

        public EnumerationBuilder<T> transitionToFinalState(T fromEnumConstant, String trigger) {
            Preconditions.checkState(finalState != null, "No final state");

            State<T> fromState = getState(fromEnumConstant);
            transitions.put(fromState, new Transition<>(fromState, finalState, trigger));
            return this;
        }

        private State<T> getState(T enumConstant) {
            return allStates.stream().filter(state -> state.matches(enumConstant)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("State not found: " + enumConstant));
        }
    }
}
