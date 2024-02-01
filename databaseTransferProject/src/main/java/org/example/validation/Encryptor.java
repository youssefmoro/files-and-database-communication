package org.example.validation;

import org.example.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
        public static String calculateSHA256Hash(File file) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                try (InputStream inputStream = new FileInputStream(file);
                     DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead = Constants.LOW;
                    while (digestInputStream.read(buffer) != -1) {
                        digest.update(buffer, 0, bytesRead);
                    }
                }
                byte[] hashBytes = digest.digest();
                return bytesToHex(hashBytes);
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
    }

