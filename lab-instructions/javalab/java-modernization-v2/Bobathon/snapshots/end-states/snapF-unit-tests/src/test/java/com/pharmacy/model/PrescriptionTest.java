package com.pharmacy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Prescription Model Tests")
class PrescriptionTest {

    private Prescription prescription;
    private Date prescriptionDate;
    private Date expiryDate;

    @BeforeEach
    void setUp() {
        prescription = new Prescription();
        prescriptionDate = new Date();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(prescriptionDate);
        cal.add(Calendar.DAY_OF_MONTH, 30);
        expiryDate = cal.getTime();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create prescription with default constructor")
        void shouldCreatePrescriptionWithDefaultConstructor() {
            Prescription presc = new Prescription();
            assertThat(presc).isNotNull();
        }

        @Test
        @DisplayName("Should create prescription with all parameters")
        void shouldCreatePrescriptionWithAllParameters() {
            Prescription presc = new Prescription(
                "RX001",
                "John Smith",
                "P12345",
                "Dr. Sarah Johnson",
                "MED001",
                "Amoxicillin 500mg",
                30,
                "1 tablet twice daily",
                prescriptionDate,
                expiryDate,
                "PENDING",
                "Take with food"
            );

            assertThat(presc.getId()).isEqualTo("RX001");
            assertThat(presc.getPatientName()).isEqualTo("John Smith");
            assertThat(presc.getPatientId()).isEqualTo("P12345");
            assertThat(presc.getDoctorName()).isEqualTo("Dr. Sarah Johnson");
            assertThat(presc.getMedicineId()).isEqualTo("MED001");
            assertThat(presc.getMedicineName()).isEqualTo("Amoxicillin 500mg");
            assertThat(presc.getQuantity()).isEqualTo(30);
            assertThat(presc.getDosage()).isEqualTo("1 tablet twice daily");
            assertThat(presc.getPrescriptionDate()).isEqualTo(prescriptionDate);
            assertThat(presc.getExpiryDate()).isEqualTo(expiryDate);
            assertThat(presc.getStatus()).isEqualTo("PENDING");
            assertThat(presc.getNotes()).isEqualTo("Take with food");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should set and get all string fields")
        void shouldSetAndGetAllStringFields() {
            prescription.setId("RX123");
            prescription.setPatientName("Jane Doe");
            prescription.setPatientId("P67890");
            prescription.setDoctorName("Dr. Michael Chen");
            prescription.setMedicineId("MED002");
            prescription.setMedicineName("Lisinopril 10mg");
            prescription.setDosage("1 tablet daily");
            prescription.setStatus("VALIDATED");
            prescription.setNotes("Monitor blood pressure");

            assertThat(prescription.getId()).isEqualTo("RX123");
            assertThat(prescription.getPatientName()).isEqualTo("Jane Doe");
            assertThat(prescription.getPatientId()).isEqualTo("P67890");
            assertThat(prescription.getDoctorName()).isEqualTo("Dr. Michael Chen");
            assertThat(prescription.getMedicineId()).isEqualTo("MED002");
            assertThat(prescription.getMedicineName()).isEqualTo("Lisinopril 10mg");
            assertThat(prescription.getDosage()).isEqualTo("1 tablet daily");
            assertThat(prescription.getStatus()).isEqualTo("VALIDATED");
            assertThat(prescription.getNotes()).isEqualTo("Monitor blood pressure");
        }

        @Test
        @DisplayName("Should set and get quantity")
        void shouldSetAndGetQuantity() {
            prescription.setQuantity(60);
            assertThat(prescription.getQuantity()).isEqualTo(60);
        }

        @Test
        @DisplayName("Should set and get dates")
        void shouldSetAndGetDates() {
            prescription.setPrescriptionDate(prescriptionDate);
            prescription.setExpiryDate(expiryDate);

            assertThat(prescription.getPrescriptionDate()).isEqualTo(prescriptionDate);
            assertThat(prescription.getExpiryDate()).isEqualTo(expiryDate);
        }
    }

    @Nested
    @DisplayName("Status Tests")
    class StatusTests {

        @Test
        @DisplayName("Should handle PENDING status")
        void shouldHandlePendingStatus() {
            prescription.setStatus("PENDING");
            assertThat(prescription.getStatus()).isEqualTo("PENDING");
        }

        @Test
        @DisplayName("Should handle VALIDATED status")
        void shouldHandleValidatedStatus() {
            prescription.setStatus("VALIDATED");
            assertThat(prescription.getStatus()).isEqualTo("VALIDATED");
        }

        @Test
        @DisplayName("Should handle FULFILLED status")
        void shouldHandleFulfilledStatus() {
            prescription.setStatus("FULFILLED");
            assertThat(prescription.getStatus()).isEqualTo("FULFILLED");
        }

        @Test
        @DisplayName("Should handle EXPIRED status")
        void shouldHandleExpiredStatus() {
            prescription.setStatus("EXPIRED");
            assertThat(prescription.getStatus()).isEqualTo("EXPIRED");
        }
    }

    @Nested
    @DisplayName("Date Validation Tests")
    class DateValidationTests {

        @Test
        @DisplayName("Should handle expiry date after prescription date")
        void shouldHandleExpiryDateAfterPrescriptionDate() {
            prescription.setPrescriptionDate(prescriptionDate);
            prescription.setExpiryDate(expiryDate);

            assertThat(prescription.getExpiryDate()).isAfter(prescription.getPrescriptionDate());
        }

        @Test
        @DisplayName("Should handle same prescription and expiry dates")
        void shouldHandleSameDates() {
            Date sameDate = new Date();
            prescription.setPrescriptionDate(sameDate);
            prescription.setExpiryDate(sameDate);

            assertThat(prescription.getPrescriptionDate()).isEqualTo(prescription.getExpiryDate());
        }

        @Test
        @DisplayName("Should handle past dates")
        void shouldHandlePastDates() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            Date pastDate = cal.getTime();

            prescription.setPrescriptionDate(pastDate);
            assertThat(prescription.getPrescriptionDate()).isBefore(new Date());
        }

        @Test
        @DisplayName("Should handle future dates")
        void shouldHandleFutureDates() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 1);
            Date futureDate = cal.getTime();

            prescription.setExpiryDate(futureDate);
            assertThat(prescription.getExpiryDate()).isAfter(new Date());
        }
    }

    @Nested
    @DisplayName("Quantity Tests")
    class QuantityTests {

        @Test
        @DisplayName("Should handle positive quantities")
        void shouldHandlePositiveQuantities() {
            prescription.setQuantity(90);
            assertThat(prescription.getQuantity()).isPositive();
        }

        @Test
        @DisplayName("Should handle zero quantity")
        void shouldHandleZeroQuantity() {
            prescription.setQuantity(0);
            assertThat(prescription.getQuantity()).isZero();
        }

        @Test
        @DisplayName("Should handle large quantities")
        void shouldHandleLargeQuantities() {
            prescription.setQuantity(365);
            assertThat(prescription.getQuantity()).isEqualTo(365);
        }
    }

    @Nested
    @DisplayName("Serialization Tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            assertThat(prescription).isInstanceOf(java.io.Serializable.class);
        }

        @Test
        @DisplayName("Should have serialVersionUID")
        void shouldHaveSerialVersionUID() {
            assertThatCode(() -> {
                Prescription.class.getDeclaredField("serialVersionUID");
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values")
        void shouldHandleNullValues() {
            prescription.setId(null);
            prescription.setPatientName(null);
            prescription.setDoctorName(null);
            prescription.setNotes(null);

            assertThat(prescription.getId()).isNull();
            assertThat(prescription.getPatientName()).isNull();
            assertThat(prescription.getDoctorName()).isNull();
            assertThat(prescription.getNotes()).isNull();
        }

        @Test
        @DisplayName("Should handle empty strings")
        void shouldHandleEmptyStrings() {
            prescription.setPatientName("");
            prescription.setDosage("");
            prescription.setNotes("");

            assertThat(prescription.getPatientName()).isEmpty();
            assertThat(prescription.getDosage()).isEmpty();
            assertThat(prescription.getNotes()).isEmpty();
        }

        @Test
        @DisplayName("Should handle special characters in notes")
        void shouldHandleSpecialCharactersInNotes() {
            String specialNotes = "Take with food! @#$%^&*() - Important: Monitor side effects.";
            prescription.setNotes(specialNotes);
            assertThat(prescription.getNotes()).isEqualTo(specialNotes);
        }
    }
}

// Made with Bob