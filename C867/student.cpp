//
//  student.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "student.h"
#include "degree.h"

/* -------- CONSTRUCTOR D.2.c -------- */
Student::Student(){};

Student
::Student(std::string ID, std::string fname, std::string lname, std::string email, int age, int* days)
: student_ID_(ID), first_name_(fname), last_name_(lname), email_address_(email), age_(age), number_of_days_(days) {};

/* -------- DESTRUCTOR D.2.e -------- */
Student::~Student() {};


/* --------- ACCESSORS D.2.a -------- */
std::string Student::getStudentID() {
    return student_ID_;
};

std::string Student::getFirstName() {
    return first_name_;
};

std::string Student::getLastName(){
    return last_name_;
};

std::string Student::getEmailAddress() {
    return email_address_;
};

int Student::getAge() {
    return age_;
};

// Return pointer to array[3] of number of days to complete each course
int* Student::getNumberOfDays() {
    return number_of_days_;
};


/* -------- MUTATORS D.2.b -------- */
void Student::setStudentID(std::string ID) {
    student_ID_ = ID;
};

void Student::setFirstName(std::string fname) {
    first_name_ = fname;
};

void Student::setLastName(std::string lname){
    last_name_ = lname;
};

void Student::setEmailAddress(std::string email) {
    email_address_ = email;
};

void Student::setAge(int age) {
    age_ = age;
};

void Student::setNumberOfDays(const int* days) {
    *number_of_days_ = *days;
};

/* -------- D.2.f -------- */
Degree Student::getDegreeProgram() {
    std::cerr << "Error: Degree not available in base type" << std::endl;
    return ERROR;
};

void Student::setDegreeProgram() {
    std::cerr << "Error: Degree not available in base type" << std::endl;
};

/* -------- D.2.d -------- */
void Student::print() {};
