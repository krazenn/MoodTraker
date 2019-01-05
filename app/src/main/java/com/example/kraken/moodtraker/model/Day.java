package com.example.kraken.moodtraker.model;


/**
 * @Params Array listDay - content name of day ago
 * @Params int numberDay - number of day to show
 */
public class Day {

    String listDay[] = {"Hier", "Avant hier", "Il y a trois jours","Il y a quatre jours","Il y a cinq jours","Il y a six jours","Il y a une semaine"};
    /*CHANGE NUMBER DAY FOR MORE TICKET COMMENT HISTORY IN RECYCLEVIEW
        NEED ADD DAY IN ARRAY OF CLASS DAY IF MORE THAN 7 DAY*/
    int numberDay = 7 ;

    public String[] getListDay() {
        return listDay;
    }

    public int getNumberDay() {
        return numberDay;
    }


}
