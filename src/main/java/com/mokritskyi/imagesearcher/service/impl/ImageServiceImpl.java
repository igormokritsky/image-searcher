package com.mokritskyi.imagesearcher.service.impl;

import com.mokritskyi.imagesearcher.cache.ImageCacheRepository;
import com.mokritskyi.imagesearcher.dto.response.PageFullInfoPictureResponse;
import com.mokritskyi.imagesearcher.dto.response.PictureInfoResponse;
import com.mokritskyi.imagesearcher.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageCacheRepository imageCacheRepository;

    public ImageServiceImpl(ImageCacheRepository imageCacheRepository) {
        this.imageCacheRepository = imageCacheRepository;
    }

    @Override
    public List<PictureInfoResponse> getImagesByFilter(String searchTerm) throws ExecutionException {
        int pageNumber = 1;
        int pageCount = 2;
        List<PictureInfoResponse> pictureInfoResponses = new ArrayList<>();
        while (true) {
            if (pageNumber == pageCount) {
                break;
            }

            PageFullInfoPictureResponse pageFullInfoPictureResponse =
                    imageCacheRepository.getPageFullInfoPictureResponse(pageNumber);
            pictureInfoResponses.addAll(pageFullInfoPictureResponse.getPictureInfoResponses());

            if (pageNumber++ == 1) {
                pageCount = pageFullInfoPictureResponse.getPageCount();
            }
        }

        return filter(pictureInfoResponses, searchTerm);
    }

    private List<PictureInfoResponse> filter(List<PictureInfoResponse> pictureInfoResponses, String searchTerm) {
        Predicate<PictureInfoResponse> authorPredicate = p -> p != null && p.getAuthor() != null && p.getAuthor().contains(searchTerm);
        Predicate<PictureInfoResponse> cameraPredicate = p -> p != null && p.getCamera() != null && p.getCamera().contains(searchTerm);
        Predicate<PictureInfoResponse> tagsPredicate = p -> p != null && p.getTags() != null && p.getTags().replaceAll("#", "").contains(searchTerm);

        return pictureInfoResponses.stream()
                .filter(authorPredicate.or(cameraPredicate).or(tagsPredicate))
                .collect(Collectors.toList());
    }
}
