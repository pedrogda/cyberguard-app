package dto;


import com.pedroaugusto.cyberguard_app.model.AlertStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertStatusRequest {
    private AlertStatus status;
}
