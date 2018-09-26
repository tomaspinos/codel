package org.codel.state.model;

import java.util.Optional;

public interface State<T> {

    Optional<String> getDescription();

    String getName();

    StateType getType();

    boolean matches(T stateObject);
}
