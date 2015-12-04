package org.ultralottery.data
/**
 * Created by mgeis on 12/3/15.
 */
public enum WesternStates {

    WS_2015( ["ALL" : [1:1427, 2:641, 3:281, 4:136, 5:57, 6:24, "LIMIT":270]]),
    WS_2016( ["ALL" : [1:2233, 2:639, 3:377, 4:171, 5:71, 6:14, 7:5, "LIMIT": 270]]);

    def static  yearMap = [2015 : WS_2015, 2016 : WS_2016]

    def static getDataFor(year) {
        yearMap[year]
    }

    def static getCode() {
        "WS"
    }

    WesternStates(data){
        this.yearData = data
    }

    def yearData;

}
