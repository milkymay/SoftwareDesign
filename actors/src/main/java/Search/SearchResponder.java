package Search;

import java.util.List;

public class SearchResponder {
    private final String apiName;
    private final List<String> topResponses;

    public SearchResponder(String apiName, List<String> topResponses) {
        this.apiName = apiName;
        this.topResponses = topResponses;
    }

    public String getApiName() {
        return apiName;
    }

    public List<String> getResponses() {
        return topResponses;
    }
}
