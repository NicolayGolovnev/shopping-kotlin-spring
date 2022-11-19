package ru.golovnev.shopping

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest
class ShoppingApplicationTests {

	@Autowired
	private val applicationContext: ApplicationContext? = null

	@Test
	fun `application context loading test`() {
		assertNotNull(applicationContext)
	}

}
