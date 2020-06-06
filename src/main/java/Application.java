import algo.SimulatedAnnealing;
import algo.TabuSearch;
import data.DataLoader;
import display.DisplayResult;
import graph.Solution;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
/*
        Solution s3205 = DataLoader.read("3205");
        System.out.println(s3205.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s3205);  // Let the constructor do the job
            }
        });
        Solution bestg3205 = new TabuSearch(3000, 150, 200).processCurrent(s3205);
        System.out.println(bestg3205.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bestg3205.finaliserSolution()); // Let the constructor do the job
            }
        });
*/


        Solution s3305 = DataLoader.read("3305");
        System.out.println(s3305.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s3305);  // Let the constructor do the job
            }
        });

        Solution tabus3305 = new TabuSearch(6000, 150, 500).processCurrent(s3305.clone());
        System.out.println(tabus3305.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(tabus3305.clone().finaliserSolution()); // Let the constructor do the job
            }
        });

        Solution simulatedannealings3305 = new SimulatedAnnealing(500, 0.99, 2000).processCurrent(s3305.clone());
        System.out.println(simulatedannealings3305.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(simulatedannealings3305.finaliserSolution()); // Let the constructor do the job
            }
        });

/*
        Solution s3306 = DataLoader.read("3306");
        System.out.println(s3306.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s3306);  // Let the constructor do the job
            }
        });
        Solution bests3306 = new TabuSearch(3000, 150, 200).processCurrent(s3306);
        System.out.println(bests3306.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bests3306.finaliserSolution()); // Let the constructor do the job
            }
        });


        Solution s3405 = DataLoader.read("3405");
        System.out.println(s3405.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s3405);  // Let the constructor do the job
            }
        });
        Solution bests3405 = new TabuSearch(3000, 150, 200).processCurrent(s3405);
        System.out.println(bests3405.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bests3405.finaliserSolution()); // Let the constructor do the job
            }
        });


        Solution s4406 = DataLoader.read("4406");
        System.out.println(s4406.toString());SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s4406);  // Let the constructor do the job
            }
        });
        Solution bests4406 = new TabuSearch(3000, 150, 200).processCurrent(s4406);
        System.out.println(bests4406.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bests4406.finaliserSolution()); // Let the constructor do the job
            }
        });


        Solution s8010 = DataLoader.read("8010");
        System.out.println(s8010.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(s8010);  // Let the constructor do the job
            }
        });
        Solution bests8010 = new TabuSearch(3000, 150, 200).processCurrent(s8010);
        System.out.println(bests8010.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bests8010.finaliserSolution()); // Let the constructor do the job
            }
        });

*/



    }

}
