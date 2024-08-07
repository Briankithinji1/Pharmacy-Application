package main.java.com.example.Pharmacy.Application.user.dao;

import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.prescription.Prescription;
import main.java.com.example.Pharmacy.Application.product.Product;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

public interface PharmacistDao {

    // Basic CRUD operations
    List<Pharmacist> getAllPharmacists();
    Optional<Pharmacist> getPharmacistsByUserId(Long userId);
    Optional<Pharmacist> getPharmacistsByEmail(String email);
    void insertPharmacist(Pharmacist pharmacist);
    void updatePharmacist(Pharmacist pharmacist);
    void deletePharmacist(Long userId);
    boolean isPharmacistExistsById(Long userId);
    boolean isPharmacistExistsByEmail(String email);

    // Prescription Management
    List<Prescription> getAllPrescriptions();
    Optional<Prescription> getPrescriptionById(Long prescriptionId);
    void approvePrescription(Long prescriptionId);
    void rejectPrescription(Long prescriptionId);
    void updatePrescription(Prescription prescription);

    // Medication Management (Inventory Management)
    List<Product> getAllMedications();
    Optional<Product> getMedicationById(Long medicationId);
    void updateMedication(Product medication);
    void manageInventory(Long medicationId, int quantity);

    // Customer Interaction (In-app Messaging)
    List<Message> getMessagesForUser(Long userId);
    List<Message> getMessagesBetweenUsers(Long userId1, Long userId2);
    void sendMessage(Message message);
    void markMessageAsRead(Long messageId);
    void deleteMessage(Long messageId);

    // Order Management
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String status);

    // Compliance and Reporting (Reports on history, sales, and inventory)
    List<Report> generateReports();
    List<AuditTrail> getAuditTrails();

    // Communication and Collaboration
    void updateProfile(Long userId, Pharmacist pharmacist);
    void assignSupplier(Long pharmacistId, Long supplierId);

    // Quality Assurance
    List<Feedback> getCustomerFeedback();
    void addressFeedback(Long feedbackId, String response);

    // Patient Profiles and Prescription History
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(Long patientId);
    void updatePatientProfile(Patient patient);
    List<Prescription> getPrescriptionHistoryByPatientId(Long patientId);

    // Inventory Status
    List<InventoryItem> getInventoryStatus();
    Optional<InventoryItem> getInventoryItemById(Long itemId);
    void updateInventoryItem(InventoryItem item);
}
