package org.ultralottery.util

/**
 * Created by mgeis on 12/3/15.
 */
class ConsoleUtils {

    static {
        Integer.metaClass.timesTracked { Closure work ->
            def start = new Date()
            int complete = 0;
            int increment = 5
            print "[$complete%]"
            int runCount = delegate

            runCount.times {
                int iteration = it
                work()
                double nowComplete =  Math.ceil((double)iteration / (double)runCount * 100);
                if(nowComplete > complete && nowComplete % increment == 0) {
                    complete = (int)nowComplete;
                    print "\r[$complete%] ${computeRemainingTime(start, iteration, runCount)} sec remaining"
                }
            }
            println "\r[100%] finished"


        }
    }

    def static computeRemainingTime(start, i, runs) {
        def now = new Date()
        def elapsed = now.time - start.time
        def percentComplete = (double)i / (double)runs
        def totalEstimated = elapsed / percentComplete
        sprintf("%.2f", (totalEstimated - elapsed) / 1000)
    }

    def static String readLine(String format, Object... args) {
        readLineWithDefault(format, null, args)
    }

    def static String readLineWithDefault(String format, String defaultVal, Object... args) {
        def captured
        if (System.console() != null) {
            captured = System.console().readLine(format, args).trim();
        } else {
            print String.format(format, args)
            captured = System.in.newReader().readLine().trim()
        }
        return captured ?: defaultVal
    }


}
