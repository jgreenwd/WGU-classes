//
//  softwareStudent.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef softwareStudent_h
#define softwareStudent_h

#include "student.h"

/* -------- D.3 -------- */
class SoftwareStudent : public Student {
    public:
        SoftwareStudent(string, string, string, string, int, int*, Degree = SOFTWARE);
        ~SoftwareStudent();
    
        Degree getDegreeProgram() override;
        void print() override;
};

#endif /* softwareStudent_h */
