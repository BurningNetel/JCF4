package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    private List<String> getInputAsList(){
        return Arrays.asList(taInput.getText().replaceAll(",","").replaceAll("\n"," ").toLowerCase().split("\\s"));
    }

    @FXML
    private void aantalAction(ActionEvent event) {
        List<String> list = this.getInputAsList();
        list.forEach(s -> s = s.trim());
        String text;
        text = "Totaal aantal woorden: " + String.valueOf(list.size()) + "\n";

        Set<String> set = new HashSet<>(list);
        set.forEach(System.out::println);
        text += "Aantal verschillende woorden: " + set.size() + "\n";

        taOutput.setText(text);
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        List<String> list = this.getInputAsList();

        // Filter unique
        Set<String> set = new HashSet<>(list);
        list = new ArrayList<>(set);

        // Sort alphabetically
        list.sort(String::compareTo);

        // Reverse the order
        Collections.reverse(list);

        taOutput.clear();
        list.forEach(s -> taOutput.appendText(s + "\n"));
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        List<String> list = this.getInputAsList();

        Map<String, Integer> wordCountMap = new HashMap<>();

        // Put in a new key, or increment the existing value by 1 if the key exists.
        list.forEach(s -> wordCountMap.put(s, wordCountMap.containsKey(s) ? wordCountMap.get(s) + 1 : 1));

        taOutput.clear();
        // Sort the map by value using java 8 stream and print it to the output box
        wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .forEach(entry -> taOutput.appendText(entry.getKey() + ": " + entry.getValue() + "\n"));
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        List<String> words = this.getInputAsList();
        Set<String> wordsSet = new HashSet<>(words);

        List<String> rows = Arrays.asList(taInput.getText().replaceAll(",","").toLowerCase().split("\n"));

        Map<String, ArrayList<Integer>> wordLocation = new HashMap<>();

        for (String word : wordsSet) {
            int length = rows.size();

            // Loop trough each row and update the map accordingly
            for (int rowNum = 0; rowNum < length; rowNum++)
            {
                int count = rowNum + 1;

                // Adds the rows location to the array if the word is in the row
                if (rows.get(rowNum).contains(word)) {
                    if (wordLocation.get(word) != null) {
                        wordLocation.get(word).add(count);
                     } else {
                        wordLocation.put(word, new ArrayList<>(Arrays.asList(count)));
                    }
                }
            }
        }

        taOutput.clear();
        // Print to output
        wordLocation.entrySet().stream().forEach(entry -> taOutput.appendText(entry.getKey() + ": " + entry.getValue().toString() + "\n"));
    }
   
}
