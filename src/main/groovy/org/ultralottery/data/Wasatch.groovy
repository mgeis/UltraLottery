package org.ultralottery.data
/**
 * Created by mgeis on 12/3/15.
 */
public enum Wasatch {


    WASATCH_2018( ["ALL" : [1:542, 2:36, 3:7, "LIMIT": 363]]);

    def static  yearMap = [2018 : WASATCH_2018]

    def static getDataFor(year) {
        yearMap[year]
    }

    def static getCode() {
        "WASATCH"
    }

    Wasatch(data){
        this.yearData = data
    }

    def yearData;

}
