import data.DataLoader;
import display.DisplayResult;
import genetics.GeneticAlgo;
import graph.Solution;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {


        Solution readFromFile = DataLoader.read("8010");
        System.out.println(readFromFile.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(readFromFile);  // Let the constructor do the job
            }
        });

        Solution geneticAlgo = new GeneticAlgo(readFromFile, 20, 500, 0.20, 20, 25, 5).process();
        System.out.println(geneticAlgo.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(geneticAlgo.clone().finaliserSolution()); // Let the constructor do the job
            }
        });

    }

}
