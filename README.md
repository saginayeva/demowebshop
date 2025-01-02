# Test Automation Project for [Demo Web Shop](https://demowebshop.tricentis.com/)

<p align="center">
  <img src="images/logo/banner_shop.png" alt="Demo Web Shop Banner" width="800">
</p>

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Test Coverage](https://img.shields.io/badge/coverage-95%25-green)
[![security status](https://www.meterian.com/badge/gh/lviggiano/owner/security)](https://www.meterian.com/report/gh/lviggiano/owner)
[![stability status](https://www.meterian.com/badge/gh/lviggiano/owner/stability)](https://www.meterian.com/report/gh/lviggiano/owner)

## **Contents:** ##

* <a href="#tools">Technologies and tools</a>

* <a href="#cases">Automated Test Cases</a>

* <a href="#jenkins">Build in Jenkins</a>

* <a href="#console">Running from Terminal</a>

* <a href="#allure">Allure report</a>

* <a href="#testops">Integration with Allure TestOps</a>

* <a href="#jira">Integration with Jira</a>

* <a href="#telegram">Telegram Notifications via Bot</a>

* <a href="#video">Test execution video examples</a>

* <a href="#POM">Page Object Model to keep code clean and maintainable</a>


-----
<a id="tools"></a>
## <a name="Technologies and tools">**Technologies and tools:**</a>

<p align="center">
<a href="https://git-scm.com/"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/git/git-original.svg" title="Git" alt="Git" width="40" height="40"/> </a>
<a href="https://junit.org/junit5"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/junit/junit-original.svg" title="JUnit5" alt="JUnit5" width="40" height="40"/> </a>
<a href="https://www.selenium.dev/"> <img src="images/logo/selenium.svg" title="Selenium" alt="Selenium" width="40" height="40"/> </a>
<a href="https://www.selenium.dev/"> <img src="images/logo/rest_assured.png" title="REST Assured" alt="REST Assured" width="40" height="40"/> </a>
<a href="https://qameta.io/"> <img src="images/logo/allure_testops.png" title="Allure TestOps" alt="Allure TestOps" width="40" height="40"/> </a>
<a href="https://www.jenkins.io"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/jenkins/jenkins-original.svg" title="Jenkins" alt="Jenkins" width="40" height="40"/> </a>
<a href="https://allurereport.org/"> <img src="images/logo/allure_report.png" title="Allure report" alt="Allure report" width="40" height="40"/> </a>
<a href="https://github.com/"><img src="images/logo/githubb.svg" title="GitHub" alt="GitHub" width="40" height="40"/> </a>
<a href="https://gradle.org"> <img src="images/logo/gradle.png" title="Gradle" alt="Gradle" width="40" height="40"/> </a>
<a href="https://www.docker.com/"> <img src="images/logo/docker.svg" title="Docker" alt="Docker" width="40" height="40"/> </a>
<a href="https://web.telegram.org/"> <img src="images/logo/telegram_icon.png.png" title="Telegram bot" alt="Telegram bot" width="40" height="40"/> </a>
<a href="https://aerokube.com/selenoid/"><img src="images/logo/selenoid.svg" title="Selenoid" alt="Selenoid" height="40" width="40"/></a>
<a href="https://www.atlassian.com/software/jira"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/jira/jira-original.svg" title="Jira" alt="Jira" width="40" height="40"/> </a>
<a href="https://www.w3schools.com/java/"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" title="Java" alt="Java" width="40" height="40"/> </a>
</p>

- The UI autotests were written in **Java**.
- **Gradle** is used as a build tool.
- **JUnit 5** and **Selenium** are used as test frameworks.
- For remote run, a job in **Jenkins** with **Allure report** generation and result send to **Telegram** via a bot has been implemented.
- Integration with **Allure TestOps** and **Jira** has been established.
- **Docker & Selenoid** are used for running tests in isolated environments.

----
<a id="cases"></a>
## **Implemented Automated Tests:**
### **UI Tests:**
- ✅ **User registration**
  - Successful/Unsuccessful registration
- ✅ **Login functionality**
  - Successful login
- ✅ **Product search**
  - Successful search
  - No results for nonexistent items
  - Empty search query
- ✅ **Adding items to cart**
  - Adding items through UI
  - Validating cart size
- ✅ **Checkout process**
  - Successful checkout

### **API Tests:**
- ✅ **Login functionality**
  - Successful/Unsuccessful login requests
- ✅ **Add product to cart**
  - Adding product as an authenticated/anonymous user
- ✅ **Cart size verification**
  - Verifying product presence in the cart

### **E2E Tests:**
- ✅ [UI + API] **User journey:**
  - Login, add product to cart, and complete order

----
<a id="jenkins"></a>
## Build in Jenkins  
[View Job in Jenkins](https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/)  

<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/"><img src="images/screen/jenkins-project.png" alt="Jenkins" width="950"/></a>  
</p>

The following screen allows you to configure the necessary parameters for the build.
<p align="center"> <img src="images/screen/jenkins-params.png" alt="Jenkins Build Parameters" width="950"/> </p>

<details>
   <summary>Jenkins build options:</summary>
  
1. `BROWSER_SIZE`
```
default screen size - 1920x1080
```
2. `REMOTE_URL`
```
default https://user1:1234@selenoid.autotests.cloud/wd/hub
```
3. `BROWSER`
```
default Chrome
```

</details>

----
<a id="console"></a>
## Running from Terminal
___
**Local Execution**
```bash  
gradle clean test
```

**Remote Execution via Jenkins**
```bash
clean test
-DremoteUrl=${REMOTE_URL}
-DbrowserSize=${BROWSER_SIZE}
-Dbrowser=${BROWSER}
```

<details>
   <summary>Additional Commands:</summary>
  
1. Generate the test report:
```
gradle allureReport
```
2. Open the report in a browser:
```
gradle allureServe
```

</details>

### Remote Execution with Selenoid  
You can execute tests from the IntelliJ IDEA terminal, and they will run in a browser hosted remotely in a Docker container via Selenoid:

```bash
gradle clean test -Denv=remote
```
----
<a id="allure"></a>
## Allure report ([link](https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/))

**Main report page**
<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/"><img src="images/screen/allure-report.png" alt="Allure Report main" width="950"/></a>  
</p>

**Test cases**
<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/"><img src="images/screen/allure-test-case.png" alt="Allure Report testcases" width="950"/></a>  
</p>

----
<a id="testops"></a>
## Integration with Allure TestOps ([link](https://allure.autotests.cloud/launch/42891))
<p align="center">  
<a href="https://allure.autotests.cloud/launch/42891"><img src="images/screen/allure-testops.png" alt="Allure TestOps" width="950"/></a>  
</p>

**Test Results Visualization**
<p align="center">  
<a href="https://allure.autotests.cloud/launch/42891"><img src="images/screen/allure-testops-dashboard.png" alt="Allure TestOps" width="950"/></a>  
</p>

----
<a id="jira"></a>
## Integration with Jira ([link](https://jira.autotests.cloud/browse/HOMEWORK-1367))
<p align="center">  
<a href="https://jira.autotests.cloud/browse/HOMEWORK-1367"><img src="images/screen/jira.png" alt="Jira" width="950"/></a>  
</p>

----
<a id="telegram"></a>
## Telegram notification with bot
<p align="center">  
<img src="images/screen/telegram.png" width="350"/> 
</p>


----
<a id="video"></a>
## Test execution video examples
<p align="center">
<img title="Video" src="images/video/checkout_page.gif" width="550" height="350"  alt="video">   
</p>

----
<p align="center">
<img title="Video" src="images/video/e2e.gif" width="550" height="350"  alt="video">   
</p>
