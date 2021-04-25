call mvn clean install
docker build . -t marmiss/search-service:1.0.0
docker push marmiss/search-service:1.0.0