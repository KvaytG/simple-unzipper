# simple-unzipper

![Java 8](https://img.shields.io/badge/Java-8-blue?logo=java)  ![MIT License](https://img.shields.io/badge/License-MIT-green)

A Java console application for safely and conveniently unpacking ZIP archives with support for Windows notifications.

## 📚 Usage
```bash
java -jar simple-unzipper.jar <file.zip>
```

## ⚙️ Installation
1. Add the JitPack repository to your `pom.xml`
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
2. Add the dependency
```xml
<dependency>
    <groupId>com.github.KvaytG</groupId>
    <artifactId>simple-unzipper</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```
Optional: to use a specific version, tag a release and replace `-SNAPSHOT` with the tag name.

## 📜 License
Licensed under the **[MIT](LICENSE.txt)** license.

This project uses open-source components. For license details see **[pom.xml](pom.xml)** and dependencies' official websites.
