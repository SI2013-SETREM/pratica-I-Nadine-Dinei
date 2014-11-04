
package util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *  Classe de criptografia
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 * @see http://crackstation.net/hashing-security.htm
 */
public abstract class Encryption {
    
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    
    // The following constants may be changed without breaking existing hashes.
    private static final int SALT_BYTE_SIZE = 24;
    private static final int HASH_BYTE_SIZE = 24;
    private static final int NUM_ITERATIONS = 1000;
    
    public static int iterations;
    public static String salt;
    public static String hash;
    
    /**
     *  Cria o hash de uma senha e disponibiliza os dados nos atributos de saída da classe
     * @param password A senha
     * @return Se o hash foi criado com sucesso
     */
    public static boolean hash(String password) {
        try {
            char[] pass = password.toCharArray();

            // Generate a random salt
            java.security.SecureRandom random = new java.security.SecureRandom();
            byte[] bytarrSalt = new byte[SALT_BYTE_SIZE];
            random.nextBytes(bytarrSalt);
            
            byte[] bytarrHash = pbkdf2(pass, bytarrSalt, NUM_ITERATIONS, HASH_BYTE_SIZE);
            
            // Send out
            hash = toHex(bytarrHash);
            salt = toHex(bytarrSalt);
            iterations = NUM_ITERATIONS;
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     *  Valida a senha
     * @param password Senha digitada
     * @param correctHash Hash correto
     * @param correctSalt Sal correto
     * @param correctIterations Número de iterações correto
     * @return Se a senha digitada confere com a que está armazenada
     */
    public static boolean validate(String password, String correctHash, String correctSalt, int correctIterations) {
        try {
            char[] pass = password.toCharArray();
            byte[] rightHash = fromHex(correctHash);
            byte[] rightSalt = fromHex(correctSalt);
            
            // Compute the hash of the provided password, using the same salt, 
            // iteration count, and hash length
            byte[] testHash = pbkdf2(pass, rightSalt, correctIterations, rightHash.length);
            // Compare the hashes in constant time. The password is correct if
            // both hashes match.
            return slowEquals(rightHash, testHash);
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line 
     * system using a timing attack and then attacked off-line.
     * 
     * @param   a       the first byte array
     * @param   b       the second byte array 
     * @return          true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  Computes the PBKDF2 hash of a password.
     *
     * @param   password    the password to hash.
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBDKF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }
    
    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) 
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
    
}
