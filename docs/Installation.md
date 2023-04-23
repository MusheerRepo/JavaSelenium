## 📦 Installation

### Install Homebrew
```
$/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
### Install Java
```
https://www.oracle.com/java/technologies/downloads/
```

### Set correct $JAVA_HOME path
To check the current path
```
$/usr/libexec/java_home
```

Set $JAVA_HOME path
```
$nano ~/.zshrc
$source ~/.zshrc
```

### Install Maven
```
brew install maven
```

### How to know version of JDK and JRE (Incase of need)
```
/usr/libexec/java_home -V //JDK
java -version //jre
```

### Uninstall java (Incase of need)
```
sudo rm -fr /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin
sudo rm -fr /Library/PreferencePanes/JavaControlPanel.prefPane
sudo rm -fr ~/Library/Application\ Support/Java
sudo rm -fr /Library/Java/JavaVirtualMachines/*
```