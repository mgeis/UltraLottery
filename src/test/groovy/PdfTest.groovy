import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfReaderContentParser
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy
import com.itextpdf.text.pdf.parser.TextExtractionStrategy
import org.junit.Test


class PdfTest {
    def neverTicketMap = [:] //47 drawn
    def veteranTicketMap = [:] //70 drawn
    def othersTicketMap = [:] //35 drawn
    //TODO winners auto entry, slots they would come from are reduced by 1
    //for 2016, mens 2015winner Killian Jornet not on ticket list
    //for 2016, womens 2015 winner Anna Frost on others list

    def ticketMaps = ["Never":neverTicketMap, "Vet":veteranTicketMap, "Else":othersTicketMap]

    def increment(category, key) {
        def map = ticketMaps[category]
        map[key] =  ++map.computeIfAbsent(key, {0})
    }

    @Test
    def void tryThis() {
        def reader = new PdfReader("./src/test/resources/hrtickets.pdf")
        def parser = new PdfReaderContentParser(reader)
        TextExtractionStrategy strategy
        def headersSeen = false
        for(int i =  1; i <= reader.numberOfPages; i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy())
            String pageText =  strategy.getResultantText()

            pageText.splitEachLine(" ") { tokens ->
                def last = tokens.last()
                if(!headersSeen && "tickets" == last) {
                    headersSeen = true
                }
                if(tokens.size() == 8 && headersSeen) {
                    def category = tokens[2]
                    int ticketCategory = last as Integer
                    increment(category, ticketCategory)
                }
            }

        }
        println "Never: $neverTicketMap"
        analyzeMap(neverTicketMap)
        println()

        println "Vet: $veteranTicketMap"
        analyzeMap(veteranTicketMap)
        println()

        println "Else: $othersTicketMap"
        analyzeMap(othersTicketMap)
        println()

    }

    def void analyzeMap(map) {
        def total = map.inject(0) { sum, k, v -> sum += k*v }
        println "$total tickets in hat"
        map.keySet().sort().each {
            println ("$it tickets: ${map[it]}")
        }

    }

    //152 will be drawn
}
