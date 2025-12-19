package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DAO.AddressDAO;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.DTO.AddressDto;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.service.Interface.IAddrService;

@Service
public class IAddrServiceImpl implements IAddrService {

    private final AddressDAO addressDAO;
    private final UserDAO userDAO;
    private final ModelMapper objectMapper;

    public IAddrServiceImpl(AddressDAO addressDAO, UserDAO userDAO, ModelMapper objectMapper) {
        this.addressDAO = addressDAO;
        this.userDAO = userDAO;
        this.objectMapper = objectMapper;
    }

    @Override
    public AddressDto newAddr(AddressDto addressDTO, User user) {
        Address addressEntity = convertDTOToEntity(addressDTO);
        associateAddressWithUser(addressEntity, user);

        Address persistedAddress = addressDAO.save(addressEntity);
        return convertEntityToDTO(persistedAddress);
    }

    @Override
    public List<AddressDto> getAddr() {
        List<Address> allAddresses = addressDAO.findAll();
        return transformAddressesToDTOs(allAddresses);
    }

    @Override
    public AddressDto getAddrById(Long addressId) {
        Address retrievedAddress = fetchAddressOrThrowException(addressId);
        return convertEntityToDTO(retrievedAddress);
    }

    @Override
    public List<AddressDto> getUserAddr(User user) {
        List<Address> userAddressList = user.getAddresses();
        return transformAddressesToDTOs(userAddressList);
    }

    @Override
    public List<AddressDto> getAddrByUserId(Long userId) {
        User retrievedUser = fetchUserOrThrowException(userId);
        List<Address> userAddressList = retrievedUser.getAddresses();
        return transformAddressesToDTOs(userAddressList);
    }

    @Override
    public AddressDto updateAddr(Long addressId, AddressDto addressDTO) {
        Address existingAddress = fetchAddressOrThrowException(addressId);
        applyAddressUpdates(existingAddress, addressDTO);

        Address modifiedAddress = addressDAO.save(existingAddress);
        synchronizeUserAddressList(existingAddress.getUser(), addressId, modifiedAddress);

        return convertEntityToDTO(modifiedAddress);
    }

    @Override
    public String delAddr(Long addressId) {
        Address addressToDelete = fetchAddressOrThrowException(addressId);
        User associatedUser = addressToDelete.getUser();

        removeAddressFromUser(associatedUser, addressId);
        addressDAO.delete(addressToDelete);

        return buildDeletionMessage(addressId);
    }

    private Address convertDTOToEntity(AddressDto addressDTO) {
        return objectMapper.map(addressDTO, Address.class);
    }

    private AddressDto convertEntityToDTO(Address address) {
        return objectMapper.map(address, AddressDto.class);
    }

    private List<AddressDto> transformAddressesToDTOs(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    private void associateAddressWithUser(Address addressEntity, User user) {
        addressEntity.setUser(user);
        List<Address> userAddressCollection = user.getAddresses();
        userAddressCollection.add(addressEntity);
        user.setAddresses(userAddressCollection);
    }

    private Address fetchAddressOrThrowException(Long addressId) {
        return addressDAO.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
    }

    private User fetchUserOrThrowException(Long userId) {
        return userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    }

    private void applyAddressUpdates(Address targetAddress, AddressDto sourceDTO) {
        targetAddress.setCity(sourceDTO.getCity());
        targetAddress.setPincode(sourceDTO.getPincode());
        targetAddress.setState(sourceDTO.getState());
        targetAddress.setCountry(sourceDTO.getCountry());
        targetAddress.setStreet(sourceDTO.getStreet());
        targetAddress.setBuildingName(sourceDTO.getBuildingName());
    }

    private void synchronizeUserAddressList(User user, Long addressIdToReplace, Address newAddress) {
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressIdToReplace));
        user.getAddresses().add(newAddress);
        userDAO.save(user);
    }

    private void removeAddressFromUser(User user, Long addressId) {
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userDAO.save(user);
    }

    private String buildDeletionMessage(Long addressId) {
        return "Address has been successfully removed (ID: " + addressId + ")";
    }
}
