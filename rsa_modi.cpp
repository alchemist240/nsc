#include <iostream>
#include <cmath>

using namespace std;

// Function to compute GCD of two numbers
int gcd(int a, int h) {
    int temp;
    while (1) {
        temp = a % h;
        if (temp == 0)
            return h;
        a = h;
        h = temp;
    }
}

// Function to perform modular exponentiation
// It returns (base^exp) % mod
long long modExp(long long base, long long exp, long long mod) {
    long long result = 1;
    base = base % mod;  // Ensure base is smaller than mod
    while (exp > 0) {
        if (exp % 2 == 1)  // If exp is odd, multiply base with result
            result = (result * base) % mod;
        exp = exp >> 1;    // exp = exp / 2
        base = (base * base) % mod;  // base = base^2 % mod
    }
    return result;
}

// Function to find modular inverse of e under modulo phi using Extended Euclidean Algorithm
long long modInverse(long long e, long long phi) {
    long long t = 0, new_t = 1;
    long long r = phi, new_r = e;
    while (new_r != 0) {
        long long quotient = r / new_r;
        t = t - quotient * new_t;
        if (t < 0)
            t += phi;
        long long temp_t = t;
        t = new_t;
        new_t = temp_t;

        long long temp_r = r;
        r = new_r;
        new_r = temp_r % new_r;
    }
    if (r > 1) return -1;  // e is not invertible
    return t;
}

// Code to demonstrate RSA algorithm
int main() {
    // Two prime numbers
    long long p = 3;
    long long q = 7;

    // First part of public key (n = p * q)
    long long n = p * q;

    // Phi (totient of n)
    long long phi = (p - 1) * (q - 1);

    // Finding e such that 1 < e < phi and gcd(e, phi) == 1
    long long e = 2;
    while (e < phi) {
        if (gcd(e, phi) == 1)
            break;
        else
            e++;
    }

    // Compute d (modular inverse of e)
    long long d = modInverse(e, phi);
    if (d == -1) {
        cout << "Error: Could not find modular inverse for d" << endl;
        return -1;
    }

    // Message to be encrypted
    long long msg = 12676;

    cout << "Message data = " << msg << endl;

    // Encryption: c = (msg^e) % n
    long long c = modExp(msg, e, n);
    cout << "Encrypted data = " << c << endl;

    // Decryption: m = (c^d) % n
    long long m = modExp(c, d, n);
    cout << "Original Message Sent = " << m << endl;

    return 0;
}
