package src.main.java.com.myapp.services;

import src.main.java.com.myapp.models.Message;

import java.util.List;

public interface MessageService {
    Message saveEncryptedMessage(String message, String aesKey);
    String generateAESKey();
    String encryptMessage(String message, String aesKey);
    String decryptMessage(String encryptedMessage, String aesKey);
    void saveAESKey(String aesKey);
    List<Message> getAllMessages();
}
