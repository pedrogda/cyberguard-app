package dto;


import com.pedroaugusto.cyberguard_app.model.AlertStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertStatusRequest {

    @NotNull(message = "Status is required")
    private AlertStatus status;
}