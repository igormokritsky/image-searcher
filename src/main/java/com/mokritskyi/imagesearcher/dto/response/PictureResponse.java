package com.mokritskyi.imagesearcher.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureResponse {

    private String id;
    @JsonProperty("cropped_picture")
    private String croppedPicture;
}
