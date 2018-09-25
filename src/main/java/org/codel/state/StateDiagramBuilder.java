package org.codel.state;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateDiagramBuilder {

    public static <T extends Enum> EnumerationBuilder<T> fromEnum(Class<T> enumType) {
        return new EnumerationBuilder<>(enumType);
    }

    static class EnumerationBuilder<T extends Enum> {

        private final Class<T> enumType;
        private final List<State<T>> states;
        private State<T> firstState;
        private State<T> exitPoint;

        private EnumerationBuilder(Class<T> enumType) {
            Preconditions.checkArgument(enumType.isEnum(), "Argument must be enum");

            this.enumType = enumType;
            this.states = Arrays.stream(enumType.getEnumConstants())
                    .map(enumConstant -> new State<>(enumConstant, enumConstant.name()))
                    .collect(Collectors.toList());
        }

        public EnumerationBuilder<T> firstState(T enumConstant) {
            firstState = states.stream().filter(state -> state.matches(enumConstant)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("State not found: " + enumConstant));
            return this;
        }

        public EnumerationBuilder<T> exitPoint(T enumConstant) {
            exitPoint = states.stream().filter(state -> state.matches(enumConstant)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("State not found: " + enumConstant));
            return this;
        }

        public StateDiagram<T> build() {
            return new StateDiagram<>(states, firstState, exitPoint, null);
        }
    }
}
