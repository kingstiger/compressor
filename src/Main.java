import huffman.HNode;
import huffman.Utils;

import java.io.*;
import java.util.BitSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String english1 = "resources/v1.txt";
        String english2 = "resources/v2.txt";
        String polish = "resources/pl1.txt";
        String polishIT = "resources/pl IT.txt";

        compress(english1, "resources/out1.txt");
        decompress("resources/out1.txt", "resources/out2.txt");
    }

    private static void decompress(String inputFilePath, String outputFilePath)
    {
        byte[] bytes = readBytes(inputFilePath);
        String text = Utils.unhuffHuffman(bytes);

    }

    private static void compress(String inputFilePath, String outputFilePath) {
        HNode root = new HNode();
        String inputData = read(inputFilePath);

        root = Utils.makeList(root, inputData);
        root = Utils.makeTree(root);
        Utils.printTree(root, "");
        Utils.codeHuffman(root, inputData);
        write(outputFilePath, Utils.encodedBits);
    }

    public static String read(String path) {
        String data = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public static byte[] readBytes(String path) {
        byte[] data = null;
        try {
            File myObj = new File(path);
            InputStream stream = new FileInputStream(myObj);
            data = stream.readAllBytes();
            stream.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public static void write(String path, BitSet data) {
        byte[] bytes = data.toByteArray();
        try {
            boolean newFile;
            File myObj = new File(path);
            if(!myObj.exists()) {
                newFile = myObj.createNewFile();
            } else {
                boolean delete = myObj.delete();
                newFile = myObj.createNewFile() && delete;
            }
            if(newFile)
            {
                OutputStream outputStream = new FileOutputStream(myObj);
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
