# 🚀 Automated Testing Suite with Java, Selenium

The "Automated Testing Suite with Java, Selenium, TestNG, and Maven" project is a comprehensive test suite designed to automate functional, regression testing.

The suite comes pre-built with a suite of automated tests covering a wide range of scenarios, including login authentication, form submissions, and user actions. The project also includes a set of utilities for logging, error reporting, and test result generation.

## 🛠️ Technologies Used

- Java 17.0.6
- Selenium
- TestNG
- Maven

## 📦 Installation

Install Homebrew
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
Install java
```
https://www.oracle.com/java/technologies/downloads/
```

Uninstall java
```
sudo rm -fr /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin
sudo rm -fr /Library/PreferencePanes/JavaControlPanel.prefPane
sudo rm -fr ~/Library/Application\ Support/Java
sudo rm -fr /Library/Java/JavaVirtualMachines/*
```

How to know version of JDK and JRE
```
/usr/libexec/java_home -V //JDK
java -version //jre
```

Set java path
```
/usr/libexec/java_home  //To check the current path
nano ~/.zshrc //To add path
source ~/.zshrc //Load update source file
```

Install maven
```
brew install maven
```

1. Clone the repository to your local machine:
```
https://github.com/MusheerRepo/JavaSelenium
```

2. Navigate to the project directory:
```
cd JavaSelenium
```

3. Download dependencies:
```
mvn clean install -Dmaven.test.skip=true
```

## 🧪 Running Tests

1. Run the tests using Maven:
```
mvn test
```

## 📄 Reports
```
open test-output/Extent_Report/Extent.html
```

## 📬 Contact

[LinkIn](https://in.linkedin.com/in/musheer-ahmad-khan-579953113)
[Twitter](https://twitter.com/Musheer_AKhan)
