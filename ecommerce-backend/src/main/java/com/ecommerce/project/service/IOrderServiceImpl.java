package com.ecommerce.project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.project.model.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DAO.AddressDAO;
import com.ecommerce.project.DAO.CartDAO;
import com.ecommerce.project.DAO.OrderDAO;
import com.ecommerce.project.DAO.OrderItemDAO;
import com.ecommerce.project.DAO.PaymentDAO;
import com.ecommerce.project.DAO.ProductDAO;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.DTO.OrderDto;
import com.ecommerce.project.DTO.OrderItemDto;
import com.ecommerce.project.DTO.OrderResponse;
import com.ecommerce.project.errorHandler.APIErrorHandler;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import com.ecommerce.project.helper.AuthHelper;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.service.Interface.ICartService;
import com.ecommerce.project.service.Interface.IOrderService;

@Service
@Transactional
public class IOrderServiceImpl implements IOrderService {

    private final CartDAO cartDAO;
    private final AddressDAO addressDAO;
    private final OrderItemDAO orderItemDAO;
    private final OrderDAO orderDAO;
    private final PaymentDAO paymentDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final ModelMapper objectMapper;
    private final ICartService shoppingCartService;
    private final AuthHelper userAuthHelper;

    public IOrderServiceImpl(CartDAO cartDAO, AddressDAO addressDAO, OrderItemDAO orderItemDAO,
                             OrderDAO orderDAO, PaymentDAO paymentDAO, ProductDAO productDAO, UserDAO userDAO,
                             ModelMapper objectMapper, ICartService shoppingCartService, AuthHelper userAuthHelper) {
        this.cartDAO = cartDAO;
        this.addressDAO = addressDAO;
        this.orderItemDAO = orderItemDAO;
        this.orderDAO = orderDAO;
        this.paymentDAO = paymentDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.objectMapper = objectMapper;
        this.shoppingCartService = shoppingCartService;
        this.userAuthHelper = userAuthHelper;
    }

    @Override
    public OrderDto placeOrder(String emailId, Long addressId, String paymentMethod, String pgName,
                               String pgPaymentId, String pgStatus, String pgResponseMessage) {
        Cart userShoppingCart = fetchCartByEmailOrThrowException(emailId);
        Address deliveryAddress = fetchAddressByIdOrThrowException(addressId);

        Order newOrder = createOrder(emailId, userShoppingCart, deliveryAddress);
        Payment transactionPayment = createAndPersistPayment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName, newOrder);
        newOrder.setPayment(transactionPayment);

        Order persistedOrder = orderDAO.save(newOrder);

        List<CartItem> itemsInCart = userShoppingCart.getCartItems();
        validateCartNotEmpty(itemsInCart);

        List<OrderItem> purchasedItems = convertCartItemsToOrderItems(itemsInCart, persistedOrder);
        purchasedItems = orderItemDAO.saveAll(purchasedItems);

        processInventoryAndClearCart(itemsInCart, userShoppingCart.getCartId());

        return buildOrderDTOResponse(persistedOrder, purchasedItems, addressId);
    }

    @Override
    public OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingCriteria = buildSortCriteria(sortBy, sortOrder);
        Pageable paginationDetails = PageRequest.of(pageNumber, pageSize, sortingCriteria);
        Page<Order> paginatedOrders = orderDAO.findAll(paginationDetails);

        List<OrderDto> orderDataList = transformOrdersToDTO(paginatedOrders.getContent());
        return buildOrderResponse(paginatedOrders, orderDataList);
    }

    @Override
    public OrderDto orderUpdate(Long orderId, String status) {
        Order existingOrder = fetchOrderByIdOrThrowException(orderId);
        existingOrder.setOrderStatus(status);
        orderDAO.save(existingOrder);
        return convertEntityToDTO(existingOrder);
    }

    @Override
    public OrderResponse getUserOrders(String emailId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingCriteria = buildSortCriteria(sortBy, sortOrder);
        Pageable paginationDetails = PageRequest.of(pageNumber, pageSize, sortingCriteria);
        Page<Order> paginatedOrders = orderDAO.fetchOrdersByUserEmail(emailId, paginationDetails);

        List<OrderDto> orderDataList = transformOrdersToDTO(paginatedOrders.getContent());
        return buildOrderResponse(paginatedOrders, orderDataList);
    }

    @Override
    public OrderResponse getUserOrdersByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User accountUser = fetchUserByIdOrThrowException(userId);
        Sort sortingCriteria = buildSortCriteria(sortBy, sortOrder);
        Pageable paginationDetails = PageRequest.of(pageNumber, pageSize, sortingCriteria);

        Page<Order> paginatedOrders = orderDAO.fetchOrdersByUserEmail(accountUser.getEmail(), paginationDetails);
        List<OrderDto> orderDataList = transformOrdersToDTO(paginatedOrders.getContent());

        return buildOrderResponse(paginatedOrders, orderDataList);
    }

    private Cart fetchCartByEmailOrThrowException(String emailId) {
        Cart userShoppingCart = cartDAO.fetchCartByUserEmail(emailId);
        if (userShoppingCart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }
        return userShoppingCart;
    }

    private Address fetchAddressByIdOrThrowException(Long addressId) {
        return addressDAO.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
    }

    private Order createOrder(String emailId, Cart userCart, Address deliveryAddress) {
        Order newOrder = new Order();
        newOrder.setEmail(emailId);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setTotalAmount(userCart.getTotalPrice());
        newOrder.setOrderStatus("Accepted");
        newOrder.setAddress(deliveryAddress);
        return newOrder;
    }

    private Payment createAndPersistPayment(String paymentMethod, String pgPaymentId, String pgStatus,
                                            String pgResponseMessage, String pgName, Order order) {
        Payment transactionPayment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        transactionPayment.setOrder(order);
        return paymentDAO.save(transactionPayment);
    }

    private void validateCartNotEmpty(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new APIErrorHandler("Your cart is empty");
        }
    }

    private List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {
        List<OrderItem> purchasedItems = new ArrayList<>();
        for (CartItem cartElement : cartItems) {
            OrderItem purchaseItem = new OrderItem();
            purchaseItem.setProduct(cartElement.getProduct());
            purchaseItem.setQty(cartElement.getQty());
            purchaseItem.setProductPrice(cartElement.getProductPrice());
            purchaseItem.setOrder(order);
            purchasedItems.add(purchaseItem);
        }
        return purchasedItems;
    }

    private void processInventoryAndClearCart(List<CartItem> cartItems, Long cartId) {
        for (CartItem cartEntry : cartItems) {
            int purchaseQuantity = cartEntry.getQty();
            Product inventoryProduct = cartEntry.getProduct();

            reduceProductInventory(inventoryProduct, purchaseQuantity);
            shoppingCartService.deleteProductFromCart(cartId, cartEntry.getProduct().getProductId());
        }
    }

    private void reduceProductInventory(Product product, int purchaseQuantity) {
        product.setQuantity(product.getQuantity() - purchaseQuantity);
        productDAO.save(product);
    }

    private OrderDto buildOrderDTOResponse(Order persistedOrder, List<OrderItem> purchasedItems, Long addressId) {
        OrderDto orderDataTransfer = convertEntityToDTO(persistedOrder);
        purchasedItems.forEach(orderEntry -> orderDataTransfer.getOrderItems()
                .add(objectMapper.map(orderEntry, OrderItemDto.class)));
        orderDataTransfer.setAddressId(addressId);
        return orderDataTransfer;
    }

    private Sort buildSortCriteria(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    private List<OrderDto> transformOrdersToDTO(List<Order> orders) {
        return orders.stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    private OrderDto convertEntityToDTO(Order order) {
        return objectMapper.map(order, OrderDto.class);
    }

    private OrderResponse buildOrderResponse(Page<Order> paginatedOrders, List<OrderDto> orderDataList) {
        OrderResponse responsePayload = new OrderResponse();
        responsePayload.setContent(orderDataList);
        responsePayload.setPageNumber(paginatedOrders.getNumber());
        responsePayload.setPageSize(paginatedOrders.getSize());
        responsePayload.setTotalElements(paginatedOrders.getTotalElements());
        responsePayload.setTotalPages(paginatedOrders.getTotalPages());
        responsePayload.setLastPage(paginatedOrders.isLast());
        return responsePayload;
    }

    private Order fetchOrderByIdOrThrowException(Long orderId) {
        return orderDAO.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));
    }

    private User fetchUserByIdOrThrowException(Long userId) {
        return userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    }
}
