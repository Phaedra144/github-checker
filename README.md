# Github checker

### Description

This application is a github parser with a nice bootstrap GUI and some basic functionality.
The queries relate to the activities of Green Fox Academy's students on github.

### Main functionalities

#### Check commits of students
With the application you are able to check:
- commit activities of students
- comment activities of mentors
- Todo App commits
  - The format of Todo-App repository has to be: **todo-app-"githubname"**
- Wanderer commits
  - The format of Wanderer repository has to be: **wanderer-"githubname"**

***Please make sure that the name of the student's main repository matches to their github handle.***

#### Add students to Green Fox Academy's github organisation
When new cohort comes, first adding every students to cohort team is needed to perform.

***Please make sure that the team (i.e. "2018-10 Rueppelli") has been already created on github.***

After assigning students to the classes you can run adding members again, thus saving students with additional information to the db and adding them to github class team at the same time.

Example:

Cohort name: "Rueppelli"
Class name: "Seadog"
Language: "java"

***Please make sure that the team (i.e. "Rueppelli Seadog") has been already created on github.***

### Environment variables

For local use you have to setup the below environment variables:

- CLIENT_ID
- CLIENT_SECRET
- AUTH_URL
- GF_PORT
- RDS_USERNAME
- RDS_PASSWORD
- RDS_DB_NAME
- RDS_HOST	- has to contain the db type, i.e.: `mysql://`
- RDS_PORT
- DB_DIALECT
- COOKIE_DOMAIN - i.e. "localhost"

### Deploy to AWS from terminal

- **Install Elastic Beanstalk CLI**
  Elastic Beanstalk CLI is a command line interface that lets you create, update and monitor Elastic Beanstalk environments, and deploy applications right from the terminal.
    - You can install Elastic Beanstalk CLI using Python’s Package management system, PIP. It comes bundled with Python version 3.x.x.


- **Use EB CLI with valid IAM user with administration access**
  [More info here](https://docs.aws.amazon.com/codecommit/latest/userguide/auth-and-access-control.html)

- **Creating an AWS CodeCommit Repository with the EB CLI**
  To get started with AWS CodeCommit, run `eb init`. During repository configuration, the EB CLI prompts you to use AWS CodeCommit to store your code and speed up deployments. Even if you previously configured your project with `eb init`, you can run it again to configure AWS CodeCommit.
    - To create an AWS CodeCommit repository with the EB CLI
    - Run eb init in your project folder. During configuration, the EB CLI asks if you want to use AWS CodeCommit to store your code and speed up deployments. If you previously configured your project with eb init, you can still run it again to configure AWS CodeCommit. Type y at the prompt to set up AWS CodeCommit.

    `~/my-app$ eb init
  Note: Elastic Beanstalk now supports AWS CodeCommit; a fully-managed source control service. To learn more, see Docs: https://aws.amazon.com/codecommit/
  Do you wish to continue with CodeCommit? (y/n)(default is n): y`

  - Choose repository.

  `Select a repository
  1) my-repo
  2) [ Create new Repository ]
  (default is 2): 2`

  - Choose an existing branch for your commits, or use the EB CLI to create a new branch.

  `Enter Branch Name
  ***** Must have at least one commit to create a new branch with CodeCommit *****
  (default is "master"): ENTER
  Successfully created branch: master`

- **Deploying from Your AWS CodeCommit Repository**
  When you configure AWS CodeCommit with your EB CLI repository, the EB CLI uses the contents of the repository to create source bundles. When you run `eb deploy` or `eb create`, the EB CLI pushes new commits and uses the HEAD revision of your branch to create the archive that it deploys to the EC2 instances in your environment.
  - first you need to run `eb create my-app-env`
  - then you can deploy with `eb deploy`

*In case you have credential issues:*
[Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html)
