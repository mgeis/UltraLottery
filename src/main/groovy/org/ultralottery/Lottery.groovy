package org.ultralottery

public class Lottery {
   //TODO some types of lottery may be different,
   // so there could be use of Strategy GOF pattern,
   // and this could be abstract or an interface

    def hat = [] //list of Ticket
    def categoryWinBreakdown = [:]
    def limit = 0

    def Lottery(Map input) {
        buildHat(input)
        limit = input.LIMIT

        input.keySet().each { y ->
            categoryWinBreakdown.put(y, 0)
        }
    }

    def buildHat(mapToUse) {
        mapToUse.findAll{ it.key instanceof Integer }.each { year, entrantCountForCategory ->
            entrantCountForCategory.times {//for each runner in the category
                addToHat(UUID.randomUUID().toString(), year)
            }
        }
    }

    def addToHat(runnerId, year) {
        ticketCount(year).times { hat << new Ticket(runnerId, year) }
    }

    //TODO refactor this out, make it abstract using a strategy
    protected def  ticketCount(year) {
        (2**(year-1))
    }

    def Set run(runnersToDraw) {
        def drawnTickets = [] as Set
        def drawnNames = [] as Set
        def tempHat = []
        tempHat.addAll(hat)

        def max = runnersToDraw ?: limit

        Collections.shuffle(tempHat) //TODO verify it's shuffled enough
        //println "before lottery there are ${hat.size} tickets in the hat"


        while(drawnTickets.size() < max && !tempHat.isEmpty()) {
            Ticket drawnTicket = tempHat.pop()
            if(!drawnNames.contains(drawnTicket.runnerId)) {
                drawnNames << drawnTicket.runnerId
                drawnTickets << drawnTicket
            }
        }
        analyzeWinners(drawnTickets)
        return drawnTickets
    }

    protected def getCategoryDisplayName() {
        "year"
    }

    def getOdds(int year, runs, mapToUse) {
        sprintf("%.3f", categoryWinBreakdown.get(year) / runs / mapToUse.get(year) * 100)
    }

    def analyzeWinners(winners) {
        winners.each { ticket ->
            def currentWinCount = categoryWinBreakdown[ticket.category]
            categoryWinBreakdown[ticket.category] = ++currentWinCount
        }
    }



}