package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.dtos.responses.GetOrderStatusResponse;
import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.repositories.IOrderStatusRepository;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements IOrderStatusService {
    @Autowired
    private IOrderStatusRepository repository;
    @Override
    public BaseResponse create(CreateOrderStatusRequest request) {
        if(repository.existsOrderStatusByName(request.getName())) {
            return BaseResponse.builder()
                    .data(null)
                    .message("Order Status already exists")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return BaseResponse.builder()
                .data(toGetOrderStatusResponse(repository.save(toOrderStatus(request))))
                .message("Order created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        if (!repository.existsById(id)) {
            return orderStatusDoesntExists();
        }
        return BaseResponse.builder()
                .data(toGetOrderStatusResponse(repository.getOrderStatusById(id)))
                .message("User Role exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderStatusRequest request) {
        if (!repository.existsById(id)) {
            return orderStatusDoesntExists();
        }
        return BaseResponse.builder()
                .data(toGetOrderStatusResponse(repository.save(toOrderStatusUpdate(request, id))))
                .message("Order updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {
        if(!repository.existsById(id)) {
            return orderStatusDoesntExists();
        }
        repository.deleteById(id);
        return BaseResponse.builder()
                .data(null)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private BaseResponse orderStatusDoesntExists() {
        return BaseResponse.builder()
                .data(null)
                .message("Order Status doesn't exists")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
    private GetOrderStatusResponse toGetOrderStatusResponse(OrderStatus orderStatus){
        GetOrderStatusResponse response = new GetOrderStatusResponse();
        response.setId(orderStatus.getId());
        response.setName(orderStatus.getName());
        return response;
    }

    private OrderStatus toOrderStatus(CreateOrderStatusRequest request){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(request.getName());
        return orderStatus;
    }

    private OrderStatus toOrderStatusUpdate(UpdateOrderStatusRequest request, Long id){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(request.getName());
        return orderStatus;
    }
    @Override
    public OrderStatus findByName(String name) {
        return null;
    }
}
