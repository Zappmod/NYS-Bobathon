package com.pharmacy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Order Model Tests")
class OrderTest {

    private Order order;
    private Date orderDate;

    @BeforeEach
    void setUp() {
        order = new Order();
        orderDate = new Date();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create order with default constructor")
        void shouldCreateOrderWithDefaultConstructor() {
            Order ord = new Order();
            assertThat(ord).isNotNull();
        }

        @Test
        @DisplayName("Should create order with all parameters")
        void shouldCreateOrderWithAllParameters() {
            Order ord = new Order(
                "ORD5001",
                "RX001",
                "John Smith",
                "P12345",
                "MED001",
                "Amoxicillin 500mg",
                30,
                new BigDecimal("479.70"),
                orderDate,
                "PENDING",
                "CASH",
                "Handle with care"
            );

            assertThat(ord.getId()).isEqualTo("ORD5001");
            assertThat(ord.getPrescriptionId()).isEqualTo("RX001");
            assertThat(ord.getPatientName()).isEqualTo("John Smith");
            assertThat(ord.getPatientId()).isEqualTo("P12345");
            assertThat(ord.getMedicineId()).isEqualTo("MED001");
            assertThat(ord.getMedicineName()).isEqualTo("Amoxicillin 500mg");
            assertThat(ord.getQuantity()).isEqualTo(30);
            assertThat(ord.getTotalAmount()).isEqualByComparingTo(new BigDecimal("479.70"));
            assertThat(ord.getOrderDate()).isEqualTo(orderDate);
            assertThat(ord.getStatus()).isEqualTo("PENDING");
            assertThat(ord.getPaymentMethod()).isEqualTo("CASH");
            assertThat(ord.getPharmacistNotes()).isEqualTo("Handle with care");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should set and get all string fields")
        void shouldSetAndGetAllStringFields() {
            order.setId("ORD5002");
            order.setPrescriptionId("RX002");
            order.setPatientName("Jane Doe");
            order.setPatientId("P67890");
            order.setMedicineId("MED002");
            order.setMedicineName("Lisinopril 10mg");
            order.setStatus("PAID");
            order.setPaymentMethod("CREDIT_CARD");
            order.setPharmacistNotes("Verified insurance");

            assertThat(order.getId()).isEqualTo("ORD5002");
            assertThat(order.getPrescriptionId()).isEqualTo("RX002");
            assertThat(order.getPatientName()).isEqualTo("Jane Doe");
            assertThat(order.getPatientId()).isEqualTo("P67890");
            assertThat(order.getMedicineId()).isEqualTo("MED002");
            assertThat(order.getMedicineName()).isEqualTo("Lisinopril 10mg");
            assertThat(order.getStatus()).isEqualTo("PAID");
            assertThat(order.getPaymentMethod()).isEqualTo("CREDIT_CARD");
            assertThat(order.getPharmacistNotes()).isEqualTo("Verified insurance");
        }

        @Test
        @DisplayName("Should set and get quantity")
        void shouldSetAndGetQuantity() {
            order.setQuantity(60);
            assertThat(order.getQuantity()).isEqualTo(60);
        }

        @Test
        @DisplayName("Should set and get total amount")
        void shouldSetAndGetTotalAmount() {
            BigDecimal amount = new BigDecimal("125.50");
            order.setTotalAmount(amount);
            assertThat(order.getTotalAmount()).isEqualByComparingTo(amount);
        }

        @Test
        @DisplayName("Should set and get order date")
        void shouldSetAndGetOrderDate() {
            order.setOrderDate(orderDate);
            assertThat(order.getOrderDate()).isEqualTo(orderDate);
        }
    }

    @Nested
    @DisplayName("Status Tests")
    class StatusTests {

        @Test
        @DisplayName("Should handle PENDING status")
        void shouldHandlePendingStatus() {
            order.setStatus("PENDING");
            assertThat(order.getStatus()).isEqualTo("PENDING");
        }

        @Test
        @DisplayName("Should handle VALIDATED status")
        void shouldHandleValidatedStatus() {
            order.setStatus("VALIDATED");
            assertThat(order.getStatus()).isEqualTo("VALIDATED");
        }

        @Test
        @DisplayName("Should handle PAID status")
        void shouldHandlePaidStatus() {
            order.setStatus("PAID");
            assertThat(order.getStatus()).isEqualTo("PAID");
        }

        @Test
        @DisplayName("Should handle COLLECTED status")
        void shouldHandleCollectedStatus() {
            order.setStatus("COLLECTED");
            assertThat(order.getStatus()).isEqualTo("COLLECTED");
        }

        @Test
        @DisplayName("Should handle CANCELLED status")
        void shouldHandleCancelledStatus() {
            order.setStatus("CANCELLED");
            assertThat(order.getStatus()).isEqualTo("CANCELLED");
        }
    }

    @Nested
    @DisplayName("Payment Method Tests")
    class PaymentMethodTests {

        @Test
        @DisplayName("Should handle CASH payment method")
        void shouldHandleCashPaymentMethod() {
            order.setPaymentMethod("CASH");
            assertThat(order.getPaymentMethod()).isEqualTo("CASH");
        }

        @Test
        @DisplayName("Should handle CREDIT_CARD payment method")
        void shouldHandleCreditCardPaymentMethod() {
            order.setPaymentMethod("CREDIT_CARD");
            assertThat(order.getPaymentMethod()).isEqualTo("CREDIT_CARD");
        }

        @Test
        @DisplayName("Should handle INSURANCE payment method")
        void shouldHandleInsurancePaymentMethod() {
            order.setPaymentMethod("INSURANCE");
            assertThat(order.getPaymentMethod()).isEqualTo("INSURANCE");
        }
    }

    @Nested
    @DisplayName("Amount Calculation Tests")
    class AmountCalculationTests {

        @Test
        @DisplayName("Should handle zero amount")
        void shouldHandleZeroAmount() {
            order.setTotalAmount(BigDecimal.ZERO);
            assertThat(order.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Should handle decimal amounts")
        void shouldHandleDecimalAmounts() {
            order.setTotalAmount(new BigDecimal("99.99"));
            assertThat(order.getTotalAmount()).isEqualByComparingTo(new BigDecimal("99.99"));
        }

        @Test
        @DisplayName("Should handle large amounts")
        void shouldHandleLargeAmounts() {
            order.setTotalAmount(new BigDecimal("9999.99"));
            assertThat(order.getTotalAmount()).isEqualByComparingTo(new BigDecimal("9999.99"));
        }

        @Test
        @DisplayName("Should maintain precision for currency")
        void shouldMaintainPrecisionForCurrency() {
            BigDecimal amount = new BigDecimal("123.456");
            order.setTotalAmount(amount);
            assertThat(order.getTotalAmount()).isEqualByComparingTo(amount);
        }
    }

    @Nested
    @DisplayName("Quantity Tests")
    class QuantityTests {

        @Test
        @DisplayName("Should handle positive quantities")
        void shouldHandlePositiveQuantities() {
            order.setQuantity(45);
            assertThat(order.getQuantity()).isPositive();
        }

        @Test
        @DisplayName("Should handle zero quantity")
        void shouldHandleZeroQuantity() {
            order.setQuantity(0);
            assertThat(order.getQuantity()).isZero();
        }

        @Test
        @DisplayName("Should handle large quantities")
        void shouldHandleLargeQuantities() {
            order.setQuantity(500);
            assertThat(order.getQuantity()).isEqualTo(500);
        }
    }

    @Nested
    @DisplayName("Date Tests")
    class DateTests {

        @Test
        @DisplayName("Should handle current date")
        void shouldHandleCurrentDate() {
            Date now = new Date();
            order.setOrderDate(now);
            assertThat(order.getOrderDate()).isCloseTo(now, 1000);
        }

        @Test
        @DisplayName("Should handle past dates")
        void shouldHandlePastDates() {
            Date pastDate = new Date(System.currentTimeMillis() - 86400000); // 1 day ago
            order.setOrderDate(pastDate);
            assertThat(order.getOrderDate()).isBefore(new Date());
        }
    }

    @Nested
    @DisplayName("Serialization Tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            assertThat(order).isInstanceOf(java.io.Serializable.class);
        }

        @Test
        @DisplayName("Should have serialVersionUID")
        void shouldHaveSerialVersionUID() {
            assertThatCode(() -> {
                Order.class.getDeclaredField("serialVersionUID");
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values")
        void shouldHandleNullValues() {
            order.setId(null);
            order.setPatientName(null);
            order.setPaymentMethod(null);
            order.setPharmacistNotes(null);
            order.setTotalAmount(null);

            assertThat(order.getId()).isNull();
            assertThat(order.getPatientName()).isNull();
            assertThat(order.getPaymentMethod()).isNull();
            assertThat(order.getPharmacistNotes()).isNull();
            assertThat(order.getTotalAmount()).isNull();
        }

        @Test
        @DisplayName("Should handle empty strings")
        void shouldHandleEmptyStrings() {
            order.setPatientName("");
            order.setPharmacistNotes("");

            assertThat(order.getPatientName()).isEmpty();
            assertThat(order.getPharmacistNotes()).isEmpty();
        }

        @Test
        @DisplayName("Should handle special characters in notes")
        void shouldHandleSpecialCharactersInNotes() {
            String specialNotes = "Patient allergic to penicillin! @#$%^&*()";
            order.setPharmacistNotes(specialNotes);
            assertThat(order.getPharmacistNotes()).isEqualTo(specialNotes);
        }

        @Test
        @DisplayName("Should handle long pharmacist notes")
        void shouldHandleLongPharmacistNotes() {
            String longNotes = "A".repeat(500);
            order.setPharmacistNotes(longNotes);
            assertThat(order.getPharmacistNotes()).hasSize(500);
        }
    }
}

// Made with Bob