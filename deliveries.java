package uk.ac.ed.inf;

public class deliveries {
    String orderNo;
    String deliveredTo;
    int costInPence;

    public deliveries(String orderNo, String deliveredTo, int costInPence) {
        this.orderNo = orderNo;
        this.deliveredTo = deliveredTo;
        this.costInPence = costInPence;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeliveredTo() {
        return deliveredTo;
    }

    public void setDeliveredTo(String deliveredTo) {
        this.deliveredTo = deliveredTo;
    }

    public int getCostInPence() {
        return costInPence;
    }

    public void setCostInPence(int costInPence) {
        this.costInPence = costInPence;
    }

    @Override
    public String toString() {
        return "deliveries{" +
                "orderNo='" + orderNo + '\'' +
                ", deliveredTo='" + deliveredTo + '\'' +
                ", costInPence=" + costInPence +
                '}';
    }
}
