package com.mokritskyi.imagesearcher.service;

import com.mokritskyi.imagesearcher.dto.response.PictureInfoResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ImageService {

    List<PictureInfoResponse> getImagesByFilter(String searchTerm) throws ExecutionException;

}
