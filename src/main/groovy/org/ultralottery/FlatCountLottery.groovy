package org.ultralottery

/**
 * Created by mgeis on 12/4/15.
 */
class FlatCountLottery extends Lottery {

    def FlatCountLottery(input) {
        super(input)
    }

    protected def ticketCount(numTickets) {
        numTickets
    }

    protected def getCategoryDisplayName() {
        "ticket"
    }
}
