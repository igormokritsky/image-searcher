package com.mokritskyi.imagesearcher.retrofit;

import com.mokritskyi.imagesearcher.dto.response.AuthTokenResponse;
import com.mokritskyi.imagesearcher.dto.response.PagePictureResponse;
import com.mokritskyi.imagesearcher.dto.response.PictureInfoResponse;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

@Component
public interface AgileEngineImageClientService {

    @POST("/auth")
    Call<AuthTokenResponse> authorize(@Body Map<String, Object> apiKey);

    @GET("/images/{id}")
    Call<PictureInfoResponse> getPictureInfoResponse(@Header("Authorization") String authHeader, @Path("id") String id);

    @GET("/images")
    Call<PagePictureResponse> getPictureResponse(@Header("Authorization") String authHeader, @Query("page") Integer page);

}
