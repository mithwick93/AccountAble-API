# AccountAble REST API

Java Spring Boot REST application for Personal finance management.

## Features

1. Support for users
2. Asset management
3. Payment system management
4. Transactions
5. Split transactions

## ER diagram

![](documentation/AccountAble.drawio.svg)

## How to run the application

### Installation

* This app requires Git, Java 21, Gradle and Docker to build, test and run.

#### Clone repo

1. Clone source files
    ```
    git clone https://github.com/mithwick93/AccountAble-API.git
    cd AccountAble-API
    ```

2. Build application
    ```
    ./gradlew clean build -x test
    ```

3. The JWT authentication requires RSA public and private key pairs
   * Provide your own key pair in the RSA_PRIVATE_KEY and RSA_PUBLIC_KEY env variables in single line without new lines
4. HTTPS requires a valid SSL certificate
   * Provide your own *.p12 certificate in the resources folder and provide the password in the KEY_STORE_PASSWORD env
     variable
5. Currency conversion requires a valid API key from https://www.exchangerate-api.com/
   * Provide your own API key in the CURRENCY_API_KEY env variable

#### Run API with docker compose

* This is the recommended approach for consuming the API locally.

1. MAC / LINUX
    ```
    export DB_PASSWORD=password 
    export DB_URL="jdbc:mysql://db:3306/accountable-db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    export DB_NAME=accountable-db
    export DB_USERNAME=accountable
    export DB_PASSWORD=####
    export MYSQL_ROOT_PASSWORD=####
    export RSA_PRIVATE_KEY=#
    export RSA_PUBLIC_KEY=#
    export KEY_STORE_PASSWORD=####
    export CURRENCY_API_KEY=####
   
   docker-compose -p accountable -f infrastructure/docker-compose.yml build --no-cache && docker-compose -p accountable -f infrastructure/docker-compose.yml up --force-recreate -d && docker image prune -a -f
    ```
   ```

## REST API

* Swagger UI is available at http://localhost:8080/swagger-ui/index.html#/

## License Information

- Apache v2 License

<pre>
Copyright(c) 2024 M.S. Wickramarathne

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>