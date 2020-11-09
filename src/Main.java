public class Main {
    public static void main(String[] args) {
        String english1 = "E:\\Java projects\\compressor\\resources\\v1.txt";
        String english2 = "E:\\Java projects\\compressor\\resources\\v2.txt";
        String polish = "E:\\Java projects\\compressor\\resources\\pl1.txt";
        String polishIT = "E:\\Java projects\\compressor\\resources\\pl IT.txt";

        FileData eng1 = new FileData("eng1");
        FileData eng2 = new FileData("eng2");
        FileData pl = new FileData("pl");
        FileData plIT = new FileData("plIT");

        eng1 = ReadAndCount.readAndCount(english1, eng1)
                .calculateFrequencyAndEntropyAndInformationAmount()
                .printData();
        eng2 = ReadAndCount.readAndCount(english2, eng2)
                .calculateFrequencyAndEntropyAndInformationAmount()
                .printData();
        pl = ReadAndCount.readAndCount(polish, pl)
                .calculateFrequencyAndEntropyAndInformationAmount()
                .printData();
        plIT = ReadAndCount.readAndCount(polishIT, plIT)
                .calculateFrequencyAndEntropyAndInformationAmount()
                .printData();

        eng1.compare(eng2).compare(pl).compare(plIT);
        eng2.compare(pl).compare(plIT);
        pl.compare(plIT);
    }
}
