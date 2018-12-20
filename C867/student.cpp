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
: student_ID_(ID), first_name_(fname), last_name_(lname), email_address_(email), degree_(deg), age_(age), number_of_days_(days)
{};

Student::~Student() {
    
};

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
    std::cout << "ID: " << this->getStudentID() << std::endl;
    std::cout << "Name: " << this->getLastName() << ", " << this->getFirstName() << std::endl;
    std::cout << "Email: " << this->getEmailAddress() << std::endl;
    std::cout << "Age: " << this->getAge() << std::endl;
    int *arr = { this->getNumberOfDays() };
    for (int i = 0; i<3;i++) {
        std::cout << "Days remaining in class(" << i << "): " << arr[i] << std::endl;
    }
};
