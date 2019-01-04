//
//  student.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "student.h"

/* -------- CONSTRUCTOR D.2.c -------- */
Student::Student(string ID, string fname, string lname, string email, int age, int* days, Degree degree)
: student_ID_(ID), first_name_(fname), last_name_(lname), email_address_(email), age_(age), number_of_days_(days), degree_(degree) {};

/* -------- DESTRUCTOR D.2.e -------- */
Student::~Student() {};


/* --------- ACCESSORS D.2.a -------- */
string Student::getStudentID() {
    return student_ID_;
};

string Student::getFirstName() {
    return first_name_;
};

string Student::getLastName() {
    return last_name_;
};

string Student::getEmailAddress() {
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
void Student::setStudentID(string ID) {
    student_ID_ = ID;
};

void Student::setFirstName(string fname) {
    first_name_ = fname;
};

void Student::setLastName(string lname){
    last_name_ = lname;
};

void Student::setEmailAddress(string email) {
    email_address_ = email;
};

void Student::setAge(int age) {
    age_ = age;
};

void Student::setNumberOfDays(const int* days) {
    *number_of_days_ = *days;
};

/* -------- D.2.d -------- */
void Student::print() {};
