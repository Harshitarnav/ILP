package uk.ac.ed.inf;

public class flightPath {
    String orderNo;
    double fromLatitude;
    double fromLongitude;
    int angle;
    double toLongitude;
    double toLatitude;

    public flightPath(String orderNo, double fromLatitude, double fromLongitude, int angle, double toLongitude, double toLatitude){
        this.orderNo = orderNo;
        this.angle = angle;
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getFromLatitude() {
        return fromLatitude;
    }

    public void setFromLatitude(double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    public double getFromLongitude() {
        return fromLongitude;
    }

    public void setFromLongitude(double fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public double getToLongitude() {
        return toLongitude;
    }

    public void setToLongitude(double toLongitude) {
        this.toLongitude = toLongitude;
    }

    public double getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(double toLatitude) {
        this.toLatitude = toLatitude;
    }

    @Override
    public String toString() {
        return "flightPath{" +
                "orderNo='" + orderNo + '\'' +
                ", fromLatitude=" + fromLatitude +
                ", fromLongitude=" + fromLongitude +
                ", angle=" + angle +
                ", toLongitude=" + toLongitude +
                ", toLatitude=" + toLatitude +
                '}';
    }
}
