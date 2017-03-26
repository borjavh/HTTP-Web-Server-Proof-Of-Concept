# HTTP-Web-Server-Proof-Of-Concept
A repository to storage a simple Http Web Server made in Java.

- [Installation](#installation)
- [Usage](#usage)
- [Author](#author)

## Installation

This project uses MAVEN. To compile the source code just execute the following command (it also run all the JUnit tests):
```shell
mvn install
```

You will get your JAR file into ./target dir. Then launch the http-web-server contained in the JAR like this:

```shell
java -jar HttpWebServer-Proof-of-concept-0.0.1.jar
```

By default the http-web-server accepts request under the 8080 TCP port. Optionally, you can set the TCP server port like this:

```shell
java -jar HttpWebServer-Proof-of-concept-0.0.1.jar 9090
```

- [Usage](#usage)

```text
user_1: this user can access the first page. (password: qwerty)
user_2: this user can access the second page. (password: qwerty)
user_3: this user can access the third page. (password: qwerty)
user_4: this user can access the first page and the third page. (password: qwerty)
user_5: this user can access the thrid page. (password: qwerty)
admin: this user has permissions to access all the pages. (password: qwerty)
```
## Author

* [Jorge Durán Murillas] (https://github.com/jduranmaster)