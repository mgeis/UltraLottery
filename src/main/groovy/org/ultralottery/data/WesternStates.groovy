package org.ultralottery.data
/**
 * Created by mgeis on 12/3/15.
 */
public enum WesternStates {

    WS_2015( ["ALL" : [1:1427, 2:641, 3:281, 4:136, 5:57, 6:24, "LIMIT":270]]),
    WS_2016( ["ALL" : [1:2233, 2:639, 3:377, 4:171, 5:71, 6:14, 7:5, "LIMIT": 270]]),
    WS_2017( ["ALL" : [1:2427, 2:1023, 3:397, 4:256, 5:112, 6:31, 7:2, "LIMIT": 270]]),
    WS_2018( ["ALL" : [1:2658, 2:1060, 3:668, 4:283, 5:161, 6:71, 7:8, "LIMIT": 270]]),
    WS_2019( ["ALL" : [1:3113, 2:1281, 3:697, 4:455, 5:191, 6:95, 7:30, "LIMIT": 270]]),
    WS_2020( ["ALL" : [1:3265, 2:1447, 3:907, 4:547, 5:314, 6:127, 7:54, 8:9, "LIMIT": 270]]);

    def static  yearMap = [2015 : WS_2015, 2016 : WS_2016, 2017 : WS_2017, 2018 : WS_2018, 2019 : WS_2019, 2020 : WS_2020]

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
