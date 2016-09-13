![alt text](https://www.bitrise.io/app/41230f25ccd1bfc9.svg?token=FvYL0SORH7xMgrm1G9XlUQ)

# mobile-user-group-firebase
Small sample application for Firebase Analytics, Database, Remote Config and Crash

# How to build
A new Firebase project must be created in order to build the project. Add the google-services.json class to the project and configure Firebase.

## Remote Config
Create a remote config named "blue_save_button" with value "true" or "false"

## Datbase
Use following rules

    {
	    "rules": {
	      "users": {
	        "$uid": {
	          ".write": "$uid === auth.uid",
	          ".read": "$uid === auth.uid"
	        }
	      }
	    }
	  }
