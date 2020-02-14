package me.piepers.uploadexample.service.impl;

import me.piepers.uploadexample.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    private final Path rootLocation;

    private static final String ALLOWED_FILETYPE = "application/x-redhat-package-manager";

    @Autowired
    public FileServiceImpl(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        if (Files.notExists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new StorageException(e.getMessage(), e);
            }
        }
    }

    @Override
    public Mono<Void> store(FilePart file) {

        if (file.filename().contains("..")) {
            return Mono.error(new StorageException("Cannot store file with relative path outside current directory " + file.filename()));
        }

        return file.transferTo(this.rootLocation.resolve(file.filename()));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }
}
