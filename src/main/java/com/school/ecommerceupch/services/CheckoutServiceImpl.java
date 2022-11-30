package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CheckoutRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.services.interfaces.ICheckoutService;
import com.school.ecommerceupch.services.interfaces.IOrderService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CheckoutServiceImpl implements ICheckoutService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;


    @Override
    public BaseResponse checkout(CheckoutRequest request) {

        Order order = orderService.findOneAndEnsureExistById(request.getOrderId());
        Float totalAmount = getTotalAmount(order);

        subtractStock(order);

        return BaseResponse.builder()
                .data(null)
                .message("Payment successful")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    private void subtractStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            Integer stockPurchased = orderItem.getQuantity();
            Integer newStock = product.getStock() - stockPurchased;

            productService.updateStock(product, newStock);
        }
    }

    private Float getTotalAmount(Order order) {
        Float total = 0.0F;
        for (OrderItem orderItem : order.getOrderItems()) {
            total += getTotalAmount(orderItem);
        }

        return total;
    }

    private Float getTotalAmount(OrderItem orderItem) {
        return orderItem.getProduct().getPrice() * orderItem.getQuantity();
    }
}
