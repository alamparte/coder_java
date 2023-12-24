package final_invoice.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ClientDTO {
    private Integer id;
    private String completeName;
    private String docNumber;
    @JsonIgnoreProperties("clientId")
    private List<Integer> invoicesId = new ArrayList<>();
}
