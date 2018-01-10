package org.ultralottery

import org.ultralottery.data.WesternStates

public class Main {

    def greet() {
        println "Welcome to UltraLottery"
    }

    def gatherInput() {
        def userInput = [:]
        def configurator = new Configurator()
        userInput.race = configurator.race

        if("AngelesCrest".equals(userInput.race)) {
            userInput.speedInput = configurator.speedInput
            String msg
            if(userInput.speedInput < 5) {
                msg = "sorry, you did not type quickly enough.  " +
                        "Better luck next year"
            } else {
                msg = "congratulations.  you're in.  now just " +
                        "do your trail maintenance work, and be sure " +
                        "to email me 100 times to verify it"
            }
            println msg.toUpperCase()
            System.exit(0)
        }

        userInput.dataType = configurator.dataType
        if("year" == userInput.dataType) {
            userInput.year = configurator.year
        } else { //manual entry
            if(userInput.race == WesternStates) {
                userInput.ticketDistribution = configurator.ticketDistribution
            } else {
                throw new IllegalArgumentException("User-supplied data not supported for " +
                        "non Western States races.")
            }
            //there could be multiple ways to elicit this data from users
            //depending on the race and how tickets are handed out
            //for example, HR is 3 lotteries
        }
        userInput.executions = configurator.executions
        //only ask for runner count if western states
        if(userInput.race == WesternStates) {
            userInput.runnerCount = configurator.runnerCount
        }
        userInput
    }

    def void runLottery(input, k, v) {
        println "Executing $input.executions simulations of $k lottery"
        Lottery lottery = null

        if(input.race == WesternStates) {
            lottery = new Lottery(v)
        } else {
            lottery = new FlatCountLottery(v)
        }

        input.executions.timesTracked {
            lottery.run(input.runnerCount)
        }

        //maybe move to lottery class for analysis
        //or not, because execution count is not part of the lottery class
        lottery.categoryWinBreakdown.keySet().findAll{ it instanceof Integer }.sort().each { y ->
            println("$y $lottery.categoryDisplayName entrants: ${ lottery.getOdds(y, input.executions, v) }%")
        }
        println()

    }

    public static void main(args) {
        def app = new Main()
        app.greet()
        def input = app.gatherInput()

        println input

        def lotteryDatasets = input.race.getDataFor(input.year).yearData
        println "using map $lotteryDatasets from race ${input.race.simpleName}"

        lotteryDatasets.each { k, v  ->
            app.runLottery(input, k, v)
//            Thread.start { app.runLottery(input, k, v) }
        }


    }

}