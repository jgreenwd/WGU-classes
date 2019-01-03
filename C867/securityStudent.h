//
//  securityStudent.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef securityStudent_h
#define securityStudent_h

#include "student.h"

/* -------- D.3 -------- */
class SecurityStudent : public Student {
    private:
        Degree degree_ = SECURITY;
    public:
        using Student::Student;
    
        // redundant constructor to comply with D.2.c
        SecurityStudent(std::string, std::string, std::string, std::string, int, int*, Degree);
        ~SecurityStudent();
    
        Degree getDegreeProgram() override;
        void print() override;
};

#endif /* securityStudent_h */
