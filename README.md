test
jade
# city-api

Originally based on the [getting-started-java](https://github.com/wercker/getting-started-java) sample for [Wercker](http://www.wercker.com), this project expands upon the original by expanding it into a simple 3-tier web app - web, api & db - each running in separate containers and ultimately deployed to Oracle Container Engine.  This project is the API part of that.

## Standing up locally using Docker

#### Spin up MySQL container:
```
docker run --name city-mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=welcome1 -d mysql:latest
```

#### Connect MySQL CLI to MySQL instance:
```
docker run -it --link city-mysql:mysql --rm mysql sh -c 'exec mysql -hmysql -P3306 -uroot -pwelcome1'
```

#### Inititialise citydb database with tables and sample data:
```
create database if not exists citydb;

use citydb;

CREATE USER IF NOT EXISTS 'test'@'%' IDENTIFIED BY 'welcome1';
GRANT ALL ON citydb.* TO 'test'@'%';

CREATE TABLE city (
  name VARCHAR(255) NOT NULL PRIMARY KEY
) CHARACTER SET utf8
  COLLATE utf8_general_ci;

INSERT INTO city (name) VALUES ("Amsterdam");
INSERT INTO city (name) VALUES ("Bristol");
INSERT INTO city (name) VALUES ("London");
INSERT INTO city (name) VALUES ("San Francisco");
```

## Standup city-api connected to MySQL container:
```
docker run --name city-api --link city-mysql:city-mysql -p 8080:8080 -e MYSQL_HOST=city-mysql -d wcr.io/rikgibson/city-api:latest
```

## Test

`http://localhost:8080/`
```
Hello World!
```
`http://localhost:8080/api/v1/city`
```
[Amsterdam, Bristol, London, San Francisco]
```
---
Sign up for wercker: http://www.wercker.com
Learn more at: http://devcenter.wercker.com
