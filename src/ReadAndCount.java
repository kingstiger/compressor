import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadAndCount {

    public static FileData readAndCount(String filePath, FileData fileData) {
        HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();
        int length = 0;

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                count(data, characterIntegerHashMap);
                length += data.length();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        fileData.charactersAndTheirAmount = characterIntegerHashMap;
        fileData.totalLength = length;

        return fileData;
    }

    private static void count(String data, HashMap<Character, Integer> map) {
        for(int i = 0; i < data.length(); i++)
        {
            char c = data.charAt(i);

            int count = map.getOrDefault(c, 0);
            map.put(c, count+1);
        }
    }
}
