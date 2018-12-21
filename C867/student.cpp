//
//  student.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "student.h"
#include "degree.h"

Student::Student(std::string ID,
                 std::string fname,
                 std::string lname,
                 std::string email,
                 Degree deg,
                 int age,
                 int* days)
        : student_ID_(ID), first_name_(fname), last_name_(lname), email_address_(email),
          degree_program_(deg), age_(age), number_of_days_(days) {};

Student::~Student() {};

std::string Student::getStudentID() {
    return student_ID_;
};

void Student::setStudentID(std::string ID) {
    student_ID_ = ID;
};

std::string Student::getLastName(){
    return last_name_;
};

void Student::setLastName(std::string lname){
    last_name_ = lname;
};

std::string Student::getFirstName() {
    return first_name_;
};

void Student::setFirstName(std::string fname) {
    first_name_ = fname;
};

std::string Student::getEmailAddress() {
    return email_address_;
};

void Student::setEmailAddress(std::string email) {
    email_address_ = email;
};

void Student::getDegreeProgram() {
    std::cerr << "Error: Degree not available for base type" << std::endl;
};

void Student::setDegreeProgram() {};

int Student::getAge() {
    return age_;
};

void Student::setAge(int age) {
    age_ = age;
};

int* Student::getNumberOfDays() {
    return number_of_days_;
};

void Student::setNumberOfDays(int* days) {
    *number_of_days_ = *days;
};

void Student::print() {
    std::cout << this->getStudentID() << "\t First Name: " << this->getFirstName() << "\t Last Name: " << this->getLastName();
    std::cout << "\tAge: " << this->getAge() << "\t daysInCourse: " << this->getNumberOfDays();
    std::cout << "\t Degree Program: " << "<degree program function>" << std::endl;
};
