package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditEntry implements Serializable {
    private String action;
    private LocalDateTime timestamp;

    public AuditEntry(String action, LocalDateTime timestamp) {
        this.action = action;
        this.timestamp = timestamp;
    }
    public AuditEntry(String action) {
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "AuditEntry\n" + "action: " + action + "\ntimestamp: " + timestamp + "\n";
    }
}
