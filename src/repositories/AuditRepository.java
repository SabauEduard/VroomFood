package repositories;

import models.AuditEntry;

import java.util.ArrayList;
import java.util.List;

public class AuditRepository extends GenericRepository<AuditEntry>{
    public AuditRepository(List<AuditEntry> objectList) {
        super(objectList);
    }
    public AuditRepository() {
        super();
    }
    public List<AuditEntry> getAuditEntryByAction(String action) {
        List<AuditEntry> auditEntryList = new ArrayList<>();
        for (AuditEntry auditEntry : objectList) {
            if (auditEntry.getAction().equals(action)) {
                auditEntryList.add(auditEntry);
            }
        }
        return auditEntryList;
    }
}
