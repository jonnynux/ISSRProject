package model;

/**
 * Pojo for mantaining the indexes calculated
 *
 * @author jonny
 */
public class Index {

    private String brand;
    private String type;
    private double returnedIndex;
    private double successIndex;
    private double expiredIndex;
    private double approvalIndex;
    private double returnedTempIndex;
    private double successTempIndex;
    private double expiredTempIndex;
    private double approvalTempIndex;

    public Index() {
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReturnedIndex(double returnedIndex) {
        this.returnedIndex = returnedIndex;
    }

    public void setSuccessIndex(double successIndex) {
        this.successIndex = successIndex;
    }

    public void setExpiredIndex(double expiredIndex) {
        this.expiredIndex = expiredIndex;
    }

    public void setApprovalIndex(double approvalIndex) {
        this.approvalIndex = approvalIndex;
    }

    public void setReturnedTempIndex(double returnedTempIndex) {
        this.returnedTempIndex = returnedTempIndex;
    }

    public void setSuccessTempIndex(double successTempIndex) {
        this.successTempIndex = successTempIndex;
    }

    public void setExpiredTempIndex(double expiredTempIndex) {
        this.expiredTempIndex = expiredTempIndex;
    }

    public void setApprovalTempIndex(double approvalTempIndex) {
        this.approvalTempIndex = approvalTempIndex;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public double getReturnedIndex() {
        return returnedIndex;
    }

    public double getSuccessIndex() {
        return successIndex;
    }

    public double getExpiredIndex() {
        return expiredIndex;
    }

    public double getApprovalIndex() {
        return approvalIndex;
    }

    public double getReturnedTempIndex() {
        return returnedTempIndex;
    }

    public double getSuccessTempIndex() {
        return successTempIndex;
    }

    public double getExpiredTempIndex() {
        return expiredTempIndex;
    }

    public double getApprovalTempIndex() {
        return approvalTempIndex;
    }
}
