package org.ultralottery

/**
 * Created by mgeis on 12/4/15.
 */
class Ticket {
    String runnerId
    int category //we need this for analyzing the odds of being drawn

    Ticket(r, c) {
        runnerId = r
        category = c
    }
}
