
#Setup kubernetes objects for services.
minikube start

kubectl apply -f Setup/platform-app-config/ops/ops-tools-api/platform-secret.yaml
kubectl apply -f Setup/platform-app-config/ops/ops-tools-api/platform-configmap.yaml
kubectl apply -f Setup/platform-app-config/ops/ops-tools-api/platform-dev01-configmap.yaml

kubectl apply -f Setup/ops-app-config/ops-tools-api/application-configmap.yaml
kubectl apply -f Setup/ops-app-config/ops-tools-api/application-dev01-configmap.yaml
kubectl apply -f Setup/ops-app-config/ops-tools-api/application-default-configmap.yaml


###Local development

### install and run sonarqube
docker pull sonarqube:latest
docker run -d -p 9000:9000  -v sonarqube_extensions:/opt/sonarqube/extensions --name sonarqube sonarqube:latest
curl http://localhost:9000 (admin/admin to start)

### install and run nexus
docker pull sonatype/nexus3
docker run -d -p 8081:8081 --name nexus sonatype/nexus3
curl http://localhost:8081/

##First time admin password is saved under /nexus-data/admin.password file in docker containerId
docker ps
docker exec -it <containerId> /bin/sh

### install maven repos locally
mvn clean install -f digital-spring-dependencies/pom.xml
mvn clean install -f digital-spring-framework/pom.xml
mvn clean install -f ops-tools-api/pom.xml

mvn clean -f digital-spring-dependencies/pom.xml
mvn clean -f digital-spring-framework/pom.xml
mvn clean -f ops-tools-api/pom.xml

### install maven deploy to nexus
mvn clean install deploy -f digital-spring-dependencies/pom.xml
mvn clean install deploy -f digital-spring-framework/pom.xml
mvn clean install deploy -f ops-tools-api/pom.xml


### dependency tree maven repos locally
mvn  dependency:tree -f ops-tools-api/pom.xml


### push to sonar for quality gate
mvn  sonar:sonar -f ops-tools-api/pom.xml -Dsonar.host.url=http://localhost:8081<Sonar server> \
-Dsonar.login=<key for project> \
-Dproject.build.sourceEncoding=UTF-8 -Dproject.reporting.outputEncoding=UTF-8 \
-Dspring.profiles.active=sonarbuild \
-Dsonar.projectKey=com.digital.core -Dsonar.projectName=ops-tools-api


### build docker image
mvn spring-boot:build-image -f ops-tools-api/pom.xml \
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


    
kubectl apply -f Setup/plaform-cd/ops/ops-tools-api/deployment-local.yaml



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


mvn clean verify -Dspring.profiles.active=desktop -f ui-automation/ops-cucumber-ui/pom.xml
mvn clean verify -f ops-automation/ops-cucumber/pom.xml

mvn test -f ui-automation/ops-cucumber-ui/pom.xml
mvn test -f ops-automation/ops-cucumber/pom.xml


Micro Front end
----

https://itnext.io/create-a-front-app-immediately-with-next-js-on-google-cloud-run-d0cfde795ce3