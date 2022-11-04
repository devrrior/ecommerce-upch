package com.school.ecommerceupch.controllers.dtos;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orderitem")
public class OrderItemController {

    @Autowired
    private IOrderItemService service;


    @GetMapping
    public ResponseEntity<BaseResponse> get(@PathVariable Long id){
        BaseResponse response = service.get(id);
        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping
    public ResponseEntity <BaseResponse> create(@RequestBody CreateOrderItemRequest request){
        BaseResponse response = service.create(request);
        return  new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PutMapping
    public ResponseEntity <BaseResponse> update(@PathVariable Long id, @RequestBody UpdateOrderItemRequest request){
        BaseResponse response = service.update(id,request);
        return new ResponseEntity<>(response,response.getHttpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity  <BaseResponse> delete(@PathVariable Long id){
        BaseResponse response= service.delete(id);
        return new ResponseEntity<>(response,response.getHttpStatus());
    }

}
