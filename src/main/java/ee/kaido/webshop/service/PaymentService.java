package ee.kaido.webshop.service;

import ee.kaido.webshop.model.request.output.EveryPayUrl;

public interface PaymentService{
    EveryPayUrl getPaymentLink(double amount, Long orderId);
    Boolean checkIfOrderPaid(Long orderId, String paymentRef);
}
