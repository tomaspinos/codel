package org.codel.state;

import java.io.BufferedWriter;
import java.io.IOException;

public class StateDiagramPlantUmlRenderer<T> {

    public void render(StateDiagram<T> diagram, BufferedWriter writer) throws IOException {
        writeln("@startuml", writer);

        StateModel<T> model = diagram.getModel();

        for (State<T> state : model.getAllStates()) {
            if (state.getType() == StateType.OTHER) {
                writeln("state " + getAlias(state), writer);
            }
        }

        for (Transition<T> transition : model.getAllTransitions()) {
            writeln(getAlias(transition.getFromState()) + " --> " + getAlias(transition.getToState()), writer);
        }

        writeln("@enduml", writer);
    }

    private String getAlias(State<T> state) {
        switch (state.getType()) {
            case INITIAL:
            case FINAL:
                return "[*]";
            case OTHER:
                return state.getName();
            default:
                throw new IllegalStateException("Unknown state type: " + state.getType());
        }
    }

    private void writeln(String str, BufferedWriter writer) throws IOException {
        writer.write(str);
        writer.newLine();
    }
}
