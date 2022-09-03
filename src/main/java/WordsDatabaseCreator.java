import com.example.codenames.DAO.sqlImplementation.SqlWordDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.service.WordService;
import com.example.codenames.service.implementation.WordServiceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public class WordsDatabaseCreator {

    private static String[] CATEGORIES = {"Animals", "Food", "Physics", "Plants", "Sports", "Travel", "Other"};
    private static WordService wordService;

    public static void main(String[] args) throws FileNotFoundException {
        wordService = new WordServiceImpl(new SqlWordDAO(new DBConnection()));
        for (int i = 0; i < CATEGORIES.length; i++) {
            FileReader fileReader = new FileReader("src/main/resources/" + CATEGORIES[i] + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    if (!CATEGORIES[i].equals("Other")) {
                        if (!wordService.addWord(line.toUpperCase(), CATEGORIES[i].toUpperCase())) {
                            System.out.println(line + " was already added in " + CATEGORIES[i]);
                        }
                    }
                    if (!wordService.addWord(line.toUpperCase(), "ALL")) {
                        System.out.println(line + " was already added in ALL");
                    }
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
