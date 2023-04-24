import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootApplication
@SpringBootTest
@ExtendWith(SpringExtension::class)
class ArtExternalTest: DescribeSpec() {

    init {
        describe("contextLoads") {
            it("should load the Spring Boot context without any issues") {}
        }
    }
}
