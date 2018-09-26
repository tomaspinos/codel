package org.codel.state.renderer;

import lombok.val;
import org.codel.state.StateDiagram;
import org.codel.state.model.State;
import org.codel.state.model.StateType;

import java.io.OutputStream;
import java.io.PrintWriter;

public class StateDiagramPlantUmlRenderer<T> {

    public void render(StateDiagram<T> diagram, OutputStream outputStream) {
        val writer = new PrintWriter(outputStream);

        writer.println("@startuml");

        val model = diagram.getModel();

        for (val state : model.getAllStates()) {
            if (state.getType() == StateType.OTHER) {
                writer.print("state " + getAlias(state));
                state.getDescription().ifPresent(description -> writer.print(" : " + description));
                writer.println();
            }
        }

        for (val transition : model.getAllTransitions()) {
            writer.print(getAlias(transition.getFromState()) + " --> " + getAlias(transition.getToState()));
            transition.getTrigger().ifPresent(trigger -> writer.print(" : " + trigger));
            writer.println();
        }

        writer.println("@enduml");

        writer.flush();
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
}
