package org.codel.state;

public class InitialState<T> implements State<T> {

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
