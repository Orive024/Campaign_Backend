package com.orive.security.volunteers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    private static final int MAX_BUFFER_SIZE = 2 * 1024 * 1024; // 2MB

    public static byte[] compressImage(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0]; // Return empty byte array if input data is null or empty
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_SIZE)) {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            while (!deflater.finished()) {
                int size = deflater.deflate(buffer);
                outputStream.write(buffer, 0, size);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0]; // Return empty byte array on error
        }
    }

    public static byte[] decompressImage(byte[] compressedData) {
        if (compressedData == null || compressedData.length == 0) {
            return new byte[0]; // Return empty byte array if input data is null or empty
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_SIZE);
             ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData)) {

            Inflater inflater = new Inflater();
            inflater.setInput(compressedData);

            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }

            return outputStream.toByteArray();
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
            return new byte[0]; // Return empty byte array on error
        }
    }
}
