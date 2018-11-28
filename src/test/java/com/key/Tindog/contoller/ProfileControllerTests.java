package com.key.Tindog.contoller;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class ProfileControllerTests {

    @Test
    public void fileToBase64StringConversion() throws IOException {
        // load file from /src/test/resources
        String inputFilePath = "./uploads/dog.jpg";
        File inputFile = new File(inputFilePath);

        byte[] fileContent = Files.readAllBytes(inputFile.toPath());
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        // decode the string and write to file
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);


        assertTrue(Arrays.equals(fileContent, decodedBytes));
    }
}
