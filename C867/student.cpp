//
//  student.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "student.h"
#include "degree.h"
using std::string;

Student(std::string student_ID = "ID", std::string lname = "last name", std::string fname = "first name",
        std::string email = "student@school.edu", Degree degree = SOFTWARE, int age = 18, int[] = [1460]) {
};

~Student(){
    
};

std::string Student::get_ID(void) {
    return student_ID;
};

void Student::set_ID(std::string ID) {
    student_ID = ID;
};

std::string Student::get_lname(void){
    return last_name;
};

void Student::set_lname(std::string lname){
    last_name = lname;
};

std::string Student::get_fname(void) {
    return first_name;
};

void Student::set_fname(std::string fname) {
    first_name = fname;
};

std::string Student::get_email(void) {
    return email_addr;
};

void Student::set_email(std::string email) {
    email_addr = email;
};

Degree Student::get_Degree(void) {
    return degree;
};

void Student::set_Degree(Degree deg) {
    degree = deg;
};

int Student::get_age(void) {
    return age;
};

void Student::set_age(int Age) {
    age = Age;
};

int Student::get_days_rem(void) {
    return *days_remaining;
};

void Student::set_days_rem(int[] days) {
    days_remaining = *days;
};

