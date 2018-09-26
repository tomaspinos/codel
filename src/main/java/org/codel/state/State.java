package org.codel.state;

public interface State<T> {

    String getName();

    StateType getType();

    boolean matches(T stateObject);
}
