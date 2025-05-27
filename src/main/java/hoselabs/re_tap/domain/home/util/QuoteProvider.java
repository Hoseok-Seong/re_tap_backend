package hoselabs.re_tap.domain.home.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hoselabs.re_tap.domain.home.dto.HomeResp;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class QuoteProvider {

    private final List<HomeResp.Quote> quoteList;

    public QuoteProvider() {
        this.quoteList = loadQuotesFromJson();
    }

    public HomeResp.Quote getQuoteByDayOfYear(int dayOfYear) {
        int index = (dayOfYear - 1) % quoteList.size();
        return quoteList.get(index);
    }

    private List<HomeResp.Quote> loadQuotesFromJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/quotes.json");
            if (inputStream == null) throw new IllegalStateException("quotes.json not found");

            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load quotes.json", e);
        }
    }
}

