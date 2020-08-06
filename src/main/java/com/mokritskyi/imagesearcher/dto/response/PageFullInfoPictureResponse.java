package com.mokritskyi.imagesearcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageFullInfoPictureResponse {

    private List<PictureInfoResponse> pictureInfoResponses;
    private Integer pageCount;

}
