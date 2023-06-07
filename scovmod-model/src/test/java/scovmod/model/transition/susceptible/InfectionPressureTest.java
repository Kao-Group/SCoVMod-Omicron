/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.util.math.Random;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class InfectionPressureTest {

	private static final double TOLERANCE = 1e-10;
	private static final double STANDARD_EFFICACY_PROTECTION = 1.0;
	private static final double VARIANT_EFFICACY_PROTECTION = 0.5;

@Mock
Random rnd;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void isValueObject() {
		//Test hashcode and equals in here
		InfectionPressure instance1a = new InfectionPressure(0.2, 0.3);
		InfectionPressure instance1b = new InfectionPressure(0.2, 0.3);
		InfectionPressure instance2 = new InfectionPressure(0.4, 0.3);
		InfectionPressure instance4 = new InfectionPressure( 0.2, 0.4);

		assertEquals(instance1a, instance1b);
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance4));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance4.hashCode());
	}

	@Test
	public void testForEvaluate_NoTransmission() {
		int timeStepInDays = 15;
		double variantPressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);
		double probNoTransmission =
				Math.exp(-(variantPressure * timeStepInDays*VARIANT_EFFICACY_PROTECTION)) *
				Math.exp(-(personPressure * timeStepInDays*STANDARD_EFFICACY_PROTECTION));
		when(rnd.nextBoolean(1-probNoTransmission)).thenReturn(false);

 		Assert.assertEquals(TransmissionEventType.NULL,instance.evaluate(rnd,timeStepInDays,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION));
	}

	@Test
	public void testForEvaluate_VariantTransmission() {
		int timeStepInDays = 15;
		double variantPressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);
		double probNoTransmission =
				Math.exp(-(variantPressure * timeStepInDays*VARIANT_EFFICACY_PROTECTION)) *
						Math.exp(-(personPressure * timeStepInDays*STANDARD_EFFICACY_PROTECTION));
		when(rnd.nextBoolean(1 - probNoTransmission)).thenReturn(true);
		when(rnd.nextBoolean(getConditionalProbability(variantPressure,personPressure))).thenReturn(true);
		assertEquals(TransmissionEventType.FROM_VARIANT,instance.evaluate(rnd,timeStepInDays,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION));
	}


	@Test
	public void testForEvaluate_PersonTransmission() {
		int timeStepInDays = 15;
		double variantPressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);
		double probNoTransmission =
				Math.exp(-(variantPressure * timeStepInDays*VARIANT_EFFICACY_PROTECTION)) *
						Math.exp(-(personPressure * timeStepInDays*STANDARD_EFFICACY_PROTECTION));
		when(rnd.nextBoolean(1 - probNoTransmission)).thenReturn(true);
		when(rnd.nextBoolean(getConditionalProbability(variantPressure,personPressure))).thenReturn(false);
		assertEquals(TransmissionEventType.FROM_PERSON,instance.evaluate(rnd,timeStepInDays,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION));
	}


	@Test
	public void augmentingPressure() {
		double variantPressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);
		InfectionPressure result = instance.augmentPeoplePressure(0.1);

		assertEquals(personPressure + 0.1, result.getPersonPressure(), TOLERANCE);
		assertEquals(variantPressure, result.getVariantPressure(), TOLERANCE);
	}

	@Test
	public void augmentingVariantPressure() {
		double variantPressure = 0.00012;
		double personPressure = 0.015;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);
		InfectionPressure result = instance.augmentVariantPressure(0.1);

		assertEquals(personPressure, result.getPersonPressure(), TOLERANCE);
		assertEquals(variantPressure+0.1, result.getVariantPressure(), TOLERANCE);
	}

	@Test
	public void settingVariantPressure() {
		double personPressure = 0.012;
		InfectionPressure instance = new InfectionPressure(0, personPressure);
		InfectionPressure result = instance.setVariantPressure(0.1);

		assertEquals(personPressure, result.getPersonPressure(), TOLERANCE);
		assertEquals(0.1, result.getVariantPressure(), TOLERANCE);
	}

	@Test
	public void exceptionIfOverwriteWildlifePressure() {
		double variantPressure = 0.1;
		double personPressure = 0;
		InfectionPressure instance = new InfectionPressure(variantPressure, personPressure);

		try {
			instance.setVariantPressure(0.2);
			fail("expected exception");
		} catch (AssertionError e){}
	}

	private double getConditionalProbability(double variantPressure, double personPressure){
		return variantPressure / (variantPressure + personPressure);
	}

}
