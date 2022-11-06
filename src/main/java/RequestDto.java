import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestDto {

    @JsonProperty("title")
    private String title;
    @JsonProperty("date")
    private String date;
    @JsonProperty("sum")
    private long sum;

    public static RequestDto parseInputJson(String input) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(input, RequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error while reading Json: %s", e.getMessage()));
        }
    }
}
