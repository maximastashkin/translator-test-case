# tinkoff-translator-test-case
## Word by word async translator using yandex api

Because the used api yandex ai translation limits the number of requests to 20 per second, I had to set the calculated delay between calls to the api. For 10 threads, for example, 10 / 20 = 0.5 seconds. This calculation is automatically performed for the selected number of streams.

## Database
Used H2 database in embedded mode. For its inspection, the console is available at the url: host:port/h2-console. The username and password are set on first run in application.properties or when the Docker container is started. Database files are stored in ./data_storage .

## Docker
#### To start, you need yandex api key and api folder id. If not, I can provide mine for testing (telegram: @maxikexe).
```sh
docker build -t translator-test-case .
```
#### When starting the container, you can configure all the options specified in the line below.
**The startup line bellow specifies the required parameters**
```sh
docker run -p <external_port>:<internal_port> -e "JAVA_OPTS=-Dserver.port=<internal_port> -Dtranslator.yandex.api_folder_id=<your_api_folder_id_here> -Dtranslator.yandex.api_key=<your_api_key_here> -Dspring.datasource.username=<your_db_username_here> -Dspring.datasource.password=<your_db_password_here>" translator-test-case
```

#### You can also configure the settings shown in the table below.
| Parameter                  | Meaning |
| -------------------------- | ------- | 
|translator.yadnex.response_timeout_ms| API response timeout |
|translation_service.async.thread_pool_size| The number of threads used in word processing of the input string |