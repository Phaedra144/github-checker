# Github checker

### Description

This application is a github parser with a nice bootstrap GUI and some basic functionality.
The queries relate to activities of Green Fox Academy's students on github.

With the application you are able to check commit activities of students, comment activities of mentors and add new students to  [organization](https://github.com/green-fox-academy).

**Please make sure that the name of the student's main repository matches to their github handle.**

Also the app is capable of monitoring some other repositories, such as:
- Todo App, the format of repository name has to be: todo-app-"githubname"
- Wanderer, the format of repository name has to be: wanderer-"githubname"

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


- **Creating an AWS CodeCommit Repository with the EB CLI**
  To get started with AWS CodeCommit, run `eb init`. During repository configuration, the EB CLI prompts you to use AWS CodeCommit to store your code and speed up deployments. Even if you previously configured your project with eb init, you can run it again to configure AWS CodeCommit.
    - To create an AWS CodeCommit repository with the EB CLI
    - Run eb init in your project folder. During configuration, the EB CLI asks if you want to use AWS CodeCommit to store your code and speed up deployments. If you previously configured your project with eb init, you can still run it again to configure AWS CodeCommit. Type y at the prompt to set up AWS CodeCommit.

  `~/my-app$ eb init
Note: Elastic Beanstalk now supports AWS CodeCommit; a fully-managed source control service. To learn more, see Docs: https://aws.amazon.com/codecommit/
Do you wish to continue with CodeCommit? (y/n)(default is n): y`

  - Choose Create new Repository.

  `Select a repository
  1) my-repo
  2) [ Create new Repository ]
  (default is 2): 2`

  - Type a repository name or press Enter to accept the default name.

  `Enter Repository Name
  (default is "codecommit-origin"): my-app
  Successfully created repository: my-app`

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
