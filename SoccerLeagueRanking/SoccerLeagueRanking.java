package advanced_java.SoccerLeagueRanking;

import java.io.*;
import java.util.*;

public class SoccerLeagueRanking {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("matches.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Example line: "Liverpool 3, ManchesterUnited 3"
                String[] parts = line.split(",");
                String[] team1 = parts[0].trim().split(" ");
                String[] team2 = parts[1].trim().split(" ");

                // Get names and scores
                String teamA = String.join(" ", Arrays.copyOf(team1, team1.length - 1));
                int scoreA = Integer.parseInt(team1[team1.length - 1]);
                String teamB = String.join(" ", Arrays.copyOf(team2, team2.length - 1));
                int scoreB = Integer.parseInt(team2[team2.length - 1]);

                // Initialize teams if not exist
                scores.putIfAbsent(teamA, 0);
                scores.putIfAbsent(teamB, 0);

                // Update points
                if (scoreA > scoreB) {
                    scores.put(teamA, scores.get(teamA) + 3);
                } else if (scoreB > scoreA) {
                    scores.put(teamB, scores.get(teamB) + 3);
                } else {
                    scores.put(teamA, scores.get(teamA) + 1);
                    scores.put(teamB, scores.get(teamB) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Sort teams
        List<Map.Entry<String, Integer>> sortedTeams = new ArrayList<>(scores.entrySet());
        sortedTeams.sort((a, b) -> {
            int comparePoints = b.getValue().compareTo(a.getValue());
            return (comparePoints != 0) ? comparePoints : a.getKey().compareTo(b.getKey());
        });

        // Display ranking
        int rank = 1;
        int previousPoints = -1;
        int displayedRank = 0;

        System.out.println("=== FINAL LEAGUE TABLE ===");
        for (Map.Entry<String, Integer> entry : sortedTeams) {
            if (entry.getValue() != previousPoints) {
                displayedRank = rank;
            }
            System.out.printf("%d. %s %d%n", displayedRank, entry.getKey(), entry.getValue());
            previousPoints = entry.getValue();
            rank++;
        }
    }
}

