---
title: "Comp 3350 Maintaining a Legacy System (W26)
author: "Jerome De Guzman"
---

# Table of Contents

- [Table of Contents](#table-of-contents)
- [Maintaining a Legacy System](#maintaining-a-legacy-system)
  - [1. Getting the System Running (Field Notes + Reflections)](#1-getting-the-system-running-field-notes--reflections)
    - [Report](#report)
      - [Environment used: (Java version, IDE, OS)](#environment-used-java-version-ide-os)
      - [Steps to get the system running:](#steps-to-get-the-system-running)
  - [2. Understanding the System (In Your Own Words)](#2-understanding-the-system-in-your-own-words)
    - [Report:](#report-1)
      - [What problem does it solve?](#what-problem-does-it-solve)
      - [What are its major features?](#what-are-its-major-features)
      - [How does a user interact with it?](#how-does-a-user-interact-with-it)
    - [Name-in-UI requirement:](#name-in-ui-requirement)
  - [3. Architecture Exploration and Reflection](#3-architecture-exploration-and-reflection)
    - [Report](#report-2)
      - [Describe the architectural style (if any) that appears to be present (e.g., layered, MVC).](#describe-the-architectural-style-if-any-that-appears-to-be-present-eg-layered-mvc)
      - [How are responsiblilities divide across packages/classes](#how-are-responsiblilities-divide-across-packagesclasses)
      - [Is there separation between UI and logic? Provide examples.](#is-there-separation-between-ui-and-logic-provide-examples)
      - [Where is coupling high? Provide at least one specific example (class names + what depends on what).](#where-is-coupling-high-provide-at-least-one-specific-example-class-names--what-depends-on-what)
      - [Where is cohesion strong or weak? Provide at least one specific example.](#where-is-cohesion-strong-or-weak-provide-at-least-one-specific-example)
  - [4. Testing and Build State (Automated Test Required)](#4-testing-and-build-state-automated-test-required)
    - [Report](#report-3)
      - [Are tests present in the repository?](#are-tests-present-in-the-repository)
      - [If yes, were they runnnable? Where do they live and what do they test?](#if-yes-were-they-runnnable-where-do-they-live-and-what-do-they-test)
      - [If not, what does that suggest about maintainability and risk?](#if-not-what-does-that-suggest-about-maintainability-and-risk)
      - [Running tests](#running-tests)
    - [Implement one automated test](#implement-one-automated-test)
      - [What you chose to test and why](#what-you-chose-to-test-and-why)
        - [Whether your test is a unit test or an integration test (and why)](#whether-your-test-is-a-unit-test-or-an-integration-test-and-why)
      - [If the refactoring was required to enable testability (describe what changed and why).](#if-the-refactoring-was-required-to-enable-testability-describe-what-changed-and-why)
      - [Where the test file lives in the project structure (e.g., src/test/java/...)](#where-the-test-file-lives-in-the-project-structure-eg-srctestjava)
  - [5. Identifying a maintenance opportunity (No implementation required)](#5-identifying-a-maintenance-opportunity-no-implementation-required)
    - [Report](#report-4)
      - [Design Flaw](#design-flaw)
  - [6. Overall maintainability assessment](#6-overall-maintainability-assessment)
    - [Report](#report-5)
      - [Summary](#summary)
- [References](#references)


# Maintaining a Legacy System

We have been instructed to maintain a legacy system that we are not familiar with, we must get a clear understanding of the legacy system as a whole to be able to work and add implementation to the system effectively.

## 1. Getting the System Running (Field Notes + Reflections)

Our task is to build and run the system from source.

### Report
Build and run the system from source.

In your report, describe:
- The environment you used (Java version, IDE, OS).
- The steps you took to get the system running.
- Any build errors or dependency/configuration issues encountered.
- How you diagnosed and resolved issues.
- Your overall approach (trial-and-error, reading build files, searching for entry points, inspecting package structure, etc.).

This section should read like professional onboarding notes. Reflect on what you tried, what worked, and what you learned about the codebase from the setup process.

Evidence:
- Screenshot showing the application successfully running.
- Screenshot showing a successful build and/or successful test execution.

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
**Figure 1: Successful Build**

Thus, we move forward with the installation guide.

We run the code:
```bash
mvn javafx:run -pl View
```

![application-run](image-1.png)
**Figure 2: Successful Application**

![test-run](image-3.png)
**Figure 3: Successful Tests**

As we can see, the application has both been succesfully built and run, with the testings as well, with no issues.

--- 

## 2. Understanding the System (In Your Own Words)

### Report:
- What problem does it solve?
- What are its major features?
- How does a user interact with it?

Your explanation should demonstrate understanding, not copy documentation.

**Name-in-UI requirement:**
- Modify a visible label, heading, or title in the application to include your name.
- Include a screenshot proving your modification works.
  

#### What problem does it solve?
The application solves the problem of user boredom, allowing them to pass the time with something fulfilling and mentally challening. Sudoku, has been shown in studies to be a [good cognitive stimulating leisure-time activity.](https://pmc.ncbi.nlm.nih.gov/articles/PMC7718610/) (Ashlesh et al., 2020).


#### What are its major features?
There are a few major features:
- Saving game: internally (SQLite) and externally (file)
- Changing Language: (English and Polish)
- 3 Difficulties: (Easy, Medium, and Hard)

#### How does a user interact with it?
The user interacts with the application with the front-end given by JavaFX. The front-end has buttons, drop down menus, radio buttons for control, and an editable text box for the actual grid pieces of the sudoku.

### Name-in-UI requirement:
- Modify a visible label, heading, or title in the application to include your name.
- Include a screenshot proving your modification works.

![name-title](image-2.png)
**Figure 4: Name-in-UI**

Here in figure 3, we can see that I was able to change the title from 'Sudoku Game' to 'Suudoku  Game: Jerome De Guzman'. 

---

## 3. Architecture Exploration and Reflection

### Report
- Describe the architectural style (if any) that appears to be present (e.g., layered, MVC).
- How are responsibilities divided across packages/classes?
- Is there separation between UI and logic? Provide examples.
- Where is coupling high? Provide at least one specific example (class names + what depends on what).
- Where is cohesion strong or weak? Provide at least one specific example.

Reflect on whether the architecture makes maintenance easier or harder. Support your claims with specific evidence from the codebase (file names, class names, call chains, screenshots, diagrams, etc.).

#### Describe the architectural style (if any) that appears to be present (e.g., layered, MVC).
The application follows a Model View Controller (MVC) architectural pattern. It also has a layered type of architecture for the data access which separates the service/logic layer from the persistence depending on which one you use. That is either the file system or the JDBC.


#### How are responsiblilities divide across packages/classes
- Dao / JdbcDao: these modules are for handling persistence for the application.
  - Dao: provides interace and specific implementation for file system storage
  - JdbcDao: provides a specific implementation for SQLite using te jOOQ library.
- Model: Is used for the different models, such as SudokuBoard and SodukuField. This module also handles the bulk of the core business logic.
- View: Contains the code for handling the front-end work, this is where the JavaFX gui code lies, logic, the XML layouts, and others.

*Side Note: This isn't anything too important, but it is pretty funny to me. When I was initally looking over the codebase, I was confused with why the directory names was Dao and JdbcDao. I thought, the native language of the author was Polish, with support for English, how do they know a different language... I just realized now what it means.*

#### Is there separation between UI and logic? Provide examples.
We can see in the codebase, that there is a strong separation between the UI and logic layers. This is apparent when we see the different modules, that there is no dependencies on each other. For example, the Model module has no dependencies on JavaFX which means we could switch out the UI layer for something else and should have little to no issues. Another example is that the validation for the sudoku board is handled by the logic layer rather than being in the UI layer and then passed on to logic layer.

#### Where is coupling high? Provide at least one specific example (class names + what depends on what).
There is one specific example that we can see has high coupling, that is in the SudokuBoardDaoFactory class. The class is coupled to the other files within this module, that is the file FileSudokuBoardDao and the database JdbcSudokuBoardDao files to instantiate them. We can see the example in the createJdbcSudokuBoardDao function where the jdbcdao is coupled.


#### Where is cohesion strong or weak? Provide at least one specific example.
I can do two exmples for the cohesion, since it also gives me a good understanding of this concept.

**Strong Cohesion:**
The SudokuBoard class has strong cohesion, since all the functions contained within are closely related to focus on the of the class as a whole. That is to handle the SudokuBoard, it has well-defined responsibilities. Such as, initalizing the SudokuBoard, getting the entry within the board, setting the entry within the board, other getters and setters, solveGame, validation for valid Sudoku, and etc.

**Weak Cohesion:**
The SudokuBoardDaoFactory class has low cohesion since it handles both the mapping of the objects to the database records and handling the database connection, which are two different responsiblities with mapping and connection management.

---

## 4. Testing and Build State (Automated Test Required)

### Report
- Are tests present in the repository?
- If yes, were they runnable? Where do they live and what do they test?
- If not, what does that suggest about maintainability and risk?
  
Run the tests (if present) and include evidence of success or failure. You may comment on coverage reports if available (and what the coverage does or does not imply)

**Required: Implement one automated test (JUnit preferred).**

In your report, explain: 
- What you chose to test and why.
- Whether your test is a unit test or an integration test (and why).
- If refactoring was required to enable testability (describe what changed and why).
- Where the test file lives in the project structure (e.g., src/test/java/...).


#### Are tests present in the repository?
There are tests present in the repository. They are in an automated test suite.

#### If yes, were they runnnable? Where do they live and what do they test?
The tests are runnable, you can either use the Maven command 'mvn test' or what I did to have a more definitive confirmation of tests running, is to run each of the tests files. This way I was also able to get better understanding on where these tests are located. 

Theses tests are located within their own respective modulars, given the MVC architecture style used. There is a directory for thoses test that follow the following directory path `<MVC-DIRECTORY>/src/test/java`. 

There are unit test using JUnit for automated tests, that test the core implementations classes functionality. For example, the JdbcSudokuBoardDaoTest.java tests its respective .java class file counterpart, that is the JdbcSudokuBoardDao.java file. Ensuring that the SQLite databases functionality is working according with tests, such as, setUp, tearDown, cleanUp, testWriteAndRead, testNames, and etc.

There are also coverage reports that I have found. For example, the JdbcDao module has JaCoCo (Java Code Coverage) reports located in their respective `target/site/jacoco` directories. This allows us to use Test Runner for Java extension in Visual Studios to see the coverage report produced.


#### If not, what does that suggest about maintainability and risk?
If there were no tests created, then that would create a big issue when it comes to the maintainability of a system over time, especially once it becomes a legacy system. The risk would be that any little change to the implementation would cause a doubt on the developer, implementing them. Since without tests, they do not have any deterministic guarantee that their implementation/refactoring works, without affecting the whole systems behaviour as a whole. This can also cause hidden issues which can be quite difficult to identify and bugs that can slowly permeate as more implementation happens, leading to a fragile codebase.

#### Running tests
![Jacoco-Coverage](image-4.png)
**Figure 5: Jacoco Coverage Report**

As we can see, the figure above shows the Jacoco coverage report for the tests, we can run tests using the command palette in Visual Studios, running the command `Java: Run Test`, or `Test: Run All Tests`, or using the Test Runner for Java extension and clicking play. We can also right click on the play button with the circular white check mark with a black background icon, to run the tests with coverage.

### Implement one automated test

#### What you chose to test and why
I chose to test the names() function, that focuses the accuracy and handling edge cases.

We test the empty state, we also tested to make sure that writes are correct and appear in the list as they exactly were. We also tested the size integrity of making sure that the names we added are taken account of.

##### Whether your test is a unit test or an integration test (and why)
The test written would be classified as an integration test because of the fact that we are actually using the actual disk or file system that is implemented within what we're testing and we aren't mocking the behaviour like what is done for unit tests. The test was written this way so that we can properly test the behaviour of the system, creating a mock/unit test would essentially defeat the purpose of the test written.

#### If the refactoring was required to enable testability (describe what changed and why).
Yes there was refactoring that was required to enable testability because once the test was written and ran, there immediately was an issue that arose. That is, when getting all the file names, the function also included the file name '.gitkeep' which is not an inherent behaviour that we would want within our project or function. Thus, we had to refactor that function to filter out file names that specifically contain '.gitkeep' so that we are not adding that to our names.

#### Where the test file lives in the project structure (e.g., src/test/java/...)
The test file lives in the same file as the FileSudokuBoardDaoTest.java. Since, that test file handles all the testing for the specific file name it relates to. The only thing we did was added a new test function that contains our test written. So the path of the test file is Dao/src/test/java/sudoku/dao/FileSudokuBoardDaoTest.java which is the relative path.

---

## 5. Identifying a maintenance opportunity (No implementation required)

### Report
Select one of the following:
- Potential new feature
- Bug
- Design flaw

**No code is required for this activity. In your report:**
- Describe the problem/change clearly.
- Identify which classes/modules would be affected (include file names, screenshots, or diagrams as needed).
- Explain architectural risk (what could break, what is hard to change, what needs protection via tests).
- Describe where you would introduce a seam to safely modify the system (if appropriate).

#### Design Flaw

![front-end-grid](image-5.png)
**Figure 5: Design Flaw**

There is a front-end design flaw with how unclear the separation between the different 3x3 grids are displayed. This causes difficulty with understanding where a grid box entry belongs to, with myself accidentally entering numbers for a grid box thinking it belongs at the top right 3x3 grid, but actually its for the middle right 3x3 grid. This causes users to have mistakes with their inputs. Ultimately, the application does not accomodate for the user and I feel like this could have been prevented if it was reviewed by a UI/UX expert to be able to catch these types of design flaws that cause user issues.

As I was understanding the design flaw and which classes/modules it would affect, I noticed that there was a JavaFX design file, named primary.fxml which does contain a proper front-end design that implements that clear separation and also an exit button. That exit button, is what I could only assume, being a button that could bring the users back to the main menu.

As for the classes/modules that would be affected, it would be the View module which the specific file GameController.java. Since, from my understanding the GameController.java doesn't actually properly implement the usage of the primary.fxml file for the grids. Please look at figure 5 which shows the primary.fxml design for the game vs the actual game design, we can see that the primary.fxml should be the one used, but the GameController.java dynamically creates its own 9x9 grid for the Sudoku game, instead of using the fxml.

There really isn't any risk to the architecture since this change is mainly to the front-end design. The only issue I could see arising would be if for the gap to exists requires, what essentially would be, a 11 x 11 grid and to treat the extra 2 columns and rows as empty, not displaying them. Obviously, that isn't the best idea and would mess up with the assumption that the grids should be a 1-to-1 relationship paring and would create a lot of overhead for the core back-end logic. Thus, I feel like if the proper fxml was used, there wouldn't really be any architectural risk on what would break, or hard to change.

The seam I would introduce to safely modify the system is to create a sprouting seam which would be to create a new method within GameController.java that would bind/map to the .fxml file. So that, instead of the GameController.java being responsible for actually creating the UI elements by doing a loop, it would just build the logic to the pre-existing items defined in the .fxml. Then, from there just match the sudokuBoardGridPane with the propery row and column entry to the SudokuBoard array. The best example that I could see that already does this type of behaviour is the App.java file within the View module. This would allow the primary.fxml to serve as the definitive source for the front-end design and allow the GameController.java to focuse on the functional interaction rather than creating the front-end items. Separating this out minimizes the risk of breaking core logic when adjusting the interface layout.

---

## 6. Overall maintainability assessment

### Report
In a 1-2 page summary, address **all** of the following:
- Does the system appear actively maintained?
- Is the technical debt visible? What evidence suggests that?
- Are SOLID principles respected or violated? Provide examples.
- How difficult would it be to extend this system long-term?
- Would you recommend incremental improvement or a major refactor? Why?

Support your conclusions with specific evidence from the codebase and connections to course concepts.

#### Summary

The system clearly appears as though it is not being actively maintained, the strongest piece of evidence to support this claim is the Github repository. In which, the last time there was any commits created was well over 2 years ago. This can also be derived from the fact taht the applications codebase dependencies have not been updated to properly take account ofthe new dependency versions. Since, there was also nothing explicitly stated in the README.md that there could be issues when dependency versions change.

The technical debt does not look to be easily visible since there are issues that seem to lay dormant, until something new requires the specific behaviour, that assumption is violated when tested. The best piece of evidence for this is with the name function in the FileSudokuBoardDao.java file. As for, when I created my JUNIT test implementation that focuses on the functions behaviour, there was a hidden technical debt that had revealed itself. For, when I tested if the List\<String\> names were empty it came back false when it should be true. This was because when the names() function traverses through the file names within the directory it does not filter out the file, named, '.gitkeep' which was being processed when it shouldn't be.

Another technical debt that could also be mentioned is the primary.fxml and the mismatched front-end implementation of the core Sudoku grid, since looking at the .fxml anyone could assume that it is responsible for actually displaying the grids. However, from a more indepth analysis of the View module, you can see that the GameController.java actually dynamicallys creates those grids rather than using the provided primary.fxml and changing the attributes of that file.

Overview of the system seems to show a mixed compliance to SOLID principles, in the Model module there seems to be compliance with the Single Responsibility Principle (SRP), but then there is a Open/Closed Principle (OCP) violation in the SudokuBoardDaoFactory. In which adding a new persistence type, like cloud storage, would require modifying the existing factory code rather than extending it. Then there's also compliance of the Dependency Inversion Principle (DIP) with the Dao\<T\> interface, which allows the application to switch between SQLite and File storage without changing the GameController logic for saving.

The difficulty to extend this system to long-term wouldn't be too difficult. Since, the architecture style having the Model View Controller allows for modularity with different implementations when done correctly. However, from what I can tell from my own understanding of this system, it seems as though changing the persistence implementation wouldn't be too much of an issue, but changes with the UI would be where some issues could arise. I say this because of the technical debt I discovered earlier with hard coded java code that iterates through nested loops to generate the front-end grid rather than having a proper .fxml that could handle that. This hardcoded approach makes doing any changes harder, as it could risk altering the 1-to-1 mapping between the grid elements and the SudokuBoard array, causing issues with UI logic.

I would recommend incremental improvement rather than a major refactor because the core business logic has strong cohesiveness, and stable with units test that ensure proper behaviour. The important part that allows me to recommend incremental improvement is the fact that there are these automated junit tests that give security that any future implementations does not break current behaviour and reduces the chance of missing any hidden technical debt that could arise. This incremental improvement approach allows for minimal disruption, allowing us to target the more important technical debt first. 
 
---

# References
Ashlesh, P., Deepak, K. K., & Preet, K. K. (2020). Role of prefrontal cortex during Sudoku task: fNIRS study. Translational neuroscience, 11(1), 419–427. https://doi.org/10.1515/tnsci-2020-0147