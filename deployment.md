{% include navigation.html %}

Raspberry Pi - Setting Up Java Web

# Discussion Questions/Policy
**Deployment: Every Friday 9pm**
**Policy: All work must be pushed by Thursday night, 24 hours  before deployment in order to be in the most updated pull**

# Deployment Guide
 ## Installing java runtime environment:
- [ ] $ sudo apt update 
- [ ] $ sudo apt upgrade
- [ ] $ sudo apt install default-jre 
- [ ] $ java -version 

 ## Install Java Development Kit
- [ ] $ sudo apt install default-jdk
- [ ] $ javac -version

 ## Maven is required to build project 
- [ ] $ sudo apt update 
- [ ] $ sudo apt upgrae

## Cloning repository:
- [ ] $ git clone https://github.com/ridhimainukurti/valid.git
- [ ] $ cd p1-Valid
- [ ] $ sudo mvn package

## Creating Web
- [ ] $ cd /etc/systemd/system/
- [ ] $ sudo nano p1-Valid.service
- [ ] $ sudo systemctl start p1-Valid
- [ ] $ sudo systemctl status p1-Valid

## Pulling Updated Code
- [ ] $ sudo git pull
- [ ] $ sudo mvn package 
- [ ] $ sudo java -jar/home/pi/pi-Valid/target/serving-web-content-0.0.1-SNAPSHOT.jar

## Run java project
- [ ] $ cd
- [ ] $ sudo java -jar/home/pi/pi-Valid/target/serving-web-content-0.0.1-SNAPSHOT.jar

## Obtaining Domain Name
- [ ]  [Step by Step Guide](https://docs.google.com/document/d/1nODveWp0jBzj4ZpFLgWCWTOXzLAHAPUhAQYmZJ4LhyU/edit)  

## Service File
- [ ] $ cd /etc/nginx/sites-available
- [ ] $ sudo ln -s /etc/nginx/sites-available/p1-Valid /etc/nginx/sites-enabled
- [ ] $ sudo nginx -t
- [ ] $ sudo systemctl restart nginx
- [ ] $ sudo nano /etc/systemd/system/valid.service

[Unit]
Description=Java
After=network.target

[Service]
User=pi
Restart=always
ExecStart=java -jar /home/pi/p1-Valid/target/csa-0.0.1-SNAPSHOT.jar

[Install]
WantedBy=multi-user.target 


- [ ] $ sudo systemctl start p1-Valid
- [ ] $ systemctl status p1-Valid
- [ ] $ sudo systemctl enable p1-Valid

# NGINX File
- [ ] $ sudo nano /etc/nginx/sites-available/p1-Valid

server {
    listen 80;
    server_name p1-valid.cf;

    location / {
        proxy_pass http://localhost:8080;
    }
}

## Test for no errors
- [ ] $ sudo ln -s /etc/nginx/sites-available/p1-Valid/etc/nginx/sites-enabled
- [ ] $ sudo nginx -t
- [ ] $ sudo systemctl restart nginx
- [ ] $ sudo systemctl start nginx
- [ ] $ sudo systemctl status nginx


# [Nginx running status](https://docs.google.com/document/d/1nXd7m8fAy-4cD54NTOrquUtCxx-63Auxtq0l2gP_tLg/edit)

# [Service Config](https://docs.google.com/document/d/1RbWeXaezjsGUvCkCHol9NRSxe7pB7SQd0DIPOGiKyfY/edit)

# [Port Forwarding Setting](https://docs.google.com/document/d/1ByeMHxUk8mbCJmzY6ohIreDKWV-0IOJGAaJpU1WV_oI/edit?usp=sharing)
