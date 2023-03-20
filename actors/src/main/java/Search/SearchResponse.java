package Search;

import java.util.List;

public class SearchResponse {
    private final String apiName;
    private final List<String> responses;

    public SearchResponse(String apiName, List<String> responses) {
        this.apiName = apiName;
        this.responses = responses;
    }

    public String getApiName() {
        return apiName;
    }

    public List<String> getResponses() {
        return responses;
    }
}
