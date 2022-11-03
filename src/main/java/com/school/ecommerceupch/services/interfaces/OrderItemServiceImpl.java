package com.school.ecommerceupch.services.interfaces;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.repositories.IOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl  implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;
    @Override
    public BaseResponse create(CreateOrderItemRequest request) {
        OrderItem orderItem = repository.save(from(request));

            return BaseResponse .builder()
                    .data(orderItem)
                    .message("OrderItem saved correctly")
                    .success(Boolean.TRUE)
                    .httpStatus(HttpStatus.CREATED)
                    .build();

    }

    @Override
    public BaseResponse get(Long id) {

        OrderItem orderItem = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderItemRequest request) {
        OrderItem orderItem = findOneAndEnsureExistById(id);
        orderItem = update(orderItem, request);
        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem update")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        repository.deleteById(id);
        return BaseResponse.builder()
                .data(null)
                .message("OrderItem deleted correctly ")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    private OrderItem findOneAndEnsureExistById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("The OrderItem does not exist"));
    }

    private OrderItem from(CreateOrderItemRequest request){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(request.getQuantity());
            orderItem.setProduct(request.getProduct());
            orderItem.setProduct(request.getProduct());

            return orderItem;
    }

    private OrderItem update(OrderItem orderItem, UpdateOrderItemRequest request){

        orderItem.setQuantity(request.getQuantity());
        orderItem.setProduct(request.getProduct());
        orderItem.setProduct(request.getProduct());
        repository.save(orderItem);
        return orderItem;
    }
}
