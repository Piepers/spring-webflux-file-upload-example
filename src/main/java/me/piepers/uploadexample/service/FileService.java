package me.piepers.uploadexample.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface FileService {

    void init();

    Mono<Void> store(FilePart file);

    void deleteAll();
}
