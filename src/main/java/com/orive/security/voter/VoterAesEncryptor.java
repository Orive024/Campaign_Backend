package com.orive.security.voter;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;
import jakarta.persistence.AttributeConverter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@Configuration
public class VoterAesEncryptor implements AttributeConverter<Object, String> {

    @Value("${aes.encryption.key}")
    private String encryptionKey;

    private final String encryptionCipher = "AES";

    private Key key;
    private Cipher cipher;

    private Key getKey() {
        if (key == null)
            key = new SecretKeySpec(encryptionKey.getBytes(), encryptionCipher);
        return key;
    }

    private Cipher getCipher() throws GeneralSecurityException {
        if (cipher == null)
            cipher = Cipher.getInstance(encryptionCipher);
        return cipher;
    }

    private void initCipher(int encryptMode) throws GeneralSecurityException {
        getCipher().init(encryptMode, getKey());
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null)
            return null;
        initCipher(Cipher.ENCRYPT_MODE);
        byte[] bytes = SerializationUtils.serialize(attribute);
        return Base64.getEncoder().encodeToString(getCipher().doFinal(bytes));
    }

//    @SneakyThrows
//    @Override
//    public Object convertToEntityAttribute(String dbData) {
//        if (dbData == null)
//            return null;
//
//        try {
//            // Ensure the Base64 string has a valid length
//            int padding = 4 - (dbData.length() % 4);
//            if (padding != 4) {
//                dbData = dbData + "====".substring(0, padding);
//            }
//
//            initCipher(Cipher.DECRYPT_MODE);
//            System.out.println("Base64 String: " + dbData); // Log or print the Base64 string
//            byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(dbData));
//            return SerializationUtils.deserialize(bytes);
//        } catch (GeneralSecurityException | RuntimeException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
    
    @SuppressWarnings("deprecation")
    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        initCipher(Cipher.DECRYPT_MODE);
        byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(dbData));
        return SerializationUtils.deserialize(bytes);
    }

}