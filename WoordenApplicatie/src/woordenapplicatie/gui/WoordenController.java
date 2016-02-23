package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n";
    
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }

    /**
     * Removes ',' and newlines, lowers and finally splits the input text into an string array.
     * @return
     */
    private String[] getInputAsArray(){
        return taInput.getText().replace(",","").replace("\n"," ").toLowerCase().split("\\s");
    }

    @FXML
    private void aantalAction(ActionEvent event) {
        String[] list = this.getInputAsArray();
        String text;

        text = "Totaal aantal woorden: " + String.valueOf(list.length) + "\n";

        Set<String> set = new HashSet<>(Arrays.asList(list));

        text += "Aantal verschillende woorden: " + set.size() + "\n";

        taOutput.setText(text);
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        String[] list = this.getInputAsArray();

        // Filter unique
        TreeSet<String> set = new TreeSet<>(Arrays.asList(list));

        taOutput.clear();
        // Reverse the order
        set.descendingSet().forEach(s -> taOutput.appendText(s + "\n"));
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        String[] list = this.getInputAsArray();

        Map<String, Integer> wordCountMap = new HashMap<>();

        // Put in a new key, or increment the existing value by 1 if the key exists
        // O(n)
        for (String s : list) {
            wordCountMap.put(s, wordCountMap.containsKey(s) ? wordCountMap.get(s) + 1 : 1);
        }

        taOutput.clear();

        // Sort the map by value using java 8 stream and print it to the output box
        // O(n log n) + O(n) = O(n + n log(n))
        wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .forEach(entry -> taOutput.appendText(entry.getKey() + ": " + entry.getValue() + "\n"));

    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        String[] words = this.getInputAsArray();

        Set<String> wordsSet = new HashSet<>(Arrays.asList(words));

        // rows
        LinkedList<String> rowList = new LinkedList<>(Arrays.asList(taInput.getText().replaceAll(",","").toLowerCase().split("\n")));

        Map<String, HashSet<Integer>> wordLocation = new HashMap<>();

        // O(n2)
        for (String word : wordsSet) {
            // Loop trough each row and update the map accordingly
            int rowcount = 0;

            for (int row = 0; row < rowList.size(); row++) {
                int count = ++rowcount;

                // Adds the rows location to the array if the word is in the row
                if (rowList.get(row).contains(word)) {
                    if (wordLocation.get(word) != null) {
                        wordLocation.get(word).add(count);
                     } else {
                        wordLocation.put(word, new HashSet<>(Arrays.asList(count)));
                    }
                }
            }
        }

        taOutput.clear();
        // Print to output
        wordLocation.entrySet().stream().forEach(entry -> taOutput.appendText(entry.getKey() + ": " + entry.getValue().toString() + "\n"));

    }
   
}
