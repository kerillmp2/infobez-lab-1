import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CesarCipher {

    Map<Character, Character> cipher;
    Map<Character, Character> decipher;

    private CesarCipher(Map<Character, Character> cipher, Map<Character, Character> decipher) {
        this.cipher = cipher;
        this.decipher = decipher;
    }

    public static CesarCipher forKeyword(String word, int a) {
        StringBuilder keywordBuilder = new StringBuilder();
        word.chars().distinct().forEach(c -> keywordBuilder.append((char) c));
        String keyword = keywordBuilder.toString();
        Map<Character, Character> replaces = new HashMap<>();
        Map<Character, Character> decipher = new HashMap<>();
        Set<Character> usedLetters = new HashSet<>();
        int alphabetSize = 0;
        int newAlphabetSize = 0;
        char begin = 0;

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            if ('A' <= c && c <= 'Z') {
                newAlphabetSize = 26;
                begin = 'A';
                usedLetters.add(c);
            }
            if ('a' <= c && c <= 'z') {
                newAlphabetSize = 26;
                begin = 'a';
                usedLetters.add(c);
            }
            if ('А' <= c && c <= 'Я') {
                newAlphabetSize = 32;
                begin = 'А';
                usedLetters.add(c);
            }
            if ('а' <= c && c <= 'я') {
                newAlphabetSize = 32;
                begin = 'а';
                usedLetters.add(c);
            }
            if (newAlphabetSize == 0) {
                continue;
            }
            if (alphabetSize == 0) {
                alphabetSize = newAlphabetSize;
            }
            if (newAlphabetSize != alphabetSize) {
                throw new IllegalArgumentException("Keyword must be only on one language and same case");
            }
        }

        a = a % alphabetSize;

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            char replaced = (char) (begin + ((i + a) % alphabetSize));
            replaces.put(replaced, c);
        }
        char pointer = (char) (begin + ((keyword.length() + a) % alphabetSize));
        char alphabetCounter = begin;
        while (pointer != begin + a) {
            while (usedLetters.contains(alphabetCounter)) {
                alphabetCounter++;
            }
            replaces.put(pointer, alphabetCounter);
            usedLetters.add(alphabetCounter);
            pointer++;
            if (pointer >= begin + alphabetSize) {
                pointer = begin;
            }
        }

        for (Map.Entry<Character, Character> replace : replaces.entrySet()) {
            decipher.put(replace.getValue(), replace.getKey());
        }

        return new CesarCipher(replaces, decipher);
    }

    public String cipherString(String s) {
        StringBuilder cipheredString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                cipheredString.append(c);
                i++;
                cipheredString.append(s.charAt(i));
                continue;
            }
            cipheredString.append(this.cipherChar(c));
        }
        return cipheredString.toString();
    }

    public String decipherString(String s) {
        StringBuilder decipheredString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                decipheredString.append(c);
                i++;
                decipheredString.append(s.charAt(i));
                continue;
            }
            decipheredString.append(this.decipherChar(c));
        }
        return decipheredString.toString();
    }

    private char cipherChar(char c) {
        return this.cipher.getOrDefault(c, c);
    }

    private char decipherChar(char c) {
        return this.decipher.getOrDefault(c, c);
    }
}
