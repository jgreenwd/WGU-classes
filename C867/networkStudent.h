//
//  networkStudent.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef networkStudent_h
#define networkStudent_h

#include "student.h"

/* -------- D.3 -------- */
class NetworkStudent : public Student {
    private:
        Degree degree_ = NETWORK;
    public:
        using Student::Student;
    
        // redundant constructor to comply with D.2.c
        NetworkStudent(string, string, string, string, int, int*, Degree);
        ~NetworkStudent();
    
        Degree getDegreeProgram() override;
        void print() override;
};

#endif /* networkStudent_h */
