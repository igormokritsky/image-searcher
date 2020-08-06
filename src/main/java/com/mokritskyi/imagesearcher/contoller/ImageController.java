package com.mokritskyi.imagesearcher.contoller;

import com.mokritskyi.imagesearcher.dto.response.PictureInfoResponse;
import com.mokritskyi.imagesearcher.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/search/{searchTerm}")
    public List<PictureInfoResponse> search(@PathVariable("searchTerm") String searchTerm) throws ExecutionException {
        return imageService.getImagesByFilter(searchTerm);
    }
}
