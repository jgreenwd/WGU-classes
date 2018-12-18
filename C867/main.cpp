//
//  main.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/16/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include <iostream>
#include "degree.h"
#include "student.h"
using namespace std;

int main() {
    std::string ID = "A1", fname = "Bob", lname = "Saget", email = "Bob@Saget.com";
    int age = 21;
    int days[] = {1490};
    Degree degree = NETWORK;
    
    Student d("A1", "Bob", "Saget", "Bob@Saget.com", age, days, degree);

    cout << "ID: " << d.get_stu_ID() << endl;
    cout << "Name: " << d.get_lname() << ", " << d.get_fname() << endl;
    cout << "Email: " << d.get_email() << endl;
    cout << "Age: " << d.get_age() << endl;
    cout << "Days remaining: " << d.get_days_rem() << endl;
    cout << "Degree: " << d.get_deg() << endl;
    
    return 0;
}
