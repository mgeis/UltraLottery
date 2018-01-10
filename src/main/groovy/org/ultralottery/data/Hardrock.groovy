package org.ultralottery.data
/**
 * Created by mgeis on 12/3/15.
 */
public enum Hardrock {



    HR_2016( [
        "NEVER" : [1:533, 2:338, 16:69, 8:90, 4:216, 32:18, 64:7, 128:1, 256:1, "LIMIT":47],
        "VET": [11:2, 21:1, 10:3, 7:7, 9:2, 5:9, 8:4, 6:7, 14:3, 19:1, 13:1, 18:2, 23:1, "LIMIT":35],
        "ELSE": [2:47, 4:42, 3:36, 5:31, 1:4, 7:7, 6:11, 11:1, 8:2, 9:2, "LIMIT":70]
    ] ),

    HR_2017( [
        "NEVER" : [1:736, 2:435, 4:252, 8:177, 16:73, 32:42, 64:10, 128:1, "LIMIT":42],
        "VET": [5:8, 6:5, 7:5, 8:9, 9:2, 10:2, 11:2, 12:2, 13:1, 14:1, 15:2, 19:1, 21:1, 22:1, 24:1, "LIMIT":33],
        "ELSE": [1:10, 2:43, 3:50, 4:43, 5:29, 6:18, 7:3, 8:3, 10:1, 19:1, "LIMIT":62]
    ] )
,

    def static  yearMap = [2016 : HR_2016, 2017 : HR_2017]

    def static getDataFor(year) {
        yearMap[year]
    }

    def static getCode() {
        "HR"
    }

    Hardrock(data){
        this.yearData = data

    }
    def yearData; //map of maps

}
