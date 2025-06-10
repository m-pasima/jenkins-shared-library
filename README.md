# 🚀 Jenkins Shared Library

![Jenkins](https://www.jenkins.io/images/logos/jenkins/jenkins.png)
![Maven](https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Apache_Maven_logo.svg/800px-Apache_Maven_logo.svg.png)



---

## 📚 Overview

This directory provides a Jenkins Shared Library refactored from the original `Jenkinsfile` in this repository. To leverage this as a **Global Pipeline Library** across multiple Jenkins projects, move the `jenkins-shared-library` directory to a separate Git repository.

---

## Directory Structure

```
jenkins-shared-library/
  vars/
   mavenAppPipeline.groovy


```

---

## 🚀 Getting Started: Implementation Steps

To set up and use this Jenkins Shared Library in your CI/CD pipelines, follow these real-world, production-ready steps:

### 1. **Clone the Library Repository**

Clone the shared library repository to your local environment:

```bash
git clone https://github.com/m-pasima/jenkins-shared-library.git
```

---

### 2. **(Optional) Migrate to Your Own Git Repository**

Prefer to keep it private or under your team’s control? Push the library to your own repo:

```bash
cd jenkins-shared-library
git remote set-url origin YOUR_OWN_REPO_URL
git push -u origin main
```

> **Pro tip:** Naming your repo `jenkins-shared-library` or `pipeline-lib` is a common convention.

---

### 3. **Configure Jenkins for Global Pipeline Libraries**

1. In Jenkins, go to:
   `Manage Jenkins → Configure System → Global Pipeline Libraries`
2. Add a new library entry:

   * **Name:** e.g., `devops-lib`
   * **Default Version:** `main` (or whichever branch you use)
   * **Repository URL:** `https://github.com/m-pasima/jenkins-shared-library.git` (or your custom repo URL)
   * **Credentials:** Set if your repository is private.

---

### 4. **Using the Library in Your Jenkins Pipelines**

In your Jenkinsfile, load and call functions from the shared library:

```groovy
@Library('devops-lib') _

mavenPipeline(repo: 'https://github.com/m-pasima/maven-web-app-demo.git')
```

> ⚠️ **Note:** Replace `'devops-lib'` with the name you configured in Jenkins.
> If you rename the pipeline function or add more, update your usage accordingly.

---

## 💡 Best Practices

* **Modularize logic:** Keep reusable pipeline logic here—makes pipelines DRY (Don’t Repeat Yourself) and easier to update globally.
* **Credentials:** **Never** hard-code credentials. Always use Jenkins Credentials for secrets.
* **Versioning:** Tag library releases and use version numbers in Jenkins to avoid “works on my branch” issues.
* **Documentation:** Comment your shared library code, especially custom steps.

---

## 📝 Example: Jenkinsfile for Multibranch Pipelines

Here’s a minimal Jenkinsfile that can work with your shared library:

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}
```

> This is a standalone pipeline. If you want to use your shared library logic, refer to the usage example above.

---

## 🔥 Pro Tips and Common Pitfalls

* **Naming mismatch:** If the library name in Jenkins and in your `@Library()` annotation don’t match, pipelines will fail.
* **Branch issues:** Always specify a default branch; mismatches lead to “library not found” errors.
* **Permissions:** Jenkins needs access to the Git repo. Test credentials on a sample job before rolling out.

---

✨ *DevOps Academy by Pasima* ✨

---


