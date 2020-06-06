package data;

import graph.Solution;
import graph.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class loads a dataset
 *
 * @author Lucas Aupoil, Ewald Janin
 */
public class DataLoader {

    public static Solution read(String num_file) {
        ArrayList<Node> clients = new ArrayList<Node>();
        String line = ""; //for the input line
        BufferedReader br = null;
        File data;
        data = new File("data/A" + num_file + ".txt");
        try {
            br = new BufferedReader(new FileReader(data));
        } catch (Exception e) {
            e.printStackTrace(); }
        Node deposit = null;
        try {
            br.readLine(); //Ommit headers
            line = br.readLine();//Deposit
            String[] inputDeposit = line.split(";");
            deposit = new Node(Integer.valueOf(inputDeposit[0]), Integer.valueOf(inputDeposit[1]), Integer.valueOf(inputDeposit[2]), Integer.valueOf(inputDeposit[3]));

            line = br.readLine();
            while (line != null){
                String[] input = line.split(";");
                clients.add(new Node(Integer.valueOf(input[0]), Integer.valueOf(input[1]), Integer.valueOf(input[2]), Integer.valueOf(input[3])));
                line = br.readLine();
            }
            br.close(); //closing input flow
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("file : A" + num_file + ".txt");
        return new Solution(deposit, clients);
    }

}
