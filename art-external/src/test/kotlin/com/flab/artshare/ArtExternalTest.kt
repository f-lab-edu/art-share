import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootApplication
@SpringBootTest
class ArtExternalTest : DescribeSpec() {

    @Autowired
    private lateinit var applicationContext: org.springframework.context.ApplicationContext

    init {
        describe("Spring Boot context") {
            it("should load without any issues") {
                assert(applicationContext != null) { "Application context should not be null" }
            }
        }
    }
}
