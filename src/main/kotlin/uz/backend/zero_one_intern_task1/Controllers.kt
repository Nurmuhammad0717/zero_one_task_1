package uz.backend.zero_one_intern_task1

import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler
import java.math.BigDecimal

@ControllerAdvice
class ExceptionHandler(private val errorMessageSource: ResourceBundleMessageSource) {

    @ExceptionHandler(Task1UniversalExceptionHandler::class)
    fun handleAccountException(exception: Task1ExceptionHandler): ResponseEntity<BaseMessage> {
        return ResponseEntity.badRequest().body(exception.getErrorMessage(errorMessageSource))
    }
}

@RestController
@RequestMapping("api/v1/users")
class UserController(val userService: UserService) {

    @PostMapping()
    fun create(@RequestBody request: UserCreateRequest) = userService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = userService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = userService.getOne(id)

    @PutMapping("{id}")
    fun update(@PathVariable id:Long, @RequestBody request: UserUpdateRequest) = userService.update(id, request)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)

}

@RestController
@RequestMapping("api/v1/product")
class ProductController(val productService: ProductService) {

    @PostMapping()
    fun create(@RequestBody request: ProductCreateRequest) = productService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = productService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = productService.getOne(id)

    @PutMapping("{id}")
    fun update(@PathVariable id:Long, @RequestBody request: ProductUpdateRequest) = productService.update(id, request)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = productService.delete(id)

}

@RestController
@RequestMapping("api/v1/category")
class CategoryController(val categoryService: CategoryService) {

    @PostMapping()
    fun create(@RequestBody request: CategoryCreateRequest) = categoryService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = categoryService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = categoryService.getOne(id)

    @PutMapping("{id}")
    fun update(@PathVariable id:Long, @RequestBody request: CategoryUpdateRequest) = categoryService.update(id, request)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = categoryService.delete(id)

}

@RestController
@RequestMapping("api/v1/transaction-item")
class TransactionItemController(val transactionItemService: TransactionItemService) {

    @PostMapping()
    fun create(@RequestBody request: TransactionItemCreateRequest) = transactionItemService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = transactionItemService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = transactionItemService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = transactionItemService.delete(id)

}

@RestController
@RequestMapping("api/v1/transaction")
class TransactionController(val transactionService: TransactionService) {

    @PostMapping()
    fun create(@RequestBody request: TransactionCreateRequest) = transactionService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = transactionService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = transactionService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = transactionService.delete(id)

}

@RestController
@RequestMapping("api/v1/user-payment-transaction")
class UserPaymentTransactionController(val userPaymentTransactionService: UserPaymentTransactionService) {

    @PostMapping()
    fun create(@RequestBody request: UserPaymentTransactionCreateRequest) = userPaymentTransactionService.create(request)

    @GetMapping
    fun getAll(pageable:Pageable) = userPaymentTransactionService.getAll(pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = userPaymentTransactionService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = userPaymentTransactionService.delete(id)

}

@RestController
@RequestMapping("api/v1/tasks")
class TaskController(val tasksService: TasksService){

    @PostMapping("task1/{userId}")
    fun updateUserBalance(@PathVariable userId: Long, @RequestParam amount: BigDecimal ) = tasksService.updateUserBalance(userId,amount)


    @GetMapping("task2/{userId}")
    fun userPaymentTransactionHistory(@PathVariable userId : Long, pageable: Pageable ) = tasksService.userPaymentTransactionHistory(userId,pageable)

    @GetMapping("task3/{userId}")
    fun userTransactionItemHistory(@PathVariable userId: Long, pageable: Pageable) = tasksService.userTransactionItemHistory(userId,pageable)

    @GetMapping("task4/{id}")
    fun transactionItems(@PathVariable id: Long) = tasksService.transactionItems(id)

    @GetMapping("task5/{userId}")
    fun allTransactionsHistoryForAdmins(@PathVariable userId: Long, pageable: Pageable) = tasksService.allTransactionsHistoryForAdmins(userId, pageable)
}