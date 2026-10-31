package com.example.lab2.dto;
public class CheckDto {
    private Long releaseId;
    private String checkName;
    private boolean mandatory;
    private String result;
    public Long getReleaseId() { return releaseId; }
    public void setReleaseId(Long releaseId) { this.releaseId = releaseId; }
    public String getCheckName() { return checkName; }
    public void setCheckName(String checkName) { this.checkName = checkName; }
    public boolean isMandatory() { return mandatory; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
