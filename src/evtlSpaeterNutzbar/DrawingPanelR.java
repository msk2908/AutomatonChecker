package evtlSpaeterNutzbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DrawingPanelR extends JPanel {
    enum Mode {NONE, ADD_STATE, ADD_TRANSITION}

    private Mode currentMode = Mode.NONE;
    private List<StateDraw> stateDraws = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private StateDraw selectedStateDrawForTransition = null;

    public DrawingPanelR() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                switch (currentMode) {
                    case ADD_STATE:
                        String name = JOptionPane.showInputDialog("Name des Zustands:");
                        if (name == null || name.isEmpty()) return;

                        boolean isStart = JOptionPane.showConfirmDialog(null, "Startzustand?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        boolean isFinal = JOptionPane.showConfirmDialog(null, "Endzustand?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        stateDraws.add(new StateDraw(name, e.getX(), e.getY(), isStart, isFinal));
                        repaint();
                        break;

                    case ADD_TRANSITION:
                        for (StateDraw s : stateDraws) {
                            if (s.contains(e.getPoint())) {
                                if (selectedStateDrawForTransition == null) {
                                    selectedStateDrawForTransition = s;
                                } else {
                                    String symbol = JOptionPane.showInputDialog("Übergangssymbol (auch ε möglich):");
                                    if (symbol != null && !symbol.isEmpty()) {
                                        transitions.add(new Transition(selectedStateDrawForTransition, s, symbol));
                                    }
                                    selectedStateDrawForTransition = null;
                                    repaint();
                                }
                                break;
                            }
                        }
                        break;
                }
            }
        });
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
        selectedStateDrawForTransition = null;
    }

    public void reset() {
        stateDraws.clear();
        transitions.clear();
        selectedStateDrawForTransition = null;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Transition t : transitions) {
            t.draw(g);
        }
        for (StateDraw s : stateDraws) {
            s.draw(g);
        }
    }

    public List<StateDraw> getStates() {
        return stateDraws;
    }
    public List<Transition> getTransition() { return transitions; }
    public List<Transition> getTransitions() { return transitions;}
}
