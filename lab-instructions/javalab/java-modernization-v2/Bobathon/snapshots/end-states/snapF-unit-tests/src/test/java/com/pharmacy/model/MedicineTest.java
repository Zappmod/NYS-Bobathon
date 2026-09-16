package com.pharmacy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Medicine Model Tests")
class MedicineTest {

    private Medicine medicine;

    @BeforeEach
    void setUp() {
        medicine = new Medicine();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create medicine with default constructor")
        void shouldCreateMedicineWithDefaultConstructor() {
            Medicine med = new Medicine();
            assertThat(med).isNotNull();
        }

        @Test
        @DisplayName("Should create medicine with all parameters")
        void shouldCreateMedicineWithAllParameters() {
            Medicine med = new Medicine(
                "MED001",
                "Amoxicillin 500mg",
                "Antibiotic for bacterial infections",
                new BigDecimal("15.99"),
                100,
                "PharmaCorp"
            );

            assertThat(med.getId()).isEqualTo("MED001");
            assertThat(med.getName()).isEqualTo("Amoxicillin 500mg");
            assertThat(med.getDescription()).isEqualTo("Antibiotic for bacterial infections");
            assertThat(med.getPrice()).isEqualByComparingTo(new BigDecimal("15.99"));
            assertThat(med.getStockQuantity()).isEqualTo(100);
            assertThat(med.getManufacturer()).isEqualTo("PharmaCorp");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should set and get id")
        void shouldSetAndGetId() {
            medicine.setId("MED123");
            assertThat(medicine.getId()).isEqualTo("MED123");
        }

        @Test
        @DisplayName("Should set and get name")
        void shouldSetAndGetName() {
            medicine.setName("Ibuprofen 200mg");
            assertThat(medicine.getName()).isEqualTo("Ibuprofen 200mg");
        }

        @Test
        @DisplayName("Should set and get description")
        void shouldSetAndGetDescription() {
            medicine.setDescription("Pain reliever");
            assertThat(medicine.getDescription()).isEqualTo("Pain reliever");
        }

        @Test
        @DisplayName("Should set and get price")
        void shouldSetAndGetPrice() {
            BigDecimal price = new BigDecimal("25.50");
            medicine.setPrice(price);
            assertThat(medicine.getPrice()).isEqualByComparingTo(price);
        }

        @Test
        @DisplayName("Should set and get stock quantity")
        void shouldSetAndGetStockQuantity() {
            medicine.setStockQuantity(150);
            assertThat(medicine.getStockQuantity()).isEqualTo(150);
        }

        @Test
        @DisplayName("Should set and get manufacturer")
        void shouldSetAndGetManufacturer() {
            medicine.setManufacturer("HealthMeds Inc");
            assertThat(medicine.getManufacturer()).isEqualTo("HealthMeds Inc");
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should handle zero stock quantity")
        void shouldHandleZeroStockQuantity() {
            medicine.setStockQuantity(0);
            assertThat(medicine.getStockQuantity()).isZero();
        }

        @Test
        @DisplayName("Should handle negative stock quantity")
        void shouldHandleNegativeStockQuantity() {
            medicine.setStockQuantity(-10);
            assertThat(medicine.getStockQuantity()).isNegative();
        }

        @Test
        @DisplayName("Should handle large stock quantities")
        void shouldHandleLargeStockQuantities() {
            medicine.setStockQuantity(999999);
            assertThat(medicine.getStockQuantity()).isEqualTo(999999);
        }

        @Test
        @DisplayName("Should handle decimal prices correctly")
        void shouldHandleDecimalPricesCorrectly() {
            medicine.setPrice(new BigDecimal("19.99"));
            assertThat(medicine.getPrice()).isEqualByComparingTo(new BigDecimal("19.99"));
        }

        @Test
        @DisplayName("Should handle zero price")
        void shouldHandleZeroPrice() {
            medicine.setPrice(BigDecimal.ZERO);
            assertThat(medicine.getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("Serialization Tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            assertThat(medicine).isInstanceOf(java.io.Serializable.class);
        }

        @Test
        @DisplayName("Should have serialVersionUID")
        void shouldHaveSerialVersionUID() {
            assertThatCode(() -> {
                Medicine.class.getDeclaredField("serialVersionUID");
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values")
        void shouldHandleNullValues() {
            medicine.setId(null);
            medicine.setName(null);
            medicine.setDescription(null);
            medicine.setPrice(null);
            medicine.setManufacturer(null);

            assertThat(medicine.getId()).isNull();
            assertThat(medicine.getName()).isNull();
            assertThat(medicine.getDescription()).isNull();
            assertThat(medicine.getPrice()).isNull();
            assertThat(medicine.getManufacturer()).isNull();
        }

        @Test
        @DisplayName("Should handle empty strings")
        void shouldHandleEmptyStrings() {
            medicine.setId("");
            medicine.setName("");
            medicine.setDescription("");
            medicine.setManufacturer("");

            assertThat(medicine.getId()).isEmpty();
            assertThat(medicine.getName()).isEmpty();
            assertThat(medicine.getDescription()).isEmpty();
            assertThat(medicine.getManufacturer()).isEmpty();
        }

        @Test
        @DisplayName("Should handle very long strings")
        void shouldHandleVeryLongStrings() {
            String longString = "A".repeat(1000);
            medicine.setDescription(longString);
            assertThat(medicine.getDescription()).hasSize(1000);
        }
    }
}

// Made with Bob