/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;

/**
 *
 * @author foppe
 */
public class FileReader {

    String line = "";
    ArrayList<Rule> list = new ArrayList();
    ArrayList<Statement> statements = new ArrayList<Statement>();
    String path;
    //int i = 0;
    
    public FileReader(String path){
        this.path = path;
    }
    
    

    public ArrayList<Rule> main() {
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int count = fileInformation(br.readLine());
            makeStatementList(count);
            while ((line = br.readLine()) != null) {
                String[] gegeven = line.split("0");
                String test = gegeven[0];
                Rule rule = new Rule(test);
                if (!rule.checkTaut()) {
                    list.add(rule);
                }
            }
        } catch (Exception e) {
        }

        return list;
    }

    
    private int fileInformation(String information) {
        String testje = information.substring(6, information.length());
        //int count;
        String lijn = "";
        for (int i = 0; i < testje.length(); i++) {
            //System.out.println(testje.substring(i, i + 1));
            if (!testje.substring(i, i + 1).equals(" ")) {
                lijn = lijn + testje.substring(i, i + 1);
            } else {

                return Integer.parseInt(lijn);
            }
        }
        return 0;

    }

    private void makeStatementList(int count) {
        for (int i = 1; i < count - 110; i++) {
            if (((i + 110) % 10 != 0) && ((((i + 110)/10) % 10) != 0)) {
                statements.add(new Statement(i + 110));
            }
        }
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

}
