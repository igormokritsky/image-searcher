package com.mokritskyi.imagesearcher.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureInfoResponse {

    private String id;
    private String author;
    private String camera;
    private String tags;
    @JsonProperty("cropped_picture")
    private String croppedPicture;
    @JsonProperty("full_picture")
    private String fullPicture;

}
