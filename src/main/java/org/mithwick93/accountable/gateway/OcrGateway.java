package org.mithwick93.accountable.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mithwick93.accountable.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OcrGateway {

    private final RestClient restClient;

    @Value("${ocr.api-url}")
    private String tess4jUrl;

    public String extractText(MultipartFile imageFile) {
        try {
            ByteArrayResource resource = new ByteArrayResource(getFileBytes(imageFile)) {

                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            String fullUrl = tess4jUrl + "/detect-text";
            URI uri = UriComponentsBuilder
                    .fromUriString(fullUrl)
                    .build().toUri();
            return restClient.post()
                    .uri(uri)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE)
                    .body(body)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new ExternalServiceException(
                    "OCR Service error: " + e.getMessage()
            );
        }
    }

    private byte[] getFileBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read uploaded file", e);
        }
    }

}
