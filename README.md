# Github checker

### Description

This application is a github parser with a nice bootstrap GUI and some basic functionality.
The queries relate to activities of Green Fox Academy's students on github.

With the application you are able to check commit activities of students, comment activities in Green Fox Academy's [organization](https://github.com/green-fox-academy).

**Please make sure that the name of the student's main repository matches to their github handle.**

Also the app is capable of monitoring some other repositories, such as:
- Todo App, the format of repository name has to be: todo-app-<githubname>
- Wanderer, the format of repository name has to be: wanderer-<githubname>

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
- COOKIE_DOMAIN
