package cep;

import bongcloud.cep.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ChangeAggressivenessTest.class, ChangeTightnessTest.class,
		InsertDecreaseAggressivenessPreviousTest.class, InsertDecreaseAggressivenessTest.class,
		InsertDecreaseTightnessPreviousTest.class, InsertDecreaseTightnessTest.class,
		InsertIncreaseAggressivenessPreviousTest.class, InsertIncreaseAggressivenessTest.class,
		InsertIncreaseTightnessTest.class })
public class CepTests {

}
