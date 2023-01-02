package com.example.springboot;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot.api.MathController;
import com.example.springboot.mathDto.mathService;
import com.example.springboot.model.mathPrase;
import org.junit.jupiter.api.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
public class CalculateTest {
	private mathPrase mathParseMoked = Mockito.mock(mathPrase.class);
	private mathService mathMoked = Mockito.mock(mathService.class);

	@BeforeEach
	public void setup() {
		mathMoked = new mathService();
		mathParseMoked = new mathPrase(new ArrayList<Integer>(), "plus");
	}

	//@Autowired
	@Test
	//@DisplayName("1 + 1 = 2")
	void addsTwoNumbers() {
		ArrayList<Integer> arguments = new ArrayList<>();
		arguments.add(3);
		arguments.add(4);
		String operator = "plus";
		mathMoked.insertMath(arguments, operator);
		//assertEquals("3 + 4 = 7", 7, mathMoked.selectAllPrase().get(0));
		/*Mockito.when(mathMoked..getStabilityFactor()).thenReturn(mockedStabilityFactor);
		List<Wheel> mockWheels = new ArrayList<>();
		mockWheels.add(wheelMock);
		mockWheels.add(wheelMock);

		// create concrete object with mocks
		double weightFactor = 1.5;
		Vehicle car = new Car(engineMock, mockWheels, weightFactor);

		// test object behavior (based on the mocks)
		Assertions.assertEquals(mockWheels.size(), car.getWheelsCount());

		double maximumAllowedWeight = car.getMaximumAllowedWeight();
		double expectedValue = mockedStabilityFactor * weightFactor;
		Assertions.assertEquals(expectedValue ,maximumAllowedWeight);
		assertEquals(2, calculatorUnderTest.add(1, 1), "1 + 1 should equal 2");
	}

	@ParameterizedTest(name = "{0} + {1} = {2}")
	@CsvSource({
			"0,    1,   1",
			"1,    2,   3",
			"49,  51, 100",
			"1,  100, 101"
	})
	void add(int first, int second, int expectedResult) {
		assertEquals(expectedResult, calculatorUnderTest.add(first, second),
				() -> first + " + " + second + " should equal " + expectedResult);
	}*/

	/*@ParameterizedTest(name = "{0} X {1} = {2}")
	@CsvSource({
			"0,    1,   0",
			"1,    2,   2",
			"10,  51, 510",
			"-1,  8, -8",
			"-2,  -24, 48",
	})
	void testTimes(int first, int second, int expectedResult) {
		assertEquals(expectedResult, calculatorUnderTest.times(first, second),
				() -> first + " X " + second + " should equal " + expectedResult);
	}

	@Test
	@DisplayName("Tests for divide options")
	void testDivide() {

		// happy path
		int result = calculatorUnderTest.divide(4, 2);
		assertEquals(2, result);

		// test the branch
		try {
			result = calculatorUnderTest.divide(4, 0);
			fail("We are expecting to fail with exception on that one, but from some reason didn't !");
		} catch (IllegalArgumentException e) {
			// all is ok
		} catch (Exception e) {
			fail("We are expecting to fail with IllegalArgumentException on that one, but from some reason we faile with: " + e.getMessage());
		}*/
/*	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}*/
	}
}
