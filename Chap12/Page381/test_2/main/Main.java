package advanced_java.Chap12.Page381.test_2.main;



import java.util.List;

public class Main {
    public static void main(String[] args) {
        Songs songsData = new Songs();
        List<Song> songs = songsData.getSongs();

        for (Song song : songs) {
            System.out.println(song);
        }
    }
}
