import java.math.BigDecimal;
import java.util.HashMap;

public class FileData {
    public String fileName;
    public HashMap<Character, Integer> charactersAndTheirAmount = new HashMap<>();
    public Integer totalLength;
    public HashMap<Character, Double> charactersAndTheirFrequency = new HashMap<>();
    public HashMap<Character, Double> charactersAndTheirAmountOfInformation = new HashMap<>();
    public Double entropy = 0.0d;


    public FileData(String fileName) {
        this.fileName = fileName;
    }

    public FileData printData() {
        System.out.println("****************");
        System.out.println("File: " + fileName);
        System.out.println("****************");

        charactersAndTheirAmount.forEach((key, value) -> System.out.print("Character: " + key + " Amount: " + value + ";\n"));

        System.out.println("****************");

        charactersAndTheirFrequency.forEach((key, value) -> System.out.print("Character: " + key + " Frequency: " + new BigDecimal(value).toPlainString() + ";\n"));

        System.out.println("****************");

        charactersAndTheirAmountOfInformation.forEach((key, value) -> System.out.print("Character: " + key + " Amount of Information: " + new BigDecimal(value).toPlainString() + ";\n"));

        System.out.println("****************");
        System.out.println("Entropy: " + entropy);
        System.out.println("****************");

        return this;
    }

    public FileData calculateFrequencyAndEntropyAndInformationAmount() {
        charactersAndTheirAmount.forEach((key, value) -> {
            double frequency = (double) value / totalLength;
            charactersAndTheirFrequency.put(key, frequency);
            entropy -= frequency * log2(frequency);
            charactersAndTheirAmountOfInformation.put(key, Math.log((double) 1 / frequency));
        });
        return this;
    }

    private Double log2(Double N) {
        return Math.log(N) / Math.log(2);
    }

    public FileData compare(FileData fileData) {
        System.out.println("*************");
        charactersAndTheirAmount.forEach((key, value) -> {
            Integer secondAmount = fileData.charactersAndTheirAmount.getOrDefault(key, 0);
            System.out.printf("File %s contains %d of %c character, file %s: %d\n",
                    fileName, value, key, fileData.fileName, secondAmount);
        });

        System.out.println("***");
        charactersAndTheirFrequency.forEach((key, value) -> {
            Double secondAmount = fileData.charactersAndTheirFrequency.getOrDefault(key, 0.0d);
            System.out.printf("File %s frequency %c character is %.4f, file %s: %.4f\n",
                    fileName, key, value, fileData.fileName, secondAmount);
        });

        System.out.println("***");
        charactersAndTheirAmountOfInformation.forEach((key, value) -> {
            Double secondAmount = fileData.charactersAndTheirAmountOfInformation.getOrDefault(key, 0.0d);
            System.out.printf("File %s amount of information in %c character is %.4f, file %s: %.4f\n",
                    fileName, key, value, fileData.fileName, secondAmount);
        });

        System.out.println("***");
        System.out.printf("In file %s entropy is equal to %.4f, in file %s: %.4f\n",
                fileName, entropy, fileData.fileName, fileData.entropy);

        System.out.println("*************");
        return this;
    }
}
