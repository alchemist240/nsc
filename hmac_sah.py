import hashlib
import hmac

def generate_hmac(key, message):
    # Create an HMAC object using the secret key and SHA-256 hash function
    hmac_obj = hmac.new(key.encode(), message.encode(), hashlib.sha256)
    
    # Return the hexadecimal digest of the HMAC
    return hmac_obj.hexdigest()

if __name__ == "__main__":
    key = "supersecretkey"
    message = "This is a secret message"
    
    # Generate HMAC
    hmac_value = generate_hmac(key, message)
    
    print("Key:", key)
    print("Message:", message)
    print("HMAC (SHA-256):", hmac_value)
