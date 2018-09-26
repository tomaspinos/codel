package org.codel.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateDiagram<T> {

    @Getter
    private final StateModel<T> model;

}
