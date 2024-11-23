# Test Automation Project for [Demo Web Shop](https://demowebshop.tricentis.com/)

<p align="center">
  <img src="images/logo/banner_shop.png" alt="Demo Web Shop Banner" width="800">
</p>

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)
![Test Coverage](https://img.shields.io/badge/coverage-95%25-green)

## **Contents:** ##

* <a href="#tools">Technologies and tools</a>

* <a href="#cases">Automated Test Cases</a>

* <a href="#jenkins">Build in Jenkins</a>

* <a href="#console">Running from Terminal</a>

* <a href="#allure">Allure report</a>

* <a href="#testops">Integration with Allure TestOps</a>

* <a href="#jira">Integration with Jira</a>

* <a href="#telegram">Telegram notification with bot</a>

* <a href="#video">Selenoid test execution video examples</a>

* <a href="#POM">Page Object Model to keep code clean and maintainable</a>


-----
<a id="tools"></a>
## <a name="Technologies and tools">**Technologies and tools:**</a>

<p align="center">
<a href="https://git-scm.com/"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/git/git-original.svg" title="Git" alt="Git" width="40" height="40"/> </a>
<a href="https://junit.org/junit5"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/junit/junit-original.svg" title="JUnit5" alt="JUnit5" width="40" height="40"/> </a>
<a href="https://www.selenium.dev/"> <img src="images/logo/selenium.svg" title="Selenium" alt="Selenium" width="40" height="40"/> </a>
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
- **Gradle** was used as a builder.
- **JUnit 5** and **Selenium** frameworks were used as test frameworks.
- For remote run, a job in **Jenkins** with **Allure report** generation and result send to **Telegram** via a bot has been implemented.
- Integration with **Allure TestOps** and **Jira** has been established.


----
<a id="cases"></a>
## **Examples of automated test cases:**
**------------**
- ✅ User Registration
- ✅ Login Functionality
- ✅ Product Search
- ✅ Add to Cart
- ✅ Checkout Process

**------------**
- ✅ ------------
- ✅ ------------
- ✅ ------------
- ✅ ------------


----
<a id="jenkins"></a>
## Build in Jenkins ([link](https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/))
<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/"><img src="images/screen/jenkins_report.png" alt="Jenkins" width="950"/></a>  
</p>

### **Jenkins build options:**

- `BROWSER_SIZE` (default screen size - 1920x1080)
- `REMOTE_URL`


----
<a id="console"></a>
## Run from Terminal
___
**Local launch**
```bash  
gradle clean test
```

**Remote launch via Jenkins**
```bash
clean test
-DremoteUrl=${REMOTE_URL}
-DbrowserSize=${BROWSER_SIZE}
```


----
<a id="allure"></a>
## Allure report ([link](https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/))

**Main report page**
<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/"><img src="images/screen/allure_report_main.png" alt="Allure Report main" width="950"/></a>  
</p>

**Test cases**
<p align="center">  
<a href="https://jenkins.autotests.cloud/job/030_asem_jenkins_selenium/allure/"><img src="images/screen/allure_report_cases.png" alt="Allure Report testcases" width="950"/></a>  
</p>


----
<a id="testops"></a>
## Integration with Allure TestOps ([link](https://allure.autotests.cloud/launch/42891))
<p align="center">  
<a href="https://allure.autotests.cloud/launch/42891"><img src="images/screen/allure_testops_main.png" alt="Allure TestOps" width="950"/></a>  
</p>

**Manual test cases**
<p align="center">  
<a href="https://allure.autotests.cloud/launch/42891"><img src="images/screen/allure_testops_manual.png" alt="Allure TestOps" width="950"/></a>  
</p>

**Automation test cases**
<p align="center">  
<a href="https://allure.autotests.cloud/launch/42891"><img src="images/screen/allure_testops_auto.png" alt="Allure TestOps" width="950"/></a>  
</p>

----
<a id="jira"></a>
## Integration with Jira ([link](https://jira.autotests.cloud/browse/HOMEWORK-1367))
<p align="center">  
<a href="https://jira.autotests.cloud/browse/HOMEWORK-1367"><img src="images/screen/jira_task.png" alt="Jira" width="950"/></a>  
</p>

----
<a id="telegram"></a>
## Telegram notification with bot
<p align="center">  
<img src="images/screen/telegram.png" width="350"/> 
</p>


----
<a id="video"></a>
## Selenoid test execution video examples
<p align="center">
<img title="Selenoid Video" src="images/video/succesful_registration.dif" width="550" height="350"  alt="video">   
</p>
