package com.pharmacy.integration;

import com.pharmacy.model.Medicine;
import com.pharmacy.model.Order;
import com.pharmacy.model.Prescription;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.OrderRepository;
import com.pharmacy.repository.PrescriptionRepository;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Pharmacy Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PharmacyIntegrationTest {

    private MedicineRepository medicineRepo;
    private PrescriptionRepository prescriptionRepo;
    private OrderRepository orderRepo;

    private String testPrescriptionId;
    private String testOrderId;

    @BeforeAll
    void setUpAll() {
        medicineRepo = MedicineRepository.getInstance();
        prescriptionRepo = PrescriptionRepository.getInstance();
        orderRepo = OrderRepository.getInstance();
    }

    @Nested
    @DisplayName("Complete Workflow Tests")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class CompleteWorkflowTests {

        @Test
        @org.junit.jupiter.api.Order(1)
        @DisplayName("Step 1: Should verify medicine availability")
        void step1_shouldVerifyMedicineAvailability() {
            Medicine medicine = medicineRepo.findById("MED001");
            
            assertThat(medicine).isNotNull();
            assertThat(medicine.getStockQuantity()).isGreaterThan(0);
            assertThat(medicine.getPrice()).isGreaterThan(BigDecimal.ZERO);
        }

        @Test
        @org.junit.jupiter.api.Order(2)
        @DisplayName("Step 2: Should create prescription")
        void step2_shouldCreatePrescription() {
            testPrescriptionId = prescriptionRepo.generateId();
            
            Date prescDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(prescDate);
            cal.add(Calendar.DAY_OF_MONTH, 30);
            Date expiryDate = cal.getTime();
            
            Prescription prescription = new Prescription(
                testPrescriptionId,
                "Integration Test Patient",
                "P99999",
                "Dr. Test Doctor",
                "MED001",
                "Amoxicillin 500mg",
                10,
                "1 tablet twice daily",
                prescDate,
                expiryDate,
                "PENDING",
                "Integration test prescription"
            );
            
            prescriptionRepo.addPrescription(prescription);
            
            Prescription saved = prescriptionRepo.findById(testPrescriptionId);
            assertThat(saved).isNotNull();
            assertThat(saved.getStatus()).isEqualTo("PENDING");
        }

        @Test
        @org.junit.jupiter.api.Order(3)
        @DisplayName("Step 3: Should validate prescription")
        void step3_shouldValidatePrescription() {
            Prescription prescription = prescriptionRepo.findById(testPrescriptionId);
            assertThat(prescription).isNotNull();
            
            prescription.setStatus("VALIDATED");
            prescriptionRepo.updatePrescription(prescription);
            
            Prescription validated = prescriptionRepo.findById(testPrescriptionId);
            assertThat(validated.getStatus()).isEqualTo("VALIDATED");
        }

        @Test
        @org.junit.jupiter.api.Order(4)
        @DisplayName("Step 4: Should create order from prescription")
        void step4_shouldCreateOrderFromPrescription() {
            Prescription prescription = prescriptionRepo.findById(testPrescriptionId);
            assertThat(prescription.getStatus()).isEqualTo("VALIDATED");
            
            Medicine medicine = medicineRepo.findById(prescription.getMedicineId());
            int initialStock = medicine.getStockQuantity();
            
            testOrderId = orderRepo.generateId();
            BigDecimal totalAmount = medicine.getPrice()
                .multiply(new BigDecimal(prescription.getQuantity()));
            
            Order order = new Order(
                testOrderId,
                testPrescriptionId,
                prescription.getPatientName(),
                prescription.getPatientId(),
                prescription.getMedicineId(),
                prescription.getMedicineName(),
                prescription.getQuantity(),
                totalAmount,
                new Date(),
                "PENDING",
                "CASH",
                "Integration test order"
            );
            
            orderRepo.addOrder(order);
            
            Order saved = orderRepo.findById(testOrderId);
            assertThat(saved).isNotNull();
            assertThat(saved.getStatus()).isEqualTo("PENDING");
            assertThat(saved.getTotalAmount()).isEqualByComparingTo(totalAmount);
        }

        @Test
        @org.junit.jupiter.api.Order(5)
        @DisplayName("Step 5: Should process payment")
        void step5_shouldProcessPayment() {
            Order order = orderRepo.findById(testOrderId);
            assertThat(order).isNotNull();
            
            Medicine medicine = medicineRepo.findById(order.getMedicineId());
            int stockBeforePayment = medicine.getStockQuantity();
            
            order.setStatus("PAID");
            orderRepo.updateOrder(order);
            
            // Update stock
            boolean stockUpdated = medicineRepo.updateStock(
                order.getMedicineId(),
                order.getQuantity()
            );
            
            assertThat(stockUpdated).isTrue();
            
            Medicine updatedMedicine = medicineRepo.findById(order.getMedicineId());
            assertThat(updatedMedicine.getStockQuantity())
                .isEqualTo(stockBeforePayment - order.getQuantity());
            
            Order paidOrder = orderRepo.findById(testOrderId);
            assertThat(paidOrder.getStatus()).isEqualTo("PAID");
        }

        @Test
        @org.junit.jupiter.api.Order(6)
        @DisplayName("Step 6: Should mark order as collected")
        void step6_shouldMarkOrderAsCollected() {
            Order order = orderRepo.findById(testOrderId);
            assertThat(order.getStatus()).isEqualTo("PAID");
            
            order.setStatus("COLLECTED");
            orderRepo.updateOrder(order);
            
            Order collected = orderRepo.findById(testOrderId);
            assertThat(collected.getStatus()).isEqualTo("COLLECTED");
        }

        @Test
        @org.junit.jupiter.api.Order(7)
        @DisplayName("Step 7: Should mark prescription as fulfilled")
        void step7_shouldMarkPrescriptionAsFulfilled() {
            Prescription prescription = prescriptionRepo.findById(testPrescriptionId);
            
            prescription.setStatus("FULFILLED");
            prescriptionRepo.updatePrescription(prescription);
            
            Prescription fulfilled = prescriptionRepo.findById(testPrescriptionId);
            assertThat(fulfilled.getStatus()).isEqualTo("FULFILLED");
        }
    }

    @Nested
    @DisplayName("Dashboard Integration Tests")
    class DashboardIntegrationTests {

        @Test
        @DisplayName("Should retrieve pending prescriptions")
        void shouldRetrievePendingPrescriptions() {
            List<Prescription> pending = prescriptionRepo.findByStatus("PENDING");
            
            assertThat(pending).isNotNull();
            assertThat(pending).allMatch(p -> "PENDING".equals(p.getStatus()));
        }

        @Test
        @DisplayName("Should retrieve pending orders")
        void shouldRetrievePendingOrders() {
            List<Order> pending = orderRepo.findByStatus("PENDING");
            
            assertThat(pending).isNotNull();
            assertThat(pending).allMatch(o -> "PENDING".equals(o.getStatus()));
        }

        @Test
        @DisplayName("Should count total prescriptions")
        void shouldCountTotalPrescriptions() {
            List<Prescription> all = prescriptionRepo.findAll();
            
            assertThat(all).isNotNull();
            assertThat(all.size()).isGreaterThan(0);
        }

        @Test
        @DisplayName("Should count total orders")
        void shouldCountTotalOrders() {
            List<Order> all = orderRepo.findAll();
            
            assertThat(all).isNotNull();
        }
    }

    @Nested
    @DisplayName("Search and Filter Tests")
    class SearchAndFilterTests {

        @Test
        @DisplayName("Should search medicines by name")
        void shouldSearchMedicinesByName() {
            List<Medicine> results = medicineRepo.searchByName("Amoxicillin");
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(m -> m.getName().toLowerCase().contains("amoxicillin"));
        }

        @Test
        @DisplayName("Should find prescriptions by patient ID")
        void shouldFindPrescriptionsByPatientId() {
            // Create test prescription
            String patientId = "P_TEST_SEARCH";
            String prescId = prescriptionRepo.generateId();
            
            Prescription prescription = new Prescription(
                prescId, "Test Patient", patientId, "Dr. Test",
                "MED001", "Test Med", 10, "Test dosage",
                new Date(), new Date(), "PENDING", ""
            );
            prescriptionRepo.addPrescription(prescription);
            
            List<Prescription> results = prescriptionRepo.findByPatientId(patientId);
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(p -> patientId.equals(p.getPatientId()));
            
            // Cleanup
            prescriptionRepo.deletePrescription(prescId);
        }

        @Test
        @DisplayName("Should find orders by patient ID")
        void shouldFindOrdersByPatientId() {
            String patientId = "P_TEST_ORDER_SEARCH";
            String orderId = orderRepo.generateId();
            
            Order order = new Order(
                orderId, "RX999", "Test Patient", patientId,
                "MED001", "Test Med", 10, new BigDecimal("100.00"),
                new Date(), "PENDING", "CASH", ""
            );
            orderRepo.addOrder(order);
            
            List<Order> results = orderRepo.findByPatientId(patientId);
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(o -> patientId.equals(o.getPatientId()));
            
            // Cleanup
            orderRepo.deleteOrder(orderId);
        }

        @Test
        @DisplayName("Should find orders by prescription ID")
        void shouldFindOrdersByPrescriptionId() {
            String prescId = "RX_TEST_LINK";
            String orderId = orderRepo.generateId();
            
            Order order = new Order(
                orderId, prescId, "Test Patient", "P123",
                "MED001", "Test Med", 10, new BigDecimal("100.00"),
                new Date(), "PENDING", "CASH", ""
            );
            orderRepo.addOrder(order);
            
            List<Order> results = orderRepo.findByPrescriptionId(prescId);
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(o -> prescId.equals(o.getPrescriptionId()));
            
            // Cleanup
            orderRepo.deleteOrder(orderId);
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle insufficient stock")
        void shouldHandleInsufficientStock() {
            Medicine medicine = medicineRepo.findById("MED001");
            int currentStock = medicine.getStockQuantity();
            
            boolean result = medicineRepo.updateStock("MED001", currentStock + 100);
            
            assertThat(result).isFalse();
            
            Medicine unchanged = medicineRepo.findById("MED001");
            assertThat(unchanged.getStockQuantity()).isEqualTo(currentStock);
        }

        @Test
        @DisplayName("Should handle non-existent medicine")
        void shouldHandleNonExistentMedicine() {
            Medicine medicine = medicineRepo.findById("NONEXISTENT");
            assertThat(medicine).isNull();
        }

        @Test
        @DisplayName("Should handle non-existent prescription")
        void shouldHandleNonExistentPrescription() {
            Prescription prescription = prescriptionRepo.findById("NONEXISTENT");
            assertThat(prescription).isNull();
        }

        @Test
        @DisplayName("Should handle non-existent order")
        void shouldHandleNonExistentOrder() {
            Order order = orderRepo.findById("NONEXISTENT");
            assertThat(order).isNull();
        }
    }

    @Nested
    @DisplayName("Data Consistency Tests")
    class DataConsistencyTests {

        @Test
        @DisplayName("Should maintain referential integrity between prescription and order")
        void shouldMaintainReferentialIntegrityBetweenPrescriptionAndOrder() {
            String prescId = prescriptionRepo.generateId();
            String orderId = orderRepo.generateId();
            
            // Create prescription
            Prescription prescription = new Prescription(
                prescId, "Test Patient", "P123", "Dr. Test",
                "MED001", "Test Med", 10, "Test dosage",
                new Date(), new Date(), "VALIDATED", ""
            );
            prescriptionRepo.addPrescription(prescription);
            
            // Create order linked to prescription
            Order order = new Order(
                orderId, prescId, "Test Patient", "P123",
                "MED001", "Test Med", 10, new BigDecimal("100.00"),
                new Date(), "PENDING", "CASH", ""
            );
            orderRepo.addOrder(order);
            
            // Verify link
            Order savedOrder = orderRepo.findById(orderId);
            Prescription linkedPrescription = prescriptionRepo.findById(savedOrder.getPrescriptionId());
            
            assertThat(linkedPrescription).isNotNull();
            assertThat(linkedPrescription.getId()).isEqualTo(prescId);
            
            // Cleanup
            orderRepo.deleteOrder(orderId);
            prescriptionRepo.deletePrescription(prescId);
        }

        @Test
        @DisplayName("Should maintain medicine data consistency")
        void shouldMaintainMedicineDataConsistency() {
            Medicine medicine = medicineRepo.findById("MED001");
            String originalName = medicine.getName();
            BigDecimal originalPrice = medicine.getPrice();
            
            // Verify medicine data hasn't been corrupted
            assertThat(medicine.getName()).isNotEmpty();
            assertThat(medicine.getPrice()).isGreaterThan(BigDecimal.ZERO);
            assertThat(medicine.getStockQuantity()).isGreaterThanOrEqualTo(0);
        }
    }

    @AfterAll
    void cleanUp() {
        // Clean up test data
        if (testOrderId != null) {
            orderRepo.deleteOrder(testOrderId);
        }
        if (testPrescriptionId != null) {
            prescriptionRepo.deletePrescription(testPrescriptionId);
        }
    }
}

// Made with Bob