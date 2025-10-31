package advanced_java.SoccerLeagueRanking;

import java.io.*;
import java.util.*;

public class SoccerLeagueRanking {
    public static void main(String[] args) {
        // Create a map to store team names and their points
        Map<String, Integer> scores = new HashMap<>();

        // ===️⃣ Locate the file automatically ===
        File file = new File("matches.txt");

        // If file not found in the same folder, show message
        if (!file.exists()) {
            System.out.println("️  File 'matches.txt' not found!");
            System.out.println("Please make sure it is in the same folder as your Java file.");
            System.out.println("Example: advanced_java/SoccerLeagueRanking/matches.txt");
            return; // stop program
        }

        // === ️⃣ Read and process matches from the file ===
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Example line: "Liverpool 3", "ManchesterUnited 3"
                String[] parts = line.split(",");
                String[] team1 = parts[0].trim().split(" ");
                String[] team2 = parts[1].trim().split(" ");

                // Extract names and scores
                String teamA = String.join(" ", Arrays.copyOf(team1, team1.length - 1));
                int scoreA = Integer.parseInt(team1[team1.length - 1]);
                String teamB = String.join(" ", Arrays.copyOf(team2, team2.length - 1));
                int scoreB = Integer.parseInt(team2[team2.length - 1]);

                // Add teams to map if they don’t exist yet
                scores.putIfAbsent(teamA, 0);
                scores.putIfAbsent(teamB, 0);

                // Assign points based on result
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
            System.out.println(" Error reading file: " + e.getMessage());
        }

        // === 3⃣ Sort the teams by points (and alphabetically if tied) ===
        List<Map.Entry<String, Integer>> sortedTeams = new ArrayList<>(scores.entrySet());
        sortedTeams.sort((a, b) -> {
            int comparePoints = b.getValue().compareTo(a.getValue());
            return (comparePoints != 0) ? comparePoints : a.getKey().compareTo(b.getKey());
        });

        // === ️⃣ Display the final league table ===
        int rank = 1;
        int previousPoints = -1;
        int displayedRank = 0;

        System.out.println("\n=== FINAL LEAGUE TABLE ===");
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

