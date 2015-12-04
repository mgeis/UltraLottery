package org.ultralottery

import org.ultralottery.data.Hardrock
import org.ultralottery.data.WesternStates

import static org.ultralottery.util.ConsoleUtils.readLineWithDefault

/**
 * Created by mgeis on 12/4/15.
 */
class Configurator {
    //could be file or URL driven later, for now it's console driven
    static final YEAR_DEFAULT = "2016" //TODO use enum to get latest
    static final EXECUTIONS_DEFAULT = "10000"
    static final RUNNER_COUNT_DEFAULT = 270 //TODO move to enum for race

    def getDataType() {
        def type = readLineWithDefault("Specific year or user supplied info?: [year]", "year", null).toLowerCase()
        validateMemberOf("data type", type, ["user", "year"])
    }

    def races = ["WS": WesternStates, "HR": Hardrock]

    def getRace() {
        def type = readLineWithDefault("Which race?: [WS]", "WS", null).toUpperCase()
        def code = validateMemberOf("race", type, ["WS", "HR"])
        def foundEnum = races[code]
        foundEnum
    }

    //TODO add support for other lotteries
    def getYear() {
        captureInt("What year do you want to use?: [$YEAR_DEFAULT]", YEAR_DEFAULT, "year")
    }

    def getExecutions() {
        captureInt("How many lottery draws to perform?: [$EXECUTIONS_DEFAULT]", EXECUTIONS_DEFAULT, "executions")
    }

    def getRunnerCount() {
        captureInt("How many runners to draw?: [$RUNNER_COUNT_DEFAULT]", RUNNER_COUNT_DEFAULT.toString(), "runner count")
    }


    //THIS IS VERY WESTERN STATES SPECIFIC
    //FOR NOW, HR WON'T SUPPORT THIS, SO ADD IN "IF RACE == HR, BYPASS"
    def getTicketDistribution() {
        def mapToUse = [:]
        System.out.println("Enter data for each year.  First select the year (1, 2, 3, etc.) " +
                "then how many runners have entered for that year.  \nTo correct errors, just enter the year/runner count" +
                " info again.  Enter 'DONE' to finish.")
        while (true) {
            def year = readLine("Enter runner year category: ")
            if("DONE" == year.toUpperCase()) {
                break
            }
            def y = validateInt("year", year)
            def count = readLine("Enter runner count: ")
            if("DONE" == count.toUpperCase()) {
                break
            }
            def c = validateInt("count", count)

            mapToUse[y] = c //TODO catch bad format, ask again
        }
        mapToUse
    }


    def captureInt(prompt, defaultValue, label) {
        def retval = readLineWithDefault(prompt, defaultValue, null)
        validateInt(label, retval)
    }

    def validateInt(label, String value) {
        if(!value.isInteger()) {
            throw new IllegalArgumentException("$value is not a valid value for $label.")
        }
        value.toInteger()
    }

    def validateMemberOf(label, value, List allowed) {
        if(!allowed.contains(value)) {
            throw new IllegalArgumentException("$value is not a valid value for $label.  Allowed options are $allowed")
        }
        value
    }
}
