package evtlSpaeterNutzbar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NEAGui extends JFrame {
    private static DrawingPanelR drawingPanelR;
    static List<StateDraw> states;

    public NEAGui() {
        setTitle("NEA Zeichner");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        drawingPanelR = new DrawingPanelR();
        add(drawingPanelR, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addStateBtn = new JButton("Zustand hinzufügen");
        JButton addTransitionBtn = new JButton("Übergang hinzufügen");
        JButton resetBtn = new JButton("Zurücksetzen");

        controlPanel.add(addStateBtn);
        controlPanel.add(addTransitionBtn);
        controlPanel.add(resetBtn);
        add(controlPanel, BorderLayout.SOUTH);

        addStateBtn.addActionListener(e -> drawingPanelR.setMode(DrawingPanelR.Mode.ADD_STATE));
        addTransitionBtn.addActionListener(e -> drawingPanelR.setMode(DrawingPanelR.Mode.ADD_TRANSITION));
        resetBtn.addActionListener(e -> drawingPanelR.reset());

        states = drawingPanelR.getStates();

        setVisible(true);
    }


    public static List<StateDraw> main() {
        SwingUtilities.invokeLater(NEAGui::new);
        return states;
    }

}

