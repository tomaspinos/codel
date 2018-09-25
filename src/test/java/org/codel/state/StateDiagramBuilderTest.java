package org.codel.state;

import org.junit.Assert;
import org.junit.Test;

public class StateDiagramBuilderTest {

    private enum StateEnum {
        A, B, C, D, E
    }

    @Test
    public void shouldCreateDiagramFromEnum() {
        StateDiagram<StateEnum> diagram = StateDiagramBuilder.fromEnum(StateEnum.class)
                .firstState(StateEnum.A)
                .exitPoint(StateEnum.E)
                .build();

        Assert.assertNotNull(diagram);
    }
}