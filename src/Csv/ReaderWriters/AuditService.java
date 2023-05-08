package Csv.ReaderWriters;

import Models.AuditEntry;
import Repositories.AuditRepository;

import java.time.LocalDateTime;

public class AuditService implements ICSVReaderWriter<AuditEntry>{
    private static AuditService instance = null;
    private static final String FILE_NAME = "src\\Csv\\audit.csv";
    private static AuditRepository auditRepository = new AuditRepository();
    public static AuditRepository getAuditRepository() {
        return auditRepository;
    }
    public static void setAuditRepository(AuditRepository auditRepository) {
        AuditService.auditRepository = auditRepository;
    }
    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }
    private AuditService() {
    }
    @Override
    public String getWritingFileName() {
        return FILE_NAME;
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(AuditEntry auditEntry) {
        return auditEntry.getAction() + SEPARATOR + " " + auditEntry.getTimestamp().toString();
    }
    @Override
    public AuditEntry processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new AuditEntry(fields[0], LocalDateTime.parse(fields[1]));
    }
    public void logAction(String action) {
        AuditEntry auditEntry = new AuditEntry(action);
        auditRepository.add(auditEntry);
        write(auditEntry);
    }
}
