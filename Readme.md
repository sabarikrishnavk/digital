

###Local development

### install maven repos locally
mvn clean install -f digital-team/digital-starter-parent/pom.xml
mvn clean install -f digital-team/digital-spring-framework/pom.xml
mvn clean install -f ops-team/ops-tools-api/pom.xml

mvn clean -f digital-team/digital-starter-parent/pom.xml
mvn clean -f digital-team/digital-spring-framework/pom.xml
mvn clean -f ops-team/ops-tools-api/pom.xml

### install maven deploy to nexus
mvn clean install deploy -f digital-team/digital-starter-parent/pom.xml
mvn clean install deploy -f digital-team/digital-spring-framework/pom.xml
mvn clean install deploy -f ops-team/ops-tools-api/pom.xml


### dependency tree maven repos locally
mvn  dependency:tree -f ops-team/ops-tools-api/pom.xml


### push to sonar for quality gate
mvn  sonar:sonar -f ops-team/ops-tools-api/pom.xml -Dsonar.host.url=http://localhost:8081<Sonar server> \
-Dsonar.login=<key for project> \
-Dproject.build.sourceEncoding=UTF-8 -Dproject.reporting.outputEncoding=UTF-8 \
-Dspring.profiles.active=sonarbuild \
-Dsonar.projectKey=com.digital.core -Dsonar.projectName=ops-tools-api


### build docker image
mvn spring-boot:build-image -f ops-team/ops-tools-api/pom.xml \
-Dspring-boot.build-image.imageName=gcr.io/${PROJECT_ID}/ops-tools-api \
-Dspring-boot.build-image.builder=gcr.io/buildpacks/builder

## After server start
http://localhost:3080/apis/ops/ops-tools-api/tools/v1/api

#Docker Build
docker build -t digital-ops/ops-tools-api ops-tools-api
#docker run -d -p 3080:3080 -p 3081:3081 --name ops-tools-api digital-ops/ops-tools-api

#docker tag digital-ops/ops-tools-api us-east4-docker.pkg.dev/$GCP_PROJECT/docker-registry/digital-ops/ops-tools-api
#docker push us-east4-docker.pkg.dev/$GCP_PROJECT>/docker-registry/digital-ops/ops-tools-api:0.0.1

#Kubernetes deployment in minikube

Run the
    minikube addons enable registry

Run the following command to enable docker registy in minikube
    docker run --rm -it --network=host alpine ash -c "apk add socat && socat TCP-LISTEN:5000,reuseaddr,fork TCP:$(minikube ip):5000"

Push the image to minikube registry
    docker tag digital-ops/ops-tools-api localhost:5000/digital-ops/ops-tools-api
    docker push localhost:5000/digital-ops/ops-tools-api:latest





##Cucumber

mvn archetype:generate                      \
"-DarchetypeGroupId=io.cucumber"           \
"-DarchetypeArtifactId=cucumber-archetype" \
"-DarchetypeVersion=7.0.0"               \
"-DgroupId=com.digital.automation.ops"                  \
"-DartifactId=ops-cucumber"               \
"-Dpackage=com.digital.automation.ops"                  \
"-Dversion=0.0.1"                 \
"-DinteractiveMode=false"

Refer : https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
To give Mac access to chromedriver 
 run >> xattr -d com.apple.quarantine /usr/local/bin/chromedriver

brew install --cask chromedriver


mvn clean verify -Dspring.profiles.active=desktop -f digital-automation/ecom-cucumber-ui/pom.xml
mvn clean verify -f ops-team/ops-cucumber/pom.xml

mvn test -f digital-automation/ecom-cucumber-ui/pom.xml
mvn test -f ops-team/ops-cucumber/pom.xml


Micro Front end
----

https://itnext.io/create-a-front-app-immediately-with-next-js-on-google-cloud-run-d0cfde795ce3