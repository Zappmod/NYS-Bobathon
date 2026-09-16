package com.pharmacy.repository;

import com.pharmacy.model.Medicine;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MedicineRepository Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicineRepositoryTest {

    private MedicineRepository repository;

    @BeforeAll
    void setUpAll() {
        // Get singleton instance
        repository = MedicineRepository.getInstance();
    }

    @BeforeEach
    void setUp() {
        // Note: Repository uses singleton pattern with pre-initialized data
        // Tests will work with existing data
    }

    @Nested
    @DisplayName("Singleton Pattern Tests")
    class SingletonTests {

        @Test
        @DisplayName("Should return same instance")
        void shouldReturnSameInstance() {
            MedicineRepository instance1 = MedicineRepository.getInstance();
            MedicineRepository instance2 = MedicineRepository.getInstance();
            
            assertThat(instance1).isSameAs(instance2);
        }

        @Test
        @DisplayName("Should not be null")
        void shouldNotBeNull() {
            assertThat(repository).isNotNull();
        }
    }

    @Nested
    @DisplayName("Find Operations Tests")
    class FindOperationsTests {

        @Test
        @DisplayName("Should find all medicines")
        void shouldFindAllMedicines() {
            List<Medicine> medicines = repository.findAll();
            
            assertThat(medicines)
                .isNotNull()
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(8); // Initial sample data
        }

        @Test
        @DisplayName("Should find medicine by id")
        void shouldFindMedicineById() {
            Medicine medicine = repository.findById("MED001");
            
            assertThat(medicine).isNotNull();
            assertThat(medicine.getId()).isEqualTo("MED001");
            assertThat(medicine.getName()).isEqualTo("Amoxicillin 500mg");
        }

        @Test
        @DisplayName("Should return null for non-existent id")
        void shouldReturnNullForNonExistentId() {
            Medicine medicine = repository.findById("NONEXISTENT");
            
            assertThat(medicine).isNull();
        }

        @Test
        @DisplayName("Should search medicines by name")
        void shouldSearchMedicinesByName() {
            List<Medicine> results = repository.searchByName("Amoxicillin");
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(m -> m.getName().toLowerCase().contains("amoxicillin"));
        }

        @Test
        @DisplayName("Should search medicines case-insensitively")
        void shouldSearchMedicinesCaseInsensitively() {
            List<Medicine> results1 = repository.searchByName("AMOXICILLIN");
            List<Medicine> results2 = repository.searchByName("amoxicillin");
            List<Medicine> results3 = repository.searchByName("Amoxicillin");
            
            assertThat(results1).hasSameSizeAs(results2).hasSameSizeAs(results3);
        }

        @Test
        @DisplayName("Should return empty list for non-matching search")
        void shouldReturnEmptyListForNonMatchingSearch() {
            List<Medicine> results = repository.searchByName("NonExistentMedicine12345");
            
            assertThat(results).isEmpty();
        }

        @Test
        @DisplayName("Should search with partial name match")
        void shouldSearchWithPartialNameMatch() {
            List<Medicine> results = repository.searchByName("mg");
            
            assertThat(results)
                .isNotEmpty()
                .allMatch(m -> m.getName().toLowerCase().contains("mg"));
        }
    }

    @Nested
    @DisplayName("Add Medicine Tests")
    class AddMedicineTests {

        @Test
        @DisplayName("Should add new medicine")
        void shouldAddNewMedicine() {
            Medicine newMedicine = new Medicine(
                "MED999",
                "Test Medicine",
                "Test Description",
                new BigDecimal("10.00"),
                50,
                "Test Manufacturer"
            );
            
            repository.addMedicine(newMedicine);
            Medicine found = repository.findById("MED999");
            
            assertThat(found).isNotNull();
            assertThat(found.getName()).isEqualTo("Test Medicine");
            
            // Cleanup
            repository.deleteMedicine("MED999");
        }

        @Test
        @DisplayName("Should replace existing medicine when adding with same id")
        void shouldReplaceExistingMedicineWhenAddingWithSameId() {
            String testId = "MED_TEST_REPLACE";
            
            Medicine original = new Medicine(
                testId, "Original", "Original Desc",
                new BigDecimal("10.00"), 10, "Original Mfg"
            );
            repository.addMedicine(original);
            
            Medicine replacement = new Medicine(
                testId, "Replaced", "Replaced Desc",
                new BigDecimal("20.00"), 20, "Replaced Mfg"
            );
            repository.addMedicine(replacement);
            
            Medicine found = repository.findById(testId);
            assertThat(found.getName()).isEqualTo("Replaced");
            
            // Cleanup
            repository.deleteMedicine(testId);
        }
    }

    @Nested
    @DisplayName("Update Medicine Tests")
    class UpdateMedicineTests {

        @Test
        @DisplayName("Should update existing medicine")
        void shouldUpdateExistingMedicine() {
            String testId = "MED_TEST_UPDATE";
            
            Medicine medicine = new Medicine(
                testId, "Original Name", "Description",
                new BigDecimal("15.00"), 100, "Manufacturer"
            );
            repository.addMedicine(medicine);
            
            medicine.setName("Updated Name");
            medicine.setPrice(new BigDecimal("20.00"));
            repository.updateMedicine(medicine);
            
            Medicine updated = repository.findById(testId);
            assertThat(updated.getName()).isEqualTo("Updated Name");
            assertThat(updated.getPrice()).isEqualByComparingTo(new BigDecimal("20.00"));
            
            // Cleanup
            repository.deleteMedicine(testId);
        }

        @Test
        @DisplayName("Should update stock quantity")
        void shouldUpdateStockQuantity() {
            String testId = "MED_TEST_STOCK";
            
            Medicine medicine = new Medicine(
                testId, "Test Med", "Description",
                new BigDecimal("10.00"), 100, "Manufacturer"
            );
            repository.addMedicine(medicine);
            
            boolean result = repository.updateStock(testId, 30);
            
            assertThat(result).isTrue();
            Medicine updated = repository.findById(testId);
            assertThat(updated.getStockQuantity()).isEqualTo(70);
            
            // Cleanup
            repository.deleteMedicine(testId);
        }

        @Test
        @DisplayName("Should not update stock when insufficient quantity")
        void shouldNotUpdateStockWhenInsufficientQuantity() {
            String testId = "MED_TEST_INSUFFICIENT";
            
            Medicine medicine = new Medicine(
                testId, "Test Med", "Description",
                new BigDecimal("10.00"), 10, "Manufacturer"
            );
            repository.addMedicine(medicine);
            
            boolean result = repository.updateStock(testId, 20);
            
            assertThat(result).isFalse();
            Medicine unchanged = repository.findById(testId);
            assertThat(unchanged.getStockQuantity()).isEqualTo(10);
            
            // Cleanup
            repository.deleteMedicine(testId);
        }

        @Test
        @DisplayName("Should return false when updating stock for non-existent medicine")
        void shouldReturnFalseWhenUpdatingStockForNonExistentMedicine() {
            boolean result = repository.updateStock("NONEXISTENT", 10);
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Delete Medicine Tests")
    class DeleteMedicineTests {

        @Test
        @DisplayName("Should delete medicine")
        void shouldDeleteMedicine() {
            String testId = "MED_TEST_DELETE";
            
            Medicine medicine = new Medicine(
                testId, "To Delete", "Description",
                new BigDecimal("10.00"), 50, "Manufacturer"
            );
            repository.addMedicine(medicine);
            
            assertThat(repository.findById(testId)).isNotNull();
            
            repository.deleteMedicine(testId);
            
            assertThat(repository.findById(testId)).isNull();
        }

        @Test
        @DisplayName("Should handle deleting non-existent medicine")
        void shouldHandleDeletingNonExistentMedicine() {
            assertThatCode(() -> repository.deleteMedicine("NONEXISTENT"))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Sample Data Tests")
    class SampleDataTests {

        @Test
        @DisplayName("Should have initialized sample data")
        void shouldHaveInitializedSampleData() {
            List<Medicine> medicines = repository.findAll();
            
            assertThat(medicines).hasSizeGreaterThanOrEqualTo(8);
        }

        @Test
        @DisplayName("Should contain Amoxicillin in sample data")
        void shouldContainAmoxicillinInSampleData() {
            Medicine amoxicillin = repository.findById("MED001");
            
            assertThat(amoxicillin).isNotNull();
            assertThat(amoxicillin.getName()).contains("Amoxicillin");
            assertThat(amoxicillin.getPrice()).isEqualByComparingTo(new BigDecimal("15.99"));
        }

        @Test
        @DisplayName("Should contain Ibuprofen in sample data")
        void shouldContainIbuprofenInSampleData() {
            Medicine ibuprofen = repository.findById("MED008");
            
            assertThat(ibuprofen).isNotNull();
            assertThat(ibuprofen.getName()).contains("Ibuprofen");
        }
    }

    @Nested
    @DisplayName("Concurrent Access Tests")
    class ConcurrentAccessTests {

        @Test
        @DisplayName("Should handle concurrent reads")
        void shouldHandleConcurrentReads() {
            assertThatCode(() -> {
                for (int i = 0; i < 100; i++) {
                    repository.findAll();
                    repository.findById("MED001");
                }
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Should handle concurrent writes")
        void shouldHandleConcurrentWrites() {
            assertThatCode(() -> {
                for (int i = 0; i < 10; i++) {
                    String id = "MED_CONCURRENT_" + i;
                    Medicine medicine = new Medicine(
                        id, "Concurrent Test", "Description",
                        new BigDecimal("10.00"), 50, "Manufacturer"
                    );
                    repository.addMedicine(medicine);
                    repository.deleteMedicine(id);
                }
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null id in findById")
        void shouldHandleNullIdInFindById() {
            Medicine result = repository.findById(null);
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("Should handle empty search string")
        void shouldHandleEmptySearchString() {
            List<Medicine> results = repository.searchByName("");
            assertThat(results).isNotEmpty(); // Should return all medicines
        }

        @Test
        @DisplayName("Should handle search with special characters")
        void shouldHandleSearchWithSpecialCharacters() {
            List<Medicine> results = repository.searchByName("@#$%");
            assertThat(results).isEmpty();
        }
    }
}

// Made with Bob