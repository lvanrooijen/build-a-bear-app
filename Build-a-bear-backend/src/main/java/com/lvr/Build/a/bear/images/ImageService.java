package com.lvr.Build.a.bear.images;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final ImageRepository imageRepository;

  @Transactional
  public String uploadImage(MultipartFile imageFile) throws IOException {
    var imageToSave =
        Image.builder()
            .name(imageFile.getOriginalFilename())
            .type(imageFile.getContentType())
            .imageData(ImageUtils.compressImage(imageFile.getBytes()))
            .build();
    imageRepository.save(imageToSave);
    return "file uploaded succesfully: " + imageFile.getOriginalFilename();
  }

  @Transactional
  public byte[] downloadImage(String imageName) {
    Optional<Image> dbImage = imageRepository.findByName(imageName);

    return dbImage
        .map(
            image -> {
              try {
                return ImageUtils.decompressImage(image.getImageData());
              } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                    .addContextValue("Image ID", image.getId())
                    .addContextValue("Image name", imageName);
              }
            })
        .orElse(null);
  }
}
