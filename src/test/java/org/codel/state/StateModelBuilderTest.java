package org.codel.state;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class StateModelBuilderTest {

    @Test
    public void shouldCreateModelFromEnum() {
        StateModel<StateEnum> model = createSampleModel();

        assertNotNull(model);

        assertThat(model.getAllStates().size(), is(7));
        assertThat(model.getInitialState().isPresent(), is(true));
        assertThat(model.getFinalState().isPresent(), is(true));

        assertThat(model.getAllTransitions().size(), is(8));
        assertThat(model.getTransitions(model.getInitialState().get()).size(), is(1));
        assertThat(model.containsTransition(model.getInitialState().get(), StateEnum.A), is(true));
        assertThat(model.getTransitions(StateEnum.A).size(), is(2));
        assertThat(model.containsTransition(StateEnum.A, StateEnum.B), is(true));
        assertThat(model.containsTransition(StateEnum.A, StateEnum.C), is(true));
        assertThat(model.getTransitions(StateEnum.B).size(), is(1));
        assertThat(model.containsTransition(StateEnum.B, StateEnum.D), is(true));
        assertThat(model.getTransitions(StateEnum.C).size(), is(1));
        assertThat(model.containsTransition(StateEnum.C, StateEnum.D), is(true));
        assertThat(model.getTransitions(StateEnum.D).size(), is(1));
        assertThat(model.containsTransition(StateEnum.D, StateEnum.E), is(true));
        assertThat(model.getTransitions(StateEnum.E).size(), is(2));
        assertThat(model.containsTransition(StateEnum.E, StateEnum.A), is(true));
        assertThat(model.containsTransition(StateEnum.E, model.getFinalState().get()), is(true));
        assertThat(model.getTransitions(model.getFinalState().get()).size(), is(0));
    }

    @Test
    public void x() throws IOException {
        StateDiagram<StateEnum> diagram = new StateDiagram<>(createSampleModel());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("state.puml"))) {
            new StateDiagramPlantUmlRenderer<StateEnum>().render(diagram, writer);
        }
    }

    private StateModel<StateEnum> createSampleModel() {
        return StateModelBuilder.fromEnum(StateEnum.class)
                .initialState()
                .finalState()
                .transitionFromInitialState(StateEnum.A)
                .transition(StateEnum.A, StateEnum.B)
                .transition(StateEnum.A, StateEnum.C)
                .transition(StateEnum.B, StateEnum.D)
                .transition(StateEnum.C, StateEnum.D)
                .transition(StateEnum.D, StateEnum.E)
                .transition(StateEnum.E, StateEnum.A)
                .transitionToFinalState(StateEnum.E)
                .build();
    }

    private enum StateEnum {
        A, B, C, D, E
    }
}
