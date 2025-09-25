package evtlSpaeterNutzbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class NEAGui extends JFrame {
    private DrawingPanel drawingPanel;

    public NEAGui() {
        setTitle("NEA Zeichner");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addStateBtn = new JButton("Zustand hinzufügen");
        JButton addTransitionBtn = new JButton("Übergang hinzufügen");
        JButton resetBtn = new JButton("Zurücksetzen");

        controlPanel.add(addStateBtn);
        controlPanel.add(addTransitionBtn);
        controlPanel.add(resetBtn);
        add(controlPanel, BorderLayout.SOUTH);

        addStateBtn.addActionListener(e -> drawingPanel.setMode(DrawingPanel.Mode.ADD_STATE));
        addTransitionBtn.addActionListener(e -> drawingPanel.setMode(DrawingPanel.Mode.ADD_TRANSITION));
        resetBtn.addActionListener(e -> drawingPanel.reset());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NEAGui::new);
    }
}

class DrawingPanel extends JPanel {
    enum Mode { NONE, ADD_STATE, ADD_TRANSITION }

    private Mode currentMode = Mode.NONE;
    private List<StateDraw> stateDraws = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private StateDraw selectedStateDrawForTransition = null;

    public DrawingPanel() {
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
}

class StateDraw {
    private static final int RADIUS = 30;
    String name;
    int x, y;
    boolean isStart, isFinal;

    public StateDraw(String name, int x, int y, boolean isStart, boolean isFinal) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isStart = isStart;
        this.isFinal = isFinal;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        g.drawString(name, x - 10, y + 5);

        if (isFinal) {
            g.drawOval(x - RADIUS + 5, y - RADIUS + 5, 2 * (RADIUS - 5), 2 * (RADIUS - 5));
        }

        if (isStart) {
            g.drawLine(x - RADIUS - 20, y, x - RADIUS, y);
            g.drawLine(x - RADIUS - 10, y - 5, x - RADIUS, y);
            g.drawLine(x - RADIUS - 10, y + 5, x - RADIUS, y);
        }
    }

    public boolean contains(Point p) {
        return (Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2)) <= Math.pow(RADIUS, 2);
    }
}

class Transition {
    StateDraw from, to;
    String symbol;

    public Transition(StateDraw from, StateDraw to, String symbol) {
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        int x1 = from.x;
        int y1 = from.y;
        int x2 = to.x;
        int y2 = to.y;

        if (from == to) {
            // Loop
            g.drawArc(x1 - 30, y1 - 50, 60, 40, 0, 360);
            g.drawString(symbol, x1, y1 - 60);
        } else {
            g.drawLine(x1, y1, x2, y2);
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g.drawString(symbol, midX + 5, midY - 5);

            // Arrowhead
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowSize = 8;
            int dx = (int) (arrowSize * Math.cos(angle - Math.PI / 6));
            int dy = (int) (arrowSize * Math.sin(angle - Math.PI / 6));
            g.drawLine(x2, y2, x2 - dx, y2 - dy);
            dx = (int) (arrowSize * Math.cos(angle + Math.PI / 6));
            dy = (int) (arrowSize * Math.sin(angle + Math.PI / 6));
            g.drawLine(x2, y2, x2 - dx, y2 - dy);
        }
    }
}