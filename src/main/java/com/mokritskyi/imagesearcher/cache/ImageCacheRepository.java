package com.mokritskyi.imagesearcher.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mokritskyi.imagesearcher.dto.response.*;
import com.mokritskyi.imagesearcher.retrofit.AgileEngineImageClientService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Repository
public class ImageCacheRepository {

    private final LoadingCache<Integer, PageFullInfoPictureResponse> pagePictureCache;

    public ImageCacheRepository(@Value("${agile-engine-base-url}") String baseUrl,
                                @Value("${agile-engine-api-key}") String apiKey) throws IOException {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        AgileEngineImageClientService agileEngineImageClientService = retrofit.create(AgileEngineImageClientService.class);

        AuthTokenResponse authTokenResponse = agileEngineImageClientService
                .authorize(Collections.singletonMap("apiKey", apiKey))
                .execute().body();
        assert authTokenResponse != null;
        String token = authTokenResponse.getToken();

        pagePictureCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(new PagePictureCacheLoader(agileEngineImageClientService, token));
    }

    @Scheduled(cron = "${cache-reload-cron}")
    public void refresh() {
        for (Integer page : pagePictureCache.asMap().keySet()) {
            pagePictureCache.refresh(page);
        }
    }

    public PageFullInfoPictureResponse getPageFullInfoPictureResponse(Integer page) throws ExecutionException {
        return pagePictureCache.get(page);
    }

    private static class PagePictureCacheLoader extends CacheLoader<Integer, PageFullInfoPictureResponse> {

        private final AgileEngineImageClientService agileEngineImageClientService;
        private final String token;

        public PagePictureCacheLoader(AgileEngineImageClientService agileEngineImageClientService,
                                      String token) {
            this.token = token;
            this.agileEngineImageClientService = agileEngineImageClientService;
        }

        @Override
        public PageFullInfoPictureResponse load(Integer page) throws Exception {
            PagePictureResponse pagePictureResponse = agileEngineImageClientService.getPictureResponse(token, page)
                    .execute().body();

            if (pagePictureResponse == null) {
                return null;
            }

            List<PictureResponse> pictureResponses = pagePictureResponse.getPictureResponses();

            if (CollectionUtils.isEmpty(pictureResponses)) {
                return null;
            }

            List<PictureInfoResponse> pictureInfoResponses = new ArrayList<>(pictureResponses.size());

            PageFullInfoPictureResponse pageFullInfoPictureResponse = new PageFullInfoPictureResponse();

            for (PictureResponse pictureResponse: pictureResponses) {
                PictureInfoResponse pictureInfoResponse =
                        agileEngineImageClientService.getPictureInfoResponse(token, pictureResponse.getId()).execute().body();

                if (pictureInfoResponse != null) {
                    pictureInfoResponses.add(pictureInfoResponse);
                }
            }

            pageFullInfoPictureResponse.setPictureInfoResponses(pictureInfoResponses);
            pageFullInfoPictureResponse.setPageCount(pagePictureResponse.getPageCount());

            log.info("Fetched {} images from API", pictureResponses.size());

            return pageFullInfoPictureResponse;
        }
    }

}
