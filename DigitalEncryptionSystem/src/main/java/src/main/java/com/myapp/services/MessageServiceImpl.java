package src.main.java.com.myapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import src.main.java.com.myapp.models.Message;
import org.springframework.stereotype.Service;
import src.main.java.com.myapp.repositories.MessageRepository;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveEncryptedMessage(String message, String aesKey) {
        System.out.println("Inside saveEncryptedMessage");
        try {
            // Generate AES key
//            String aesKey = generateAESKey();

            // Encrypt the message
//            String encryptedMessage = encryptMessage(message, aesKey);
            System.out.println(message);
            System.out.println(aesKey);

            // Create a new Message object
            Message newMessage = new Message();
            newMessage.setEncryptedMessage(message);
            newMessage.setAesKey(aesKey);
            newMessage.setCreatedAt(new Date());

            // Save the message to the database or perform any other operations

            return messageRepository.save(newMessage);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public String generateAESKey() {
//        try {
//            // Generate AES key
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(256);
//            SecretKey secretKey = keyGenerator.generateKey();
//
//            // Encode the key to Base64
//            byte[] encodedKey = secretKey.getEncoded();
//            return Base64.getEncoder().encodeToString(encodedKey);
//        } catch (NoSuchAlgorithmException e) {
//            // Handle NoSuchAlgorithmException
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public String encryptMessage(String message, String aesKey) {
//        try {
//            // Initialize AES cipher with the generated key
//            Cipher cipher = Cipher.getInstance("AES");
//            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//            // Encrypt the message
//            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
//
//            // Encode the encrypted bytes to Base64
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            // Handle any exceptions
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public String encryptMessage(String message, String aesKey) {
        try {
            // Initialize AES cipher with the provided key
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypt the message
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());

            // Encode the encrypted bytes to Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String generateAESKey() {
        try {
            // Generate AES key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            // Encode the key to Base64
            byte[] encodedKey = secretKey.getEncoded();
            return Base64.getEncoder().encodeToString(encodedKey);
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveAESKey(String aesKey) {
        // Placeholder logic to save the AES key
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

// MessageServiceImpl.java

    @Override
    public String decryptMessage(String encryptedMessage, String aesKey) {
        try {
            // Decode the AES key from Base64
            byte[] decodedKey = Base64.getDecoder().decode(aesKey);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            System.out.println("Encrypted Message: " + encryptedMessage);
            System.out.println("AES Key: " + aesKey);

            // Initialize AES cipher with the provided key
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decode the encrypted message from Base64
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);

            System.out.println("Decoded Encrypted Bytes: " + Arrays.toString(encryptedBytes));

            // Decrypt the message
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Convert decrypted bytes to String
            String decryptedMessage = new String(decryptedBytes);
            System.out.println("Decrypted Message: " + decryptedMessage);

            return decryptedMessage;
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return null;
        }
    }
}
