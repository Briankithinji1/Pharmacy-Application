package com.brytech.order_service.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.DeliveryStatus;
import com.brytech.order_service.model.DeliveryDetails;
import com.brytech.order_service.repository.DeliveryDetailsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("deliveryDetailsJpa")
public class DeliveryDetailsJpaDataAccessService implements DeliveryDetailsDao {

    private final DeliveryDetailsRepository detailsRepository;

    public DeliveryDetailsJpaDataAccessService(DeliveryDetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Override
    public DeliveryDetails save(DeliveryDetails details) {
        return detailsRepository.save(details);
    }

    @Override
    public void delete(Long id) {
        detailsRepository.deleteById(id); 
    }

    @Override
    public Optional<DeliveryDetails> findById(Long id) {
       return detailsRepository.findById(id);
    }

    @Override
    public List<DeliveryDetails> findByDeliveryStatus(DeliveryStatus status) {
        return detailsRepository.findByStatus(status);
    }

    @Override
    public List<DeliveryDetails> findByAddress(String deliveryAddress) {
        return detailsRepository.findByDeliveryAddress(deliveryAddress);
    }

    @Override
    public List<DeliveryDetails> findByCourierId(Long courierId) {
        return detailsRepository.findByCourierId(courierId);
    }

    @Override
    public Optional<DeliveryDetails> findByOrderId(Long orderId) {
        return detailsRepository.findByOrder_Id(orderId);
    }

    @Override
    public List<DeliveryDetails> findByEstimatedDeliveryTimeBetween(LocalDateTime start, LocalDateTime end) {
        return detailsRepository.findByEstimatedDeliveryTimeBetween(start, end);
    }

    @Override
    public void saveAll(List<DeliveryDetails> details) {
        detailsRepository.saveAll(details);
    }

    @Override
    public Page<DeliveryDetails> findAll(Pageable pageable) {
        return detailsRepository.findAll(pageable);
    }

    @Override
    public List<DeliveryDetails> findByDeliveredAtBetween(LocalDateTime start, LocalDateTime end) {
        return detailsRepository.findByDeliveredAtBetween(start, end);
    }    
}
