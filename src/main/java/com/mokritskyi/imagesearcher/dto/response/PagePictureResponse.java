package com.mokritskyi.imagesearcher.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagePictureResponse {

    @JsonProperty("pictures")
    private List<PictureResponse> pictureResponses;
    private Integer page;
    private Integer pageCount;
    private boolean hasMore;

}
