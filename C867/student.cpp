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
                 int Age,
                 int* days)
: student_ID(ID), first_name(fname), last_name(lname), email_addr(email), degree(deg), age(Age), days_remaining(days)
{};

Student::~Student() {
    
};

std::string Student::get_ID() {
    return student_ID;
};

void Student::set_ID(std::string ID) {
    student_ID = ID;
};

std::string Student::get_lname(){
    return last_name;
};

void Student::set_lname(std::string lname){
    last_name = lname;
};

std::string Student::get_fname() {
    return first_name;
};

void Student::set_fname(std::string fname) {
    first_name = fname;
};

std::string Student::get_email() {
    return email_addr;
};

void Student::set_email(std::string email) {
    email_addr = email;
};

Degree Student::get_Degree() {
    return degree;
};

void Student::set_Degree(Degree deg) {
    degree = deg;
};

int Student::get_age() {
    return age;
};

void Student::set_age(int Age) {
    age = Age;
};

int* Student::get_days_rem() {
    return days_remaining;
};

void Student::set_days_rem(int* days) {
    *days_remaining = *days;
};

void Student::print() {
    std::cout << "ID: " << this->get_ID() << std::endl;
    std::cout << "Name: " << this->get_lname() << ", " << this->get_fname() << std::endl;
    std::cout << "Email: " << this->get_email() << std::endl;
    std::cout << "Age: " << this->get_age() << std::endl;
};

void Student::getDegreeProgram() {
    std::cerr << "Error: Degree not available for base type" << std::endl;
};
