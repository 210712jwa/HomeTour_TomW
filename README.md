## Revature Project B: HomeTour

### Steps taken to upload from local project:

1. Using the Git Hub Web Client

	create repository through website with just readme file	

2. Using Git Bash on local machine

	cd (to project directory)
	
	example: cd Desktop/Projects

3.  Clone GIT repository created above to local drive GIT project area
	
	git clone https://github.com/tlw8253/HomeTour.git

4.  move project directories and files from develpment area in to local drive GIT project area

5.  cd src
	
### 6.  add directories path and files with git add as in the following example:

	git add docs/..
	git add src/fixtures/..
	git add src/game/..
	git add src/project/..
	
7. Commit the changes adding a comment	
	git commit -m "updates through 20210719 2318"
	
8. Push changes to the repository	
	
	git push

9. Check the status
	
	git status
	
### Steps to take to download from repository to local drive:
	
1. Using Git Bash on local machine
	
	cd (to local project area)
	
	example: cd Desktop/Projects/Git

2. Clone the repository to local drive
	
	git clone https://github.com/tlw8253/HomeTour.git
	
### Steps to take to compile and run project:
	
#### 1. Change directory to each of the package directories and complie in this order with the specific commands:

	cd HomeTour/src/project
	javac Constants.java
	javac Trace.java

	cd HomeTour/src/fixtures
	javac -classpath ../src/:.. Fixture.java
	javac -classpath ../src/:.. Room.java

	cd HomeTour/src/game
	javac -classpath ../src/:.. RoomManager.java
	javac -classpath ../src/:.. Player.java
	javac -classpath ../src/:.. Display.java
	javac -classpath ../src/:.. Main.java
	
#### 2. Change directory to source directory and run Main from game package

	cd HomeTour/src
	java game.Main
	
	
	
	
