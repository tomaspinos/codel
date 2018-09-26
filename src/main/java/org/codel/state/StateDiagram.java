package org.codel.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.codel.state.model.StateModel;

@RequiredArgsConstructor
public class StateDiagram<T> {

    @Getter
    private final StateModel<T> model;

}
