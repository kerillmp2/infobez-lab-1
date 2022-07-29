public class AffineCipher {

    // Шифрование строки Аффинным шифром
    public static String cipherString(String s, int a, int b) {
        StringBuilder cipheredString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                cipheredString.append(c);
                i++;
                cipheredString.append(s.charAt(i));
                continue;
            }
            cipheredString.append(cipherChar(c, a, b));
        }
        return cipheredString.toString();
    }

    // Дешифрование строки Аффинным шифром
    public static String decipherString(String s, int a, int b) {
        StringBuilder decipheredString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                decipheredString.append(c);
                i++;
                decipheredString.append(s.charAt(i));
                continue;
            }
            decipheredString.append(decipherChar(c, a, b));
        }
        return decipheredString.toString();
    }

    // Шифрование одного символа Аффинным шифром
    public static char cipherChar(char c, int a, int b) {
        int alphabetSize = 0;
        int begin = 0;
        if ('A' <= c && c <= 'Z') {
            alphabetSize = 26;
            begin = 'A';
        }
        if ('a' <= c && c <= 'z') {
            alphabetSize = 26;
            begin = 'a';
        }
        if ('А' <= c && c <= 'Я') {
            alphabetSize = 32;
            begin = 'А';
        }
        if ('а' <= c && c <= 'я') {
            alphabetSize = 32;
            begin = 'а';
        }
        if (alphabetSize == 0) {
            return c;
        }
        if (Utils.gcd(a, alphabetSize) != 1) {
            throw new IllegalArgumentException("Greatest Common Divisor of 'a' and 'alphabet size' must be 1");
        }
        return (char) ((a * (c - begin) + b) % alphabetSize + begin);
    }

    // Дешифрование одного символа Аффинным шифром
    public static char decipherChar(char c, int a, int b) {
        int alphabetSize = 0;
        int begin = 0;
        if ('A' <= c && c <= 'Z') {
            alphabetSize = 26;
            begin = 'A';
        }
        if ('a' <= c && c <= 'z') {
            alphabetSize = 26;
            begin = 'a';
        }
        if ('А' <= c && c <= 'Я') {
            alphabetSize = 32;
            begin = 'А';
        }
        if ('а' <= c && c <= 'я') {
            alphabetSize = 32;
            begin = 'а';
        }
        if (alphabetSize == 0) {
            return c;
        }
        if (Utils.gcd(a, alphabetSize) != 1) {
            throw new IllegalArgumentException("Greatest Common Divisor of 'a' and 'alphabet size' must be 1");
        }
        int decipher = findDecipher(a, alphabetSize);
        if (decipher == -1) {
            throw new IllegalArgumentException("Greatest Common Divisor of 'a' and 'alphabet size' must be 1");
        }
        return (char) (((decipher * (c - begin - b)) + alphabetSize * decipher * 2) % alphabetSize + begin);
    }

    // Поиск числа для дешифрования Аффинного шифра
    private static int findDecipher(int a, int alphabetSize) {
        for (int i = 0; i < alphabetSize; i++) {
            if ((a * i) % alphabetSize == 1) {
                return i;
            }
        }
        return -1;
    }
}
