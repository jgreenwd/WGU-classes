# Scripting & Programming Applications Project: Platform Migration Tool

This project served as an introduction to Object-Oriented Programming. It emphasizes correct usage of control structures, arrays, and pointers.


## SCENARIO

You are hired as a contractor to help a university migrate an existing student system to a new platform using C++ language. Since the application already exists, its requirements exist as well, and they are outlined in the next section. You are responsible for implementing the part of the system based on these requirements. A list of data is provided as part of these requirements. This part of the system is responsible for reading and manipulating the provided data.<br><br>
You must write a program containing five classes (i.e.,  Student , SecurityStudent , NetworkStudent , SoftwareStudent , and  Roster ). The program will maintain a current roster of students within a given course. Student data for the program includes student ID, first name, last name, email address, age, an array of the number of days to complete each course, and degree. This information can be found in the studentData table below. The program will read a list of five students and use function calls to manipulate data (see part F4 in the requirements below). While parsing the list of data, the program should create student objects using the appropriate subclasses indicated by the degree program. The entire student list will be stored in one array of students called  classRosterArray Specific data-related output will be directed to the console. 


## REQUIREMENTS

WITHOUT USING ANY EXTERNAL LIBRARIES -
Create a program that converts the array of strings found in the StudentData table to an array of student objects by doing the following:

##### A.  Modify the studentData table to include your personal information as the last item.

##### B.  Create a C++ project in your integrated development environment (IDE) with the following files:
  * degree.h
  * student.h and student.cpp
  * networkStudent.h and networkStudent.cpp
  * securityStudent.h and securityStudent.cpp
  * softwareStudent.h and softwareStudent.cpp
  * roster.h and roster.cpp
 
##### C.  Define an enumerated data type Degree for the degree programs containing the following data elements SECURITY, NETWORKING and SOFTWARE.
 
##### D.  For the Student class, do the following:
  1.  Create the base class Student in the files student.h and student.cpp, which includes each  of the following variables:
      * student ID
      * first name
      * last name
      * email address
      * age
      * array of number of days to complete each course
      * degree types
 
  2.  Create each of the following functions in the Student class:
      * an accessor (i.e., getter) for each  instance variable from part D1
      * a mutator (i.e., setter) for each  instance variable from part D1
      * constructor using all  of the input parameters provided in the table
      * virtual print() to print specific student data 
      * destructor
      * virtual getDegreeProgram()

  3.  Create the three following classes as subclasses of Student, using the files created in part B:
      * SecurityStudent
      * NetworkStudent
      * SoftwareStudent

  Each subclass should override the getDegreeProgram() function. Each  subclass should have a data member to hold the enumerated type for the degree program using the types defined in part C.
 

##### E.  Create a Roster class (roster.cpp) by doing the following:

  1.  Create an array of pointers, classRosterArray, to hold the data provided in the studentData table.

  2.  Create a student object for each  student in the data table by using the subclasses NetworkStudent, SecurityStudent, and SoftwareStudent, and populate classRosterArray.
      *  Apply pointer operations when parsing each  set of data identified in the studentData table.
      *  Add each student object to classRosterArray.

  3.  Define the following functions:
      *  public void add(string studentID, string firstName, string lastName, string emailAddress, int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, <degree program>) ''' set the instance variables from part D1 and update the roster '''
      *  public void remove(string studentID) ''' remove students from the roster by student ID. If the student ID does not exist, print an error message indicating that the student was not found. '''
      *  public void printAll() 
              ''' Print a complete tab-separated list of student data using accessor functions with the provided format:
              1 [tab] First Name: John [tab] Last Name: Smith [tab] Age: 20 [tab]daysInCourse: {35, 40, 55} Degree Program: Security. 
              Loop through all students in classRosterArray and call the print() function for each student. '''
      *  public void printDaysInCourse(string studentID) ''' Correctly print a student’s average number of days in the three courses. The student is identified by the studentID parameter. '''
      *  public void printInvalidEmails() ''' Verify student email addresses and display all invalid email addresses to the user '''
      *  public void printByDegreeProgram(int degreeProgram) ''' Print student information for a degree program specified by an enumerated type '''

__Note: A valid email should include an at sign ('@') and period ('.') and should not include a space (' ').__

##### F.  Demonstrate the program’s required functionality by using a void main() function, which will contain the required function calls to achieve the following results:
  1.  Print out to the screen, via your application, the course title, the programming language used, your student ID, and your name.
  2.  Create an instance of the Roster class called classRoster.
  3.  Add each student to classRoster.
  4.  Convert the following pseudo code to complete the rest of the main() function:
```
    classRoster.printAll();
    classRoster.printInvalidEmails();
    //loop through classRosterArray and for each element:
    classRoster.printAverageDaysInCourse(/*current_object's student id*/);
    classRoster.printByDegreeProgram(SOFTWARE);
    classRoster.remove("A3");
    classRoster.remove("A3");
    //expected: the above line should print a message saying such a student with this ID was not found.
```
  5.  Call the destructor to release the Roster memory.


## STUDENT DATA

| Student ID	| First Name |	Last Name |	Email |	Age |	Days in Course	| Degree |
|:----------- | ----------:|:--------- | ----- | --- | -------------- | ------ |
| A1	| John	|	Smith	|	John1989@gm ail.com	|	20	|	30, 35, 40 |	SECURITY |
| A2	| Suzan	|	Erickson	|	Erickson_1990@gmailcom	|	19	|	50, 30, 40 |	NETWORK |
| A3	| Jack	|	Napoli	|	The_lawyer99yahoo.com	|	19	|	20, 40, 33 |	SOFTWARE |
| A4	| Erin	|	Black	|	Erin.black@comcast.net	|	22	|	50, 58, 40 |	SECURITY |
| A5	| _my name_	| _my name_	| _my email address_ |	_my age_	| _Number of days to complete 3 courses_	| SOFTWARE |


## RESULT

![](https://github.com/jgreenwd/WGU-classes/blob/master/C867/result.png)
