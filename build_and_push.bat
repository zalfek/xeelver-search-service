call mvn clean install
docker build . -t marmiss/search-service:1.1.1
docker push marmiss/search-service:1.1.1