package uz.backend.zero_one_intern_task1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(
    basePackages = ["uz.backend.zero_one_intern_task1"],
    repositoryBaseClass = BaseRepositoryImpl::class
)
class ZeroOneInternTask1Application

fun main(args: Array<String>) {
    runApplication<ZeroOneInternTask1Application>(*args)
}
