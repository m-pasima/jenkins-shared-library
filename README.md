# 🚀 Jenkins Shared Library

![Jenkins](https://www.jenkins.io/images/logos/jenkins/jenkins.png)
![Maven](https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Apache_Maven_logo.svg/800px-Apache_Maven_logo.svg.png)

## 📚 Overview

This shared library encapsulates common Jenkins pipeline stages for Maven projects, including cloning, building, scanning with SonarQube, artifact deployment, and conditional deployment to Tomcat.

## 🌳 Directory Structure

```
jenkins-shared-library/
├── vars/
│   └── mavenAppPipeline.groovy
└── src/
    └── org/example/ (optional for helper classes)
```

## 🛠 Steps to Clone and Implement

Follow these steps to clone and use this Jenkins Shared Library in your own Jenkins setup:

### 1. **Clone the Repository**

```bash
git clone https://github.com/m-pasima/jenkins-shared-library.git
```

### 2. **Upload to Your Own Git Repository (optional)**

* If you prefer to host your own copy, create a new repository (e.g., named `jenkins-shared-library`) on your Git hosting service (GitHub, GitLab, Bitbucket).
* Push the cloned content to your new repository:

```bash
cd jenkins-shared-library
git remote set-url origin YOUR_NEW_REPO_URL
git push -u origin main
```

### 3. **Configure Jenkins**

* Navigate to `Manage Jenkins → Configure System → Global Pipeline Libraries`.
* Add your repository details:

  * **Library Name**: e.g., `devops-lib`
  * **Repository URL**: `https://github.com/m-pasima/jenkins-shared-library.git` (or your new URL)
  * **Default Branch**: e.g., `main`
  * Add credentials if required.

### 4. **Use the Library in Your Jenkinsfile**

In your Jenkinsfile, load and invoke the shared pipeline:

```groovy
@Library('devops-lib') _

mavenAppPipeline(repo: 'https://github.com/m-pasima/maven-web-app-demo.git')
```

---

## ✅ Best Practices

* Keep reusable pipeline logic here for ease of maintenance and consistency.
* Avoid embedding sensitive credentials; use Jenkins Credentials instead.

## 📌 Example Jenkinsfile for Multibranch Pipelines

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

---

✨ *DevOps Academy by Pasima* ✨

