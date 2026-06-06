package bikestore_async;

import java.io.Serializable;

public class OrderMessage implements Serializable {

    private String pedidoId;
    private String clienteEmail;
    private double total;
    private String paymentStatus;
    private int retryCount;

    public OrderMessage() {
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "pedidoId='" + pedidoId + '\'' +
                ", clienteEmail='" + clienteEmail + '\'' +
                ", total=" + total +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }
    public OrderMessage(String pedidoId, String clienteEmail, double total, String paymentStatus, int retryCount) {
        this.pedidoId = pedidoId;
        this.clienteEmail = clienteEmail;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.retryCount = retryCount;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}