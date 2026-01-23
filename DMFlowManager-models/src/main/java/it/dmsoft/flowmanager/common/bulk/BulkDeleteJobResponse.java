package it.dmsoft.flowmanager.common.bulk;

public class BulkDeleteJobResponse {
    private final String jobId;
    private final String statusEndpoint;
    private final String topic;

    public BulkDeleteJobResponse(String jobId, String statusEndpoint, String topic) {
        this.jobId = jobId;
        this.statusEndpoint = statusEndpoint;
        this.topic = topic;
    }

    public String getJobId() { return jobId; }
    public String getStatusEndpoint() { return statusEndpoint; }
    public String getTopic() { return topic; }
}