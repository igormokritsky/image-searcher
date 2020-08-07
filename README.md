Command for build :
`mvn clean install`
Command for run :
`java -jar target/image-searcher-1.0.0.jar`'

The application uses 
1. Guava Cache as cache provider (https://guava.dev/releases/21.0/api/docs/com/google/common/cache/Cache.html)
2. Retrofit as API presenter and HTTP client (https://square.github.io/retrofit/)

Searching logic is implemented in `ImageServiceImpl#filter()` method
Endpoint for searching - `/search/{searchedTerm}`
Application port - `8080`

`cache-reload-cron` from `application.properties` sets time of refreshing in `ImageCacheRepository` class in `refresh` method.

`agile-engine-base-url` from `application.properties` sets base URL in `ImageCacheRepository`.

`agile-engine-api-key` from `application.properties` sets API Key.