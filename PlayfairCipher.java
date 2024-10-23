import java.util.Scanner;

public class PlayfairCipher {

    private String key;
    private char[][] matrix;

    // Constructor to initialize the key and generate the matrix
    public PlayfairCipher(String key) {
        this.key = prepareKey(key);
        this.matrix = new char[5][5];
        generateMatrix();
    }

    // Prepare the key by removing duplicates and merging it with the alphabet
    private String prepareKey(String key) {
        key = key.toLowerCase().replaceAll("[^a-z]", "").replace('j', 'i');
        StringBuilder newKey = new StringBuilder();
        boolean[] used = new boolean[26];
        for (char c : key.toCharArray()) {
            if (!used[c - 'a']) {
                newKey.append(c);
                used[c - 'a'] = true;
            }
        }

        // Append remaining letters to the key
        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue; // Skip 'j'
            if (!used[c - 'a']) {
                newKey.append(c);
                used[c - 'a'] = true;
            }
        }

        return newKey.toString();
    }

    // Generate the 5x5 Playfair cipher matrix
    private void generateMatrix() {
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = key.charAt(index++);
            }
        }
    }

    // Split the plaintext or ciphertext into digraphs (pairs of letters)
    private String[] createDigraphs(String input) {
        input = input.toLowerCase().replaceAll("[^a-z]", "").replace('j', 'i');
        StringBuilder sb = new StringBuilder(input);

        // Insert 'x' between repeated letters in a pair for encryption
        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'x');
            }
        }

        // If the length is odd, add 'x' at the end
        if (sb.length() % 2 != 0) {
            sb.append('x');
        }

        String[] digraphs = new String[sb.length() / 2];
        for (int i = 0; i < sb.length(); i += 2) {
            digraphs[i / 2] = sb.substring(i, i + 2);
        }

        return digraphs;
    }

    // Find the position of a letter in the matrix
    private int[] findPosition(char letter) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Encrypt a single digraph
    private String encryptDigraph(String digraph) {
        char first = digraph.charAt(0);
        char second = digraph.charAt(1);
        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);

        if (firstPos[0] == secondPos[0]) {
            // Same row: Shift right
            return "" + matrix[firstPos[0]][(firstPos[1] + 1) % 5] + matrix[secondPos[0]][(secondPos[1] + 1) % 5];
        } else if (firstPos[1] == secondPos[1]) {
            // Same column: Shift down
            return "" + matrix[(firstPos[0] + 1) % 5][firstPos[1]] + matrix[(secondPos[0] + 1) % 5][secondPos[1]];
        } else {
            // Rectangle: Swap the columns
            return "" + matrix[firstPos[0]][secondPos[1]] + matrix[secondPos[0]][firstPos[1]];
        }
    }

    // Decrypt a single digraph
    private String decryptDigraph(String digraph) {
        char first = digraph.charAt(0);
        char second = digraph.charAt(1);
        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);

        if (firstPos[0] == secondPos[0]) {
            // Same row: Shift left
            return "" + matrix[firstPos[0]][(firstPos[1] + 4) % 5] + matrix[secondPos[0]][(secondPos[1] + 4) % 5];
        } else if (firstPos[1] == secondPos[1]) {
            // Same column: Shift up
            return "" + matrix[(firstPos[0] + 4) % 5][firstPos[1]] + matrix[(secondPos[0] + 4) % 5][secondPos[1]];
        } else {
            // Rectangle: Swap the columns
            return "" + matrix[firstPos[0]][secondPos[1]] + matrix[secondPos[0]][firstPos[1]];
        }
    }

    // Encrypt the plaintext using the Playfair cipher
    public String encrypt(String plaintext) {
        String[] digraphs = createDigraphs(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (String digraph : digraphs) {
            ciphertext.append(encryptDigraph(digraph));
        }

        return ciphertext.toString().toUpperCase();
    }

    // Decrypt the ciphertext using the Playfair cipher
    public String decrypt(String ciphertext) {
        String[] digraphs = createDigraphs(ciphertext);
        StringBuilder plaintext = new StringBuilder();

        for (String digraph : digraphs) {
            plaintext.append(decryptDigraph(digraph));
        }

        return plaintext.toString().toLowerCase();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the key input from the user
        System.out.println("Enter the key:");
        String key = scanner.nextLine();

        PlayfairCipher cipher = new PlayfairCipher(key);

        // Encrypt or decrypt based on user choice
        System.out.println("Enter 1 to encrypt or 2 to decrypt:");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        if (choice == 1) {
            // Get the plaintext and encrypt it
            System.out.println("Enter the plaintext:");
            String plaintext = scanner.nextLine();
            String ciphertext = cipher.encrypt(plaintext);
            System.out.println("Encrypted text: " + ciphertext);
        } else if (choice == 2) {
            // Get the ciphertext and decrypt it
            System.out.println("Enter the ciphertext:");
            String ciphertext = scanner.nextLine();
            String plaintext = cipher.decrypt(ciphertext);
            System.out.println("Decrypted text: " + plaintext);
        } else {
            System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}
