package com.impactupgrade.integration.hubspot.v1.model;

public class EngagementTaskMetadata extends EngagementMetadata {

    //     "body": "This is the body of the task.",
    //    "subject": "Task title",
    //    "status": "NOT_STARTED",
    //    "forObjectType": "CONTACT"

    private String body;
    private String subject;
    private Status status; // needs to be NOT_STARTED, COMPLETED, IN_PROGRESS, WAITING, or DEFERRED.
    private final String forObjectType = "CONTACT";

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        NOT_STARTED,
        COMPLETED,
        IN_PROGRESS,
        WAITING,
        DEFERRED;
    }

    @Override
    public String toString() {
        return "EngagementTaskMetadata{" +
                "body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", forObjectType='" + forObjectType + '\'' +
                '}';
    }

}
