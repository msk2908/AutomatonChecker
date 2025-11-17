package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NEAGui extends JFrame {
    private static DrawingPanelR drawingPanelR;
    static List<StateDraw> states;
    static List<Transition> transitions;
    static InputAutomaton automaton;
    static boolean done = false;

    public NEAGui() {
        setTitle("Please draw your solution here");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        add(drawingPanelR, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addStateBtn = new JButton("Zustand hinzufügen");
        JButton doneBtn = new JButton("Fertig mit der Eingabe");
        JButton addTransitionBtn = new JButton("Übergang hinzufügen");
        JButton resetBtn = new JButton("Zurücksetzen");

        controlPanel.add(addStateBtn);
        controlPanel.add(addTransitionBtn);
        controlPanel.add(doneBtn);
        controlPanel.add(resetBtn);
        add(controlPanel, BorderLayout.SOUTH);

        addStateBtn.addActionListener(e -> drawingPanelR.setMode(DrawingPanelR.Mode.ADD_STATE));
        addTransitionBtn.addActionListener(e -> drawingPanelR.setMode(DrawingPanelR.Mode.ADD_TRANSITION));
        doneBtn.addActionListener(_ -> automaton.setComplete());
        resetBtn.addActionListener(e -> drawingPanelR.reset());

        setVisible(true);

        new javax.swing.Timer(10000, e -> {
            for (StateDraw stateDraw : drawingPanelR.getStates()) {
                if (!states.contains(stateDraw)) {
                    states.add(stateDraw);
                }
            }

            for (Transition transition: drawingPanelR.getTransitions()) {
                if (!transitions.contains(transition)) {
                    transitions.add(transition);
                }
            }
        }).start();
    }


    public static InputAutomaton inputMain() {
        drawingPanelR = new DrawingPanelR();
        states = drawingPanelR.getStates();
        transitions = drawingPanelR.getTransitions();
        SwingUtilities.invokeLater(NEAGui::new);

        automaton = new InputAutomaton(states, transitions, done);
        return automaton;
    }

}

