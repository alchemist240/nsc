import hashlib

# Function to hash a message using SHA-256
def hash_message(message):
    # Convert the message to bytes
    byte_message = message.encode()

    # Create a SHA-256 hash object
    sha256_hash = hashlib.sha256()

    # Update the hash object with the message
    sha256_hash.update(byte_message)

    # Get the hexadecimal digest of the hash
    hash_digest = sha256_hash.hexdigest()

    return hash_digest

# Example usage
message = "Hello World"
hashed_message = hash_message(message)

print(f"Original Message: {message}")
print(f"Hashed Message (SHA-256): {hashed_message}")
