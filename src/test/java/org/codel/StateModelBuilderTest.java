package org.codel;

import org.codel.state.StateDiagram;
import org.codel.state.model.StateModel;
import org.codel.state.model.StateModelBuilder;
import org.codel.state.renderer.StateDiagramPlantUmlRenderer;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

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

        try (OutputStream outputStream = new FileOutputStream("state.puml")) {
            new StateDiagramPlantUmlRenderer<StateEnum>().render(diagram, outputStream);
        }
    }

    private StateModel<StateEnum> createSampleModel() {
        return StateModelBuilder.fromEnum(StateEnum.class,
                stateEnum -> {
                    switch (stateEnum) {
                        case A:
                            return Optional.of("It all starts with state A");
                        case E:
                            return Optional.of("It all ends with state E");
                        default:
                            return Optional.empty();
                    }
                })
                .initialState()
                .finalState()
                .transitionFromInitialState(StateEnum.A, "Here we go")
                .transition(StateEnum.A, StateEnum.B)
                .transition(StateEnum.A, StateEnum.C)
                .transition(StateEnum.B, StateEnum.D)
                .transition(StateEnum.C, StateEnum.D)
                .transition(StateEnum.D, StateEnum.E)
                .transition(StateEnum.E, StateEnum.A)
                .transitionToFinalState(StateEnum.E, "Everything must come to an end")
                .build();
    }

    private enum StateEnum {
        A, B, C, D, E
    }
}
