import data.DataLoader;
import display.DisplayResult;
import genetics.GeneticAlgo;
import graph.Solution;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {


        Solution s3305 = DataLoader.read("3305");
        System.out.println(s3305.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s3305);  // Let the constructor do the job
            }
        });

        Solution geneticAlgo = new GeneticAlgo(s3305, 20, 300000, 0.20, false).process();
        System.out.println(geneticAlgo.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(geneticAlgo.clone().finaliserSolution()); // Let the constructor do the job
            }
        });

    }

}
