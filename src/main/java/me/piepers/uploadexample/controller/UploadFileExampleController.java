package me.piepers.uploadexample.controller;

import me.piepers.uploadexample.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class UploadFileExampleController {
    private final FileService fileService;

    @Autowired
    public UploadFileExampleController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity> save(@RequestPart("file") Mono<FilePart> file) {
        return file
//                .filter(mpf -> mpf.getContentType().equalsIgnoreCase(ALLOWED_FILETYPE))
                .flatMap(f -> this.fileService.store(f))
                .map(result -> ok().body(Map.of("message", "ok")));
    }
}
