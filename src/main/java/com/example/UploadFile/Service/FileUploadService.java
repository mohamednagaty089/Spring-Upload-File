package com.example.UploadFile.Service;

import com.example.UploadFile.Error.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {
    Logger log= LoggerFactory.getLogger(FileUploadService.class);

    private Path fileStorageLocation;

    @Value("${file.upload.base-path}")
    private final String basePath="";
    public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }

    public String storeFile(File file, Long id, String pathType) {

        // create uploaded path
        this.fileStorageLocation = Paths.get(basePath + pathType).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);//create folder of files
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }

        String fileName = StringUtils.cleanPath(id + "-" + file.getName());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            InputStream targetStream = new FileInputStream(file);
            Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

           // updateImagePath(id, pathType, pathType + "/" + fileName);

            return pathType+"/"+fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
