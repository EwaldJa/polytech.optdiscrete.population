package display;

import graph.Node;
import graph.Solution;
import utils.ColorPicker;
import utils.ForEachWrapper;
import utils.FormatUtils;

import javax.swing.*;
import java.awt.*;

/**
 * This class displays the result
 *
 * @author Lucas Aupoil, Ewald Janin
 */
public class DisplayResult extends JFrame {

    public static final int CANVAS_WIDTH  = 1280;
    public static final int CANVAS_HEIGHT = 720;

    public static final int INFOPANEL_WIDTH  = 400;
    public static final int INFOLINE_HEIGHT  = 40;

    public static final int DEPOSIT_DOT_DIAMETER = 20;

    private DrawCanvas _canvas;
    private Solution _solution;

    // Constructor to set up the GUI components and event handlers
    public DisplayResult(Solution solution) {

        _solution = solution;
        _canvas = new DrawCanvas();    // Construct the drawing _canvas
        _canvas.setPreferredSize(new Dimension(CANVAS_WIDTH+INFOPANEL_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(_canvas);
        // or "setContentPane(_canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Result");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show
    }

    /**
     * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
     */
    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background

            ColorPicker.initColors();
            // Your custom painting codes. For example,
            // Drawing primitive shapes
            ForEachWrapper<Integer> dtNb = new ForEachWrapper<>(0);
            _solution.getDeliveryTours().parallelStream().forEachOrdered(tour -> {
                g.setColor(ColorPicker.getRandomColor());
                g.setFont(new Font("Baskerville Old Face", Font.PLAIN, 30));
                g.drawString("#" + dtNb.value + ", cap:"+tour.remainingSpace() + ", dist:"+ FormatUtils.round(tour.getTotalDistance(),2), CANVAS_WIDTH + INFOLINE_HEIGHT/2, (dtNb.value + 1) * INFOLINE_HEIGHT + INFOLINE_HEIGHT/2);
                dtNb.value++;
                        tour.getEdges().forEach(
                                edge -> {

                        g.drawLine(convertPosX(edge.getN1().getXPos()), convertPosY(edge.getN1().getYPos()), convertPosX(edge.getN2().getXPos()), convertPosY(edge.getN2().getYPos()) );

                        Node node = edge.getN1();
                        int width = node.getOrder() + 3;
                        g.fillOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);

                        node = edge.getN2();
                        width = node.getOrder() + 3;
                        g.fillOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);
                    });
                }
            );

            Node deposit = _solution.getDeposit();
            g.setColor(Color.BLACK);
            g.drawLine(CANVAS_WIDTH + INFOLINE_HEIGHT/4, 0, CANVAS_WIDTH + INFOLINE_HEIGHT/4, CANVAS_HEIGHT);
            g.fillRect(convertPosX(deposit.getXPos()) - DEPOSIT_DOT_DIAMETER/2, convertPosY(deposit.getYPos()) - DEPOSIT_DOT_DIAMETER/2, DEPOSIT_DOT_DIAMETER, DEPOSIT_DOT_DIAMETER);
            g.setFont(new Font("Baskerville Old Face", Font.PLAIN, 25));
            g.drawString("Deposit", CANVAS_WIDTH + DEPOSIT_DOT_DIAMETER + INFOLINE_HEIGHT, (dtNb.value + 1) * INFOLINE_HEIGHT + INFOLINE_HEIGHT/2);
            g.fillRect(CANVAS_WIDTH + INFOLINE_HEIGHT/2, (dtNb.value+1) * INFOLINE_HEIGHT, DEPOSIT_DOT_DIAMETER, DEPOSIT_DOT_DIAMETER);
            g.setFont(new Font("Baskerville Old Face", Font.PLAIN, 30));
            g.drawString("Total dist: "+FormatUtils.round(_solution.getTotalDistance(),2), CANVAS_WIDTH + INFOLINE_HEIGHT/2, (dtNb.value + 3) * INFOLINE_HEIGHT + INFOLINE_HEIGHT/2);

        }

    }

    private int convertPosX(int posX){
        return (int) (((double) posX / 100.0) * (double) CANVAS_WIDTH);
    }

    private int convertPosY(int posY){
        return (int) (((double) posY / 100.0) * (double) CANVAS_HEIGHT);
    }
}
