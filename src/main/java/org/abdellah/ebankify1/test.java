package org.abdellah.ebankify1;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        Date test = new Date();

        String mtDate = "12/12/24";
        //comparing it with the stirng

        DateFormat df = DateFormat.getDateInstance();
        Date testDate = new Date();
        try {
            testDate = df.parse(mtDate);
        } catch (Exception e) {
            e.printStackTrace();
        }




        System.out.println(test.after(testDate));
    }

}
