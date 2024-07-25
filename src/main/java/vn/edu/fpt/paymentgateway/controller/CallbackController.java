package vn.edu.fpt.paymentgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.constants.StatusOrder;
import vn.edu.fpt.paymentgateway.entity.*;
import vn.edu.fpt.paymentgateway.repo.*;
import vn.edu.fpt.paymentgateway.constants.StatusDelivery;
import vn.edu.fpt.paymentgateway.services.PaymentFactory;
import vn.edu.fpt.paymentgateway.services.PaymentService;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.PAYUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CallbackController {

    private PaymentService paymentService;

    @Value("${payment.redirectUrl}")
    private String redirectUrl;
    @Autowired
    private DeliverRepository deliverRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private PaymentDetailRepository paymentDetailRepository;
    @Autowired
    private JewelryRepository jewelryRepository;

    @GetMapping("/callback")
    @Transactional(rollbackFor = Exception.class)
    public void paymentCallback(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws IOException {
        paymentService = PaymentFactory.getPayment(request, PaymentSupplierEnum.VNPAY);
        System.out.println("Get callback: " + params);
        String orderId = params.get("orderId");
        String transId = params.get("transId");
        String status = params.get("status");
        paymentService.updatePayment(orderId, transId, status);

        PaymentDetail detailPayMent = paymentDetailRepository.findPaymentDetailByOrderId(orderId).get();

        if (status.equals("0")) {
            Random rand = new Random();
            List<Long> var5 = deliverRepository.findAllByOrderByIdDesc()
                    .stream()
                    .filter(e -> e.getStatus().equals("active"))
                    .collect(Collectors.toList())
                    .stream()
                    .map(Deliver::getUserId)
                    .collect(Collectors.toList());

            Long deliverId = var5.get(rand.nextInt(var5.size()));

            Delivery delivery = new Delivery();
            delivery.setOrderId(orderId);
            delivery.setDeliverId(deliverId);
            delivery.setStatus(StatusDelivery.WAITING.getValue());
            Date date1 = new Date();
            Date date2 = (Date) date1.clone();
            date2.setDate(date1.getDate() + 3);

            delivery.setStatusDate(date1);
            delivery.setEndDateEstimated(date2);
            delivery.setDeliveryFee(0L);
            delivery.setCreatedAt(new Date());

            deliveryRepository.save(delivery);

            Orders orders = ordersRepository.findByUniqueOrderId(orderId).get();
            orders.setStatus(StatusOrder.DELIVERY.getValue());
            // nhan 100 tro lai luc thanh toan chia 100
            orders.setTotalPrice(detailPayMent.getAmount() * 100);

            List<OrderDetail> orderDetail = orderDetailRepository.findAllByUniqueOrderId(orderId);
            List<Jewelry> jewelries = jewelryRepository.findAllByIdIn(orderDetail.stream().map(OrderDetail::getJewelryId).collect(Collectors.toList()));
            orderDetail.forEach(e -> {
                e.setStatus(StatusOrder.DELIVERY.getValue());
            });

            jewelries.forEach(e -> {
                Optional<OrderDetail> orderDetail1 =
                        orderDetail.stream()
                                .filter(orderDetail2 -> orderDetail2.getJewelryId().equals(e.getId()))
                                .findFirst();

                orderDetail1.ifPresent(detail -> e.setQuantity(e.getQuantity() - detail.getQuantityNumber()));
            });
            jewelryRepository.saveAll(jewelries);
            ordersRepository.save(orders);
            orderDetailRepository.saveAll(orderDetail);
        }
        response.sendRedirect(redirectUrl + PAYUtils.buildQuery(params));
    }

//    @PostMapping("/callback")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void retrieve(HttpServletRequest httpServletRequest) {
//        System.out.println(httpServletRequest.toString());
//    }
}
