/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package controller;

import c195.C195;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import lib.LocalDB;
import model.Appointment;

public class ModalViewController {
    @FXML public TextArea modalDataArea = new TextArea();
    
    public final void load(String args) {
        modalDataArea.clear();
        
        switch (args) {
            
            case "weekly": { 
                modalDataArea.appendText("------------------ Upcoming Weekly Schedule for " 
                        + C195.user.getUsername() + " ------------------\n");
                modalDataArea.appendText("Customer: \t\tTime: \tDate: \t\tLocation: "
                        + "\t\tTitle: \tType: \n");
                
                LocalDB.getAllAppointments()
                        .stream()
                        .filter(a -> a.getContact().equals(C195.user.getUsername()))
                        .filter(a -> a.getStart().isAfter(LocalDateTime.now()))
                        .filter(a -> a.getStart().isBefore(LocalDateTime.now().plusWeeks(1)))
                        .sorted(Comparator.comparing(Appointment::getStart))
                        .forEachOrdered(a -> modalDataArea.appendText(
                                a.getCustomerName() + "\t" + a.getStartTime() + 
                                "\t" + a.getDate() + "\t" + a.getLocation() + "\t" +
                                a.getTitle() + "\t" + a.getType() + "\n"
                        ));
                } break;
            
                
            case "monthly": { 
                modalDataArea.appendText("------------------ Upcoming Monthly Schedule for " 
                        + C195.user.getUsername() + " ------------------\n");
                modalDataArea.appendText("Customer: \t\tTime: \tDate: \t\tLocation: "
                        + "\t\tTitle: \tType: \n");
                
                LocalDB.getAllAppointments()
                        .stream()
                        .filter(a -> a.getContact().equals(C195.user.getUsername()))
                        .filter(a -> a.getStart().isAfter(LocalDateTime.now()))
                        .filter(a -> a.getStart().isBefore(LocalDateTime.now().plusMonths(1)))
                        .sorted(Comparator.comparing(Appointment::getStart))
                        .forEachOrdered(a -> modalDataArea.appendText(
                                a.getCustomerName() + "\t" + a.getStartTime() + 
                                "\t" + a.getDate() + "\t" + a.getLocation() + "\t" +
                                a.getTitle() + "\t" + a.getType() + "\n"
                        ));
                } break;
            
                
            // There is definitely a more functional way to code this, but it is
            // beyond my current knowledge. I suspect it would involve sending
            // a sorted stream to a map, and collecting a reduction?
            case "appointments": {
                modalDataArea.appendText("------------------ Number of Appointment "
                        + "Types by Month for All Users ------------------\n");
                
                List<Appointment> list = 
                    LocalDB.getAllAppointments()
                        .stream()
                        .sorted(Comparator.comparing(Appointment::getStart))
                        .collect(Collectors.toList());

                int count = 1;
                for(int i = 0; i<list.size()-1; i++) {
                    if (list.get(i).getStart().getMonth() == list.get(i+1).getStart().getMonth() &&
                        list.get(i).getStart().getYear() == list.get(i+1).getStart().getYear() &&
                        list.get(i).getType().equals(list.get(i+1).getType())) {
                        ++count;
                    } else {
                        modalDataArea.appendText(list.get(i).getStart().getMonth()
                                .toString().substring(0,3) + "\t" + 
                                list.get(i).getStart().getYear() + "\t" + count + 
                                "\t" +list.get(i).getType() + "\n");
                        count = 1;
                    }
                }
                
                } break;
            
                
            case "consultants": {
                modalDataArea.appendText("------------------ Weekly Schedule for "
                        + "Each Consultant ------------------\n");
                modalDataArea.appendText("Consultant: \tCustomer: \tTime: \tDate: \t\tLocation: "
                        + "\t\tTitle: \tType: \n");
                
                LocalDB.getAllAppointments()
                        .stream()
                        .filter(a -> a.getStart().isAfter(LocalDateTime.now()))
                        .filter(a -> a.getStart().isBefore(LocalDateTime.now().plusWeeks(1)))
                        .sorted(Comparator.comparing(Appointment::getContact)
                        .thenComparing(Appointment::getStart))
                        .forEachOrdered(a -> modalDataArea.appendText(
                                a.getContact() + "\t\t" +
                                a.getCustomerName() + "\t" + a.getStartTime() + 
                                "\t" + a.getDate() + "\t" + a.getLocation() + "\t" +
                                a.getTitle() + "\t" + a.getType() + "\n"
                        ));
                } break;
            
                
            case "customers": {
                modalDataArea.appendText("------------------ Weekly Schedule for "
                        + "All Customers ------------------\n");
                modalDataArea.appendText("Customer: \t\tTime: \tDate: \t\tLocation: "
                        + "\t\tTitle: \tType: \n");
                
                LocalDB.getAllAppointments()
                        .stream()
                        .filter(a -> a.getStart().isAfter(LocalDateTime.now()))
                        .filter(a -> a.getStart().isBefore(LocalDateTime.now().plusWeeks(1)))
                        .sorted(Comparator.comparing(Appointment::getCustomerName)
                        .thenComparing(Appointment::getStart))
                        .forEachOrdered(a -> modalDataArea.appendText(
                                a.getCustomerName() + "\t\t" + a.getStartTime() + 
                                "\t" + a.getDate() + "\t" + a.getLocation() + "\t" +
                                a.getTitle() + "\t" + a.getType() + "\n"
                        ));
                } break;
        }
    }
}
