package Chap12.Page403;

import java.util.*;
import java.util.stream.Collectors;

public class JukeboxStreams {
    public static void main(String[] args) {
        // Get the list of all songs
        List<Song> songs = new Songs().getSongs();

        // Filter songs with genre "Rock"
        List<Song> rockSongs = songs.stream()
                .filter(song -> song.getGenre().equals("Rock"))
                .collect(Collectors.toList());

        // Print the filtered list
        System.out.println(rockSongs);
    }
}
