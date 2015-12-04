package org.ultralottery.deprecated

import java.util.concurrent.atomic.AtomicInteger

public class FullLottery {
    def static ticketMap = [1:2233, 2:639, 3:377, 4:171, 5:71, 6:14, 7:5]//this map leaves me out
    def static masterHat = []

    public FullLottery() {

    }

    def static ticketCount(year) {
        (2**(year-1))
    }

    def static addToHat(runnerId, year) {
        ticketCount(year).times { masterHat << new Ticket(runnerId, year) }
    }

    def static buildHat(yearNumber) {
        FullLottery.ticketMap.each { year, entrantCountForCategory ->
            entrantCountForCategory.times {//for each runner in the category
                addToHat(UUID.randomUUID().toString(), year)
            }
        }
    }

    static class Ticket {
        String runnerId
        int year

        Ticket(r, y) {
            runnerId = r
            year = y
        }
    }

    def static categoryWinBreakdown = [:]
    def static main(args) {
        def lottery = new FullLottery()
        def winners = []
        def runs = 100000
        def yearNumber = 2
        def limit = 270

        buildHat(yearNumber)

        (1..7).each { y ->
            categoryWinBreakdown.put(y, new AtomicInteger(0))
        }

        def start = new Date()
        runs.times {
            winners = lottery.run(yearNumber, limit)
            lottery.analyzeWinners(winners)
        }

        (1..7).each { y ->
            println("$y year entrants: ${ lottery.getOdds(y, runs) }%")
        }
        def end = new Date()
        println ("${end.time - start.time} ms")
    }

    def getOdds(int year, runs) {
        (categoryWinBreakdown.get(year).get() / runs / ticketMap.get(year) * 100)
    }

    def analyzeWinners(winners) {
        winners.each { ticket ->
            categoryWinBreakdown.get(ticket.year).incrementAndGet()
        }
    }


    def Set run(year, limit) {
        def drawnTickets = [] as Set
        def drawnNames = [] as Set
        def hat = []
        hat.addAll(FullLottery.masterHat)

        Collections.shuffle(hat)
        //println "before lottery there are ${hat.size} tickets in the hat"


        while(drawnTickets.size() < limit) {
            def drawnTicket = hat.pop()
            if(!drawnNames.contains(drawnTicket.runnerId)) {
                drawnNames << drawnTicket.runnerId
                drawnTickets << drawnTicket
            }
        }
        return drawnTickets
    }
}