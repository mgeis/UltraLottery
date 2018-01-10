package org.ultralottery.util

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.stream.Collectors

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

    def static String readTimed(String format, int delay) {
        def captured
        print String.format(format)
        BufferedReader br

        if (System.console() != null) {
            br = new BufferedReader(System.console().reader())
        } else {
            br = new BufferedReader(System.in.newReader())
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor()
        def c = {
            Thread.sleep(5000);
            char[] cbuf = new char[8192]
            def txt = br.read(cbuf)
            br.close()
            captured = new String(cbuf).trim()
            return captured
        } as Callable<String>

        executorService.invokeAll([c])
        return captured
    }

    static class TypingTimer {
        Timer timer
        BufferedReader br
        String target

        public TypingTimer(int delay, BufferedReader br, String target) {
            this.br = br
            this.target = target
            timer = new Timer();  //At this line a new Thread will be created
            timer.schedule(new EndTypingTask(), delay); //delay in milliseconds
        }

        class EndTypingTask extends TimerTask {
            @Override
            public void run() {
                System.out.println("EndTypingTask is completed by Java timer");
                target = br.lines().collect(Collectors.joining("\n"))
                timer.cancel(); //Not necessary because we call System.exit
                //System.exit(0); //Stops the AWT thread (and everything else)
            }

        }
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
