package org.codel.state;

public class FinalState<T> implements State<T> {

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
