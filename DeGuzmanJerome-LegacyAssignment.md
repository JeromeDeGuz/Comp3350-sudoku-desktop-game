---
title: "Comp 3350 Maintaining a Legacy System (W26)
author: "Jerome De Guzman"
---

# Maintaining a Legacy System

We have been instructed to maintain a legacy system that we are not familiar with, we must get a clear understanding of the legacy system as a whole to be able to work and add implementation to the system effectively.

## 1. Getting the System Running (Field Notes + Reflections)

Our task is to build and run the system from source.

### Report

#### Environment used: (Java version, IDE, OS)
- Java version: 25.0.1, Although Java 21 is minimum version needed
- Java runtime environment: (build 25.0.1+8-LTS-27)
- IDE: Visual Studios: 1.112.0
- OS: OSX (MacOS): Sequoia 15.4.1

#### Steps to get the system running:

There are immediately errors after forking this codebase; there are undefined methods, variables, and imports; methods that need to be overridden or create implementation; and wrong parameter arguments that need to be handled before being able to build initally.

Following the legacy systems documentation instructions for installation and running, we can see we run into a dependency issue. That is, we need to have maven installed on our system.

Thus, one of the first things we must do is install maven:
```bash
brew install maven
```

Now we try to run the command:
```bash
mvn install
```

After running, we can see we were not able to have some symbols recognized because we were building with JOOQ since there was no explicit version created, we defaulted to 3.21.1. The build error said there was an error building JdbcDao with the mismatched dependencies version, so I went into the pom.xml file inside the JdbcDao directory and realized that jooq was there, but the specific version was not explicitely stated. So I added it in with the explicit version required that matches the DefaultCatalog.java and Constants.java files JOOQ version support.

Initalially, I had issues with figuring out which pom.xml file to change, but realized that the specific module that had the issue was within JdbcDao. From there I was able to find the pom.xml, added the explicit version requirement and tried to build again, and it worked.

I almost went down the route of thinking the code itself has issue, but after experiencing Comp 3350, Software Engineering, I realized that it could be build issues. As I have seen with other groups have issues with their gradle dependencies.

Now our build runs successfully:

![build-successful](image.png)

Thus, we move forward with the installation guide.

We run the code:
```bash
mvn javafx:run -pl View
```

![application-run](image-1.png)

## 2. Understanding the System (In Your Own Words)

### Report:
- What problem does it solve?
- What are its major features?
- How does a user interact with it?
  
### Name-in-UI requirement:
- Modify a visible label, heading, or title in the application to include your name.
- Include a screenshot proving your modification works.