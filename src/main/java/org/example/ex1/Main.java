package org.example.ex1;

import org.example.Group;
import org.example.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

public class Main {

    public static void main(String[] args) {
        List<Entry> entries = readEntriesFromFile("/Users/alexandrabercu/IdeaProjects/practic_beispiel2/data/avengers.txt");

        //entries.forEach(System.out::println);

        //sortSpaceMissions(entries);

        //sortSuccessfulAvg(entries);

        Map<String, Integer> totalScores = calculateTotalScores(entries);

        saveScoresToFile(totalScores, "/Users/alexandrabercu/IdeaProjects/practic_beispiel2/data/result.txt");
    }


    public static List<Entry> readEntriesFromFile(String fileName) {
        List<Entry> entries = new ArrayList<>();
        Path filePath = Paths.get(fileName);

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                String[] parts = line.split("#");

                if (parts.length == 7) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();

                        Group group = Group.valueOf(parts[2].trim().replace(" ", "_"));
                        String description = parts[3].trim();
                        Level level = Level.valueOf(parts[4].trim().replace(" ", "_"));
                        int pointAmount = Integer.parseInt(parts[5].trim());
                        String status = parts[6].trim();


                        entries.add(new Entry(id, name, group, description,level, pointAmount,status));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            });
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return entries;
    }


    public static void sortSpaceMissions(List<Entry> entries) {
        List<String> missionNames = entries.stream()
                .filter(entry -> entry.getGroup() == Group.Space)
                .sorted(Comparator.comparing(Entry::getLevel)) //.reversed() daca e desc
                .map(Entry::getDescription)
                .toList();


        missionNames.forEach(System.out::println);
    }

    public static void sortSuccessfulAvg(List<Entry> entries) {
        List <String> avgNames = entries.stream()
                .filter(entry -> Objects.equals(entry.getStatus(), valueOf("win"))).distinct()
                .sorted(Comparator.comparing(Entry::getAvgname))
                .map(Entry::getAvgname)
                .toList();

        avgNames.forEach(System.out::println);
    }

    public static Map<String, Integer> calculateTotalScores(List<Entry> entries) {
        Map<String, Integer> totalScores = new HashMap<>();

        for (Entry entry : entries) {
            String name = entry.getAvgname();
            int points = entry.getPoints();
            String status = entry.getStatus();

            totalScores.putIfAbsent(name, 0); // Initialize score to 0 if not present

            if ("win".equalsIgnoreCase(status)) {
                totalScores.put(name, totalScores.get(name) + points);
            } else if ("fail".equalsIgnoreCase(status)) {
                totalScores.put(name, totalScores.get(name) - points);
            }
        }

        return totalScores;
    }



    public static void saveScoresToFile(Map<String, Integer> totalScores, String outputFilePath) {
        // Sort the scores in descending order
        List<String> sortedScores = totalScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> entry.getKey() + "&" + entry.getValue()) // Format as "Name&Score"
                .collect(Collectors.toList());

        // Write the sorted scores to the file
        Path outputPath = Paths.get(outputFilePath);
        try {
            Files.write(outputPath, sortedScores);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


}





