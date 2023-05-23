package repositories;

import models.AuditEntry;

import java.util.ArrayList;
import java.util.List;

public class AuditRepository extends GenericRepository<AuditEntry>{
    private static final AuditRepository instance = new AuditRepository();
    private AuditRepository(List<AuditEntry> objectList) {
        super(objectList);
    }
    private AuditRepository() {
        super();
    }
    public static AuditRepository getInstance(){
        return instance;
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
