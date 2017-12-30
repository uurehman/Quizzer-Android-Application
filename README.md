# Quizzer Android Application
Quizzer is a simple application in which one can make quizzes (as teacher) and attempt quizzes (as student).


## Problem Statement
One of the key problems to digital quizzing applications is the difficulty to generate a digital quiz. In order to solve this particular problem, you will be designing a Java based desktop application that provides an interactive interface to an instructor for generating a quiz.


## Problem Working details

### User Entity, Login (Roles):
Start by designing a user entity, which holds at least the username, password, role and score. The username and password will be used by the application to authenticate the user, while the role can be either an instructor or a student.

### Instructor details:
Instructors after logging in, should be allowed to create a Quiz by providing a short title and description for the quiz. Once a quiz has been created, the application should then allow the user to add a question (of type multiple choice, true false or numeric). All questions will be indexed with their position editable by the instructor but not their content. For each question the instructor must add a text for the question, options (4 for MCQs, 2 for True False and none for numeric), expected correct answer (watch out for long expected answers. Sometimes it is better to match an expected option rather than an answer), and a maximum achievable score. On successful creation of the quiz, store its state.

### Student details:
Student can see the list of quizzes available, can attempt any of the quizzes and then see the results.


## Softwares used
- Android Studio v3.0.1 
- Eclipse IDE (Mars.2 - v4.5.2)


## Languages and Frameworks
- Java with Spring Framework
- Kotlin with Android
- XML for configurations and application layouts


## Flow Diagram
* Start
	* Login as
		* Teacher
			* Make quiz
				* add Questions
			* See quizzes
		* Student
			* see available lists
				* Select a quiz to attempt
			* Attempt quiz
				* See results

#### [GitHub Repository Link](https://github.com/uurehman/QuizzerAndroidApplication)


## How to run

### SETTING UP THE SERVER:
1. Open the project in directory named "quizzer_server" as maven project in your IDE (Eclipse, IntelliJ etc.)
2. In your sql settings, make a blank database by the name "quizzerdb"
3. Run your main application class as a java application.
4. To make sure server is working correctly, go to you host address (hostaddress:port/url) e.g 'localhost:8090/users'
5. If server is showing the JSON String, then Well Done... Server is running

### SETTING UP THE ANDROID APPLICATION:

1. Import the android project in your android studio as a graddle project
2. Run on you AVD or any amulator you use
3. On a successful build, very first page will be the sign-in page.
4. Then on upper right corner of your screen, there will be a button "SERVER IP". Click on it and enter the IP address of the machine where server is running.
5. Sign in and enjoy ;)
