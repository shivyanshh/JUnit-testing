package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@BeforeALL
//@BeforeEach
//@Test
//@AfterEach
//@AfterAll
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//it is by default as lifecyle.per_method meaning it creates multiple objects when testing
//lifecylce.per class uses the same object every time

//Before All prints
//before each works  -> creating obj
//tests run
//aftereach works
//before each works -> creating obj
//test runs
// .......
//after each runs
//afterall prints
class ContactManagerTest {
    ContactManager contactManager;  //class setup for object to be created in before each
    @BeforeAll                      //this runs before any test runs
    public static void setupall(){
        System.out.println("it will be printed before all\n");
    }
    @BeforeEach     //this methods are run before each test is run, used for setting up test ex- this runs 4 times since we have 4 tests
    public void setup(){
        //Since we were creating contact manager obj every test so we can initialise it before each test ran
        contactManager = new ContactManager();
    }
    @Test
    public void ShouldCreateContact(){
        //ContactManager contactManager = new ContactManager();
        contactManager.addContact("john","doe","0282233283");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
    }
    //checking for errors if they work properly
    @Test
    @DisplayName("Should Not Create Contact when First name is null")
    public void ShouldThrowRuntimeExceptionwhenFirstnameIsNull(){
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->contactManager.addContact(null,"doe","0282233283"));
    }
    @Test
    @DisplayName("Should Not Create Contact when First name is null")
    public void ShouldThrowRuntimeExceptionwhenLastnameIsNull(){
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->contactManager.addContact("peter",null,"0282233283"));
    }
    @Test
    @DisplayName("Should Not Create Contact when First name is null")
    public void ShouldThrowRuntimeExceptionwhenPhoneNumberIsNull(){
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,()->contactManager.addContact("peter","parker",null));
    }
    @Test
    @DisplayName("Should Create Contact Only on Mac OS")
    @EnabledOnOs(value=OS.MAC,disabledReason = "Only on MAC OS")
    public void ShouldRunONMacOs(){
        contactManager.addContact("henry","cavill","0123456789");
    }
    @Test
    @DisplayName("Should Not Create Contact on Windows")
    @DisabledOnOs(value=OS.WINDOWS,disabledReason = "not on windows baby")
    public void ShouldNotCreateContactOnWindows(){
        contactManager.addContact("monkey d","luffy","0123456789");
    }
    @AfterEach
    public void setupclean(){
        System.out.println("after each test\n");
    }
    @AfterAll
    public static void exit(){
        System.out.println("tests completed");
    }
    @Test
    @DisplayName("Should Test Contact Creation ON Developer Machine")
    //Assumptions helps with conditional cases where we get aborted if not present or execute
    public void ShouldTestContactCreationOnDEV(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("john","doe","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
    //Repeated Test for randomness in a method
    @DisplayName("Repeat This Test for 5 times")
    @RepeatedTest(value=5,
            name="Repeated test attempt hehe {currentRepetition} of {totalRepetitions}")
    public void RepeatContactCreation5times(){
        contactManager.addContact("john","doe","0123456789");
        assertEquals(1,contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().isEmpty()!=true);
    }

    @DisplayName("Parameterized Test for Contact Creation Using Value Source")
    @ParameterizedTest
    @ValueSource(strings = {"0232132335","0848543272","0213838039"})
    public void ShouldTestMethodWithValueSource(String phonenumber){
        contactManager.addContact("john","doe",phonenumber);
        assertTrue(contactManager.getAllContacts().size()==1);
        assertFalse(contactManager.getAllContacts().isEmpty()==true);
    }

    //Since Most of the time Values wont be so straight forward we
    //may need to create objects as values
    @DisplayName("Parameterized Test for Contact Creation Using Method Source")
    @ParameterizedTest
    @MethodSource("phoneNumbers")
    public void ShouldTestMethodWithMethodSource(String phonenumber){
        contactManager.addContact("john","doe",phonenumber);
        assertTrue(contactManager.getAllContacts().size()==1);
        assertFalse(contactManager.getAllContacts().isEmpty()==true);
    }
    public static List<String> phoneNumbers(){
        return Arrays.asList("0232132335","0848543272","0213838039");
    }

    @DisplayName("Parameterized Test for Contact Creation Using CSV Source")
    @ParameterizedTest
    @CsvSource({"0232132335","0848543272","0213838039"})
    public void ShouldTestMethodWithCSVSource(String phonenumber){
        contactManager.addContact("john","doe",phonenumber);
        assertTrue(contactManager.getAllContacts().size()==1);
        assertFalse(contactManager.getAllContacts().isEmpty()==true);
    }

    @DisplayName("Parameterized Test for Contact Creation Using CSV File Source")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void ShouldTestMethodWithCSVFileSource(String phonenumber){
        contactManager.addContact("john","doe",phonenumber);
        assertTrue(contactManager.getAllContacts().size()==1);
        assertFalse(contactManager.getAllContacts().isEmpty()==true);
    }

    //Rather than writing all tests like that
//we can seperate it so its nicer and cleaner
    @Nested
    class NestedTests{
        ContactManager contactManager;
        @BeforeEach
        public void setup(){
            contactManager = new ContactManager();
        }

        @DisplayName("Parameterized Test for Contact Creation Using CSV File Source part 2")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void ShouldTestMethodWithCSVFileSource(String phonenumber){
            contactManager.addContact("john","doe",phonenumber);
            assertTrue(contactManager.getAllContacts().size()==1);
            assertFalse(contactManager.getAllContacts().isEmpty()==true);
        }


        //This is for Tests which are Disabled rn
        // not reccommended you mighf forget this
        @Test
        @Disabled
        public void ShouldCreateContact(){
            //ContactManager contactManager = new ContactManager();
            contactManager.addContact("john","doe","0282233283");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1,contactManager.getAllContacts().size());
        }
    }
}