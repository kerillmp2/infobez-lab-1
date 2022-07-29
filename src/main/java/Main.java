import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        String text = FileController.readFile("/Users/kvzosimov/code/infobez-1/src/main/resources/aphine_input.txt");
        int a = 17;
        int b = 5;

        String cipheredText = AffineCipher.cipherString(text, a, b);
        String decipheredText = AffineCipher.decipherString(cipheredText, a, b);

        FileController.writeToFile("/Users/kvzosimov/code/infobez-1/src/main/resources/aphine_ciphered_output.txt", cipheredText);
        FileController.writeToFile("/Users/kvzosimov/code/infobez-1/src/main/resources/aphine_deciphered_output.txt", decipheredText);

        String cesarText = FileController.readFile("/Users/kvzosimov/code/infobez-1/src/main/resources/cesar_input.txt");
        CesarCipher cesarCipher = CesarCipher.forKeyword("ключевоеслово", 5);
        String cipheredCesarText = cesarCipher.cipherString(cesarText);
        String decipheredCesareText = cesarCipher.decipherString(cipheredCesarText);

        FileController.writeToFile("/Users/kvzosimov/code/infobez-1/src/main/resources/cesar_output.txt", cipheredCesarText + "\n\n" + decipheredCesareText);

        Map<Character, Integer> counter = new TreeMap<>();
        Map<Character, Integer> cipherCounter = new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < cipheredText.length(); i++) {
            char c = Character.toLowerCase(cipheredText.charAt(i));
            cipherCounter.put(c, cipherCounter.getOrDefault(c, 0) + 1);
        }
        List<Stat> textStats = new ArrayList<>();
        for (Character character : counter.keySet()) {
            if ('a' <= character && 'я' >= character) {
                textStats.add(new Stat(character, (double) counter.get(character) / (double) text.length()));
            }
        }
        List<Stat> cipheredTextStats = new ArrayList<>();
        for (Character character : cipherCounter.keySet()) {
            if ('a' <= character && 'я' >= character) {
                cipheredTextStats.add(new Stat(character, (double) cipherCounter.get(character) / (double) cipheredText.length()));
            }
        }
        textStats.sort(Comparator.comparingDouble(o -> o.part));
        Collections.reverse(textStats);
        cipheredTextStats.sort(Comparator.comparingDouble(o -> o.part));
        Collections.reverse(cipheredTextStats);
        StringBuilder statsString = new StringBuilder();
        for (int i = 0; i < textStats.size(); i++) {
            if (i < cipheredTextStats.size()) {
                statsString.append(textStats.get(i).c).append(": ").append(textStats.get(i).part).append("  |  ").append(cipheredTextStats.get(i).c).append(": ").append(cipheredTextStats.get(i).part).append("\n");
            }
        }

        FileController.writeToFile("/Users/kvzosimov/code/infobez-1/src/main/resources/aphine_stats.txt", statsString.toString());
    }
}
