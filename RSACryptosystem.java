import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSACryptosystem {

    private BigInteger n;  // n = p * q
    private BigInteger e;  // Public exponent
    private BigInteger d;  // Private exponent
    private BigInteger phi;  // Euler's totient function φ(n)
    private int bitLength = 1024;  // Size of p and q (in bits)
    private SecureRandom random;

    // Constructor to generate public and private keys
    public RSACryptosystem() {
        random = new SecureRandom();
        // Step 1: Generate two distinct prime numbers p and q
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);
        // Step 2: Compute n = p * q
        n = p.multiply(q);

        // Step 3: Compute φ(n) = (p-1)(q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Step 4: Choose public exponent e such that 1 < e < φ(n) and gcd(e, φ(n)) = 1
        e = new BigInteger("65537");  // Common choice for e

        // Step 5: Compute private exponent d such that d ≡ e^(-1) mod φ(n)
        d = e.modInverse(phi);
    }

    // Encryption: c = m^e mod n
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Decryption: m = c^d mod n
    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    // Getters for the public and private key components
    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    // Main method to test the RSA implementation
    public static void main(String[] args) {
        RSACryptosystem rsa = new RSACryptosystem();
        Scanner scanner = new Scanner(System.in);

        // Public key (e, n)
        System.out.println("Public Key (e, n): (" + rsa.getE() + ", " + rsa.getN() + ")");

        // Private key (d, n)
        System.out.println("Private Key (d, n): (" + rsa.getD() + ", " + rsa.getN() + ")");

        // Enter a message to encrypt
        System.out.println("Enter a message to encrypt (as a number): ");
        BigInteger message = new BigInteger(scanner.nextLine());

        // Encrypt the message using the public key
        BigInteger ciphertext = rsa.encrypt(message);
        System.out.println("Encrypted message: " + ciphertext);

        // Decrypt the ciphertext using the private key
        BigInteger decryptedMessage = rsa.decrypt(ciphertext);
        System.out.println("Decrypted message: " + decryptedMessage);

        // Close the scanner
        scanner.close();
    }
}


