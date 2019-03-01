# JavaBox
A maven project similar to Dropbox with the following features: 
1. Keep watching the folder LocalFolder 
2. Detects any changes in the folder: new files, deleted files, modified files
3. Sync up all the changes from LocalFolder to specific AWS S3 bucket
## Prerequistes
Put your AWS credentials file located at ~/.s3_info
```
your_access_key_id
your_secret_access_key
```
# Testing Implemented 
1. Unit Testing and Integration Testing (JUnit)
2. Mocking (Mockito)
3. Static Code Analysis (CheckStyle, FindBugs)
4. Code Coverage (Cobertura)
![Alt text](/screenshots/code_coverage.png)

# Continuous Integration using Circle CI
![Alt text](/screenshots/circleci.png)
