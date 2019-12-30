# wishlistRestAPI
basic spring boot app rest api configured to run on AWS

the UI is angular code that is in another repo.

it is built there with 'ng build --prod' and put in src/main/resources/static

simple controller serves angular's index.html when user hits root

# setup
when running localy use -Dspring.profiles.active=local

to package and eploy to AWS use

mvn clean package spring-boot:repackage -Dmaven.test.skip=true

eb deploy (deploy artifact specified in /.elasticbeanstalk/config.yml)


