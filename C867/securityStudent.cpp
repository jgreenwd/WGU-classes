//
//  securityStudent.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "securityStudent.h"

SecurityStudent
::SecurityStudent( std::string ID, std::string fname, std::string lname, std::string email, int age, int* days)
: Student(ID, fname, lname, email, age, days){};

SecurityStudent::~SecurityStudent() {};

Degree SecurityStudent::getDegreeType() {
    return degree_;
};
