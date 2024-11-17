package uz.backend.zero_one_intern_task1

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.Date

interface UserService {

    fun create(request: UserCreateRequest)

    fun getAll(pageable: Pageable) : Page<UserResponse>

    fun getOne(id: Long) : UserResponse

    fun update(id: Long, request: UserUpdateRequest)

    fun delete(id: Long)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun create(request: UserCreateRequest) {

        request.run {
           val user = userRepository.findByUsernameAndDeletedFalse(username)

            if (user != null)
                throw UserAlreadyExistsExceptionHandler()

            userRepository.save(this.toEntity())
        }

    }

    override fun getAll(pageable: Pageable): Page<UserResponse> {
        return userRepository.findAllNotDeletedForPageable(pageable).map {
            UserResponse.toResponse(it)
        }
        }


    override fun getOne(id: Long): UserResponse {
        return userRepository.findByIdAndDeletedFalse(id)?.let {
            UserResponse.toResponse(it)
        }?: throw UserNotFoundExceptionHandler()
    }

    override fun update(id: Long, request: UserUpdateRequest) {

        val user = userRepository.findByIdAndDeletedFalse(id)
        if (user == null) throw UserNotFoundExceptionHandler()

        request.run {
            username.let {
                val findByUsername = userRepository.findByUsername(id, it)
                if (findByUsername != null) throw UserAlreadyExistsExceptionHandler()
                    user.username = it
            }
            fullName.let { user.fullName = it }
            password.let { user.password = it }
            balance.let { user.balance = it }
        }

        userRepository.save(user)
    }

    override fun delete(id: Long) {
        userRepository.trash(id) ?: throw UserNotFoundExceptionHandler()
    }
}


interface ProductService {

    fun create(request: ProductCreateRequest)

    fun getAll(pageable: Pageable) : Page<ProductResponse>

    fun getOne(id: Long) : ProductResponse

    fun update(id: Long, request: ProductUpdateRequest)

    fun delete(id: Long)
}

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
): ProductService {

    override fun create(request: ProductCreateRequest) {

        request.run {

           val category = categoryRepository.findByIdAndDeletedFalse(categoryId)?: throw CategoryNotFoundExceptionHandler()

            productRepository.save(this.toEntity(category))
        }
    }

    override fun getAll(pageable: Pageable): Page<ProductResponse> {
        return productRepository.findAllNotDeletedForPageable(pageable).map {
            ProductResponse.toResponse(it)
        }
    }

    override fun getOne(id: Long): ProductResponse {

        return productRepository.findByIdAndDeletedFalse(id)?.let {
            return ProductResponse.toResponse(it)
        }?: throw Task1UniversalExceptionHandler(ErrorCodes.PRODUCT_NOT_FOUND)
    }

    override fun update(id: Long, request: ProductUpdateRequest) {

       val product = productRepository.findByIdAndDeletedFalse(id)?: throw Task1UniversalExceptionHandler(ErrorCodes.PRODUCT_NOT_FOUND)
       val category = categoryRepository.findByIdAndDeletedFalse(request.categoryId)?: throw CategoryNotFoundExceptionHandler()
        request.run {
            name.let { product.name=it }
            count.let{ product.count=it }
            product.category = category
        }
        productRepository.save(product)
    }

    override fun delete(id: Long) {
       productRepository.trash(id)?: throw Task1UniversalExceptionHandler(ErrorCodes.PRODUCT_NOT_FOUND)
    }

}


interface CategoryService {

    fun create(request: CategoryCreateRequest)

    fun getAll(pageable: Pageable) : Page<CategoryResponse>

    fun getOne(id: Long) : CategoryResponse

    fun update(id: Long, request: CategoryUpdateRequest)

    fun delete(id: Long)
}

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
): CategoryService {

    override fun create(request: CategoryCreateRequest) {
        request.run {
            categoryRepository.save(this.toEntity())
        }
    }

    override fun getAll(pageable: Pageable): Page<CategoryResponse> {

       return categoryRepository.findAllNotDeletedForPageable(pageable).map {
            CategoryResponse.toResponse(it)
        }

    }

    override fun getOne(id: Long): CategoryResponse {

       val category = categoryRepository.findByIdAndDeletedFalse(id)?: throw CategoryNotFoundExceptionHandler()

        return CategoryResponse.toResponse(category)

    }

    override fun update(id: Long, request: CategoryUpdateRequest) {

        val category = categoryRepository.findByIdAndDeletedFalse(id)?: throw CategoryNotFoundExceptionHandler()

        request.run {

            name.let { category.name=it }
            order.let { category.categoryOrder=it }
            description.let { category.description=it }

        }
        categoryRepository.save(category)
    }

    override fun delete(id: Long) {

        categoryRepository.trash(id) ?: throw CategoryNotFoundExceptionHandler()

    }


}

interface TransactionService {

    fun create(request: TransactionCreateRequest)

    fun getAll(pageable: Pageable) : Page<TransactionResponse>

    fun getOne(id: Long) : TransactionResponse

    fun delete(id: Long)

}

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
): TransactionService {

    override fun create(request: TransactionCreateRequest) {
        request.run {
           val user = userRepository.findByIdAndDeletedFalse(userId)?: throw UserNotFoundExceptionHandler()

            transactionRepository.save(this.toEntity(user))

        }
    }

    override fun getAll(pageable: Pageable): Page<TransactionResponse> {

       return transactionRepository.findAllNotDeletedForPageable(pageable).map {
            TransactionResponse.toResponse(it)
        }

    }

    override fun getOne(id: Long): TransactionResponse {

       val transaction = transactionRepository.findByIdAndDeletedFalse(id)?: throw Task1UniversalExceptionHandler(ErrorCodes.TRANSACTION_NOT_FOUND)
        return TransactionResponse.toResponse(transaction)
    }

    override fun delete(id: Long) {
        transactionRepository.trash(id) ?: throw Task1UniversalExceptionHandler(ErrorCodes.TRANSACTION_NOT_FOUND)
    }
}

interface TransactionItemService {

    fun create(request: TransactionItemCreateRequest)

    fun getAll(pageable: Pageable) : Page<TransactionItemResponse>

    fun getOne(id: Long) : TransactionItemResponse

    fun delete(id: Long)

}

@Service
class TransactionItemServiceImpl(
    private val transactionItemRepository: TransactionItemRepository,
    private val productRepository: ProductRepository,
    private val transactionRepository: TransactionRepository
):TransactionItemService {
    override fun create(request: TransactionItemCreateRequest) {

        request.run {
            val product =  productRepository.findByIdAndDeletedFalse(productId)?: throw Task1UniversalExceptionHandler(ErrorCodes.PRODUCT_NOT_FOUND)
            val transaction = transactionRepository.findByIdAndDeletedFalse(transactionId)?: throw Task1UniversalExceptionHandler(ErrorCodes.TRANSACTION_NOT_FOUND)
            transactionItemRepository.save(this.toEntity(product, transaction))
        }

    }

    override fun getAll(pageable: Pageable): Page<TransactionItemResponse> {
       return transactionItemRepository.findAllNotDeletedForPageable(pageable).map {
            TransactionItemResponse.toResponse(it)
        }
    }

    override fun getOne(id: Long): TransactionItemResponse {
      val transactionItm =  transactionItemRepository.findByIdAndDeletedFalse(id) ?: throw Task1UniversalExceptionHandler(ErrorCodes.TRANSACTION_NOT_FOUND)

        return TransactionItemResponse.toResponse(transactionItm)
    }

    override fun delete(id: Long) {

        transactionItemRepository.trash(id) ?: throw Task1UniversalExceptionHandler(ErrorCodes.TRANSACTION_NOT_FOUND)
    }

}

interface UserPaymentTransactionService{

    fun create(request: UserPaymentTransactionCreateRequest)

    fun getAll(pageable: Pageable) : Page<UserPaymentTransactionResponse>

    fun getOne(id: Long) : UserPaymentTransactionResponse

    fun delete(id: Long)

}

@Service
class UserPaymentTransactionServiceImpl(
    private val userPaymentTransactionRepository: UserPaymentTransactionRepository,
    private val userRepository: UserRepository
): UserPaymentTransactionService {

    override fun create(request: UserPaymentTransactionCreateRequest) {

        request.run {
           val user = userRepository.findByIdAndDeletedFalse(userId)?: throw UserNotFoundExceptionHandler()
            userPaymentTransactionRepository.save(this.toEntity(user))
        }

    }

    override fun getAll(pageable: Pageable): Page<UserPaymentTransactionResponse> {

       return userPaymentTransactionRepository.findAllNotDeletedForPageable(pageable).map {
            UserPaymentTransactionResponse.toResponse(it)
        }
    }

    override fun getOne(id: Long): UserPaymentTransactionResponse {
        val userPaymentTransaction = userPaymentTransactionRepository.findByIdAndDeletedFalse(id)?: throw Task1UniversalExceptionHandler(ErrorCodes.USER_NOT_FOUND)

        return UserPaymentTransactionResponse.toResponse(userPaymentTransaction)
    }

    override fun delete(id: Long) {

        userPaymentTransactionRepository.trash(id) ?: throw Task1UniversalExceptionHandler(ErrorCodes.USER_NOT_FOUND)

    }

}

interface TasksService{

    fun updateUserBalance(id: Long, balance: BigDecimal)

    fun userPaymentTransactionHistory(userId: Long, pageable: Pageable): Page<UserPaymentTransactionResponse>

    fun userTransactionItemHistory(userId: Long, pageable: Pageable): Page<TransactionItemResponse>

    fun transactionItems(id: Long): List<TransactionItemResponse>

    fun allTransactionsHistoryForAdmins(userId: Long, pageable: Pageable): Page<TransactionResponse>
}

@Service
class TaskServiceImpl(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val transactionItemRepository: TransactionItemRepository,
    private val userPaymentTransactionRepository: UserPaymentTransactionRepository,
    private val userPaymentTransactionService: UserPaymentTransactionService

):TasksService{

    @Transactional
    override fun updateUserBalance(id: Long, balance: BigDecimal) {

       val user = userRepository.findByIdAndDeletedFalse(id)?: throw Task1UniversalExceptionHandler(ErrorCodes.USER_NOT_FOUND)

        balance.let { user.balance += it }

        userRepository.save(user)

        val date = Date()

        val request = UserPaymentTransactionCreateRequest(user.id!!,balance,date)

        userPaymentTransactionService.create(request)

    }

    override fun userPaymentTransactionHistory(userId: Long, pageable: Pageable): Page<UserPaymentTransactionResponse> {

         return userPaymentTransactionRepository.findAllByUserId(userId, pageable).map {
             UserPaymentTransactionResponse.toResponse(it)
         }

    }

    override fun userTransactionItemHistory(userId: Long, pageable: Pageable): Page<TransactionItemResponse> {

       val userAllTransactions = transactionRepository.findAllByUserId(userId)

        val transactionIds = userAllTransactions.map { it.id!! }

       return transactionItemRepository.findAllByTransactionIds(transactionIds,pageable).map {
            TransactionItemResponse.toResponse(it)
        }
    }

    override fun transactionItems(id: Long): List<TransactionItemResponse> {

       return transactionItemRepository.findByTransactionId(id).map {
           TransactionItemResponse.toResponse(it)
       }

    }

    override fun allTransactionsHistoryForAdmins(userId: Long, pageable: Pageable): Page<TransactionResponse> {

        val user = userRepository.findByIdAndDeletedFalse(userId)?: throw Task1UniversalExceptionHandler(ErrorCodes.USER_NOT_FOUND)

        if(user.role != RoleEnum.ADMIN) throw Task1UniversalExceptionHandler(ErrorCodes.YOU_HAVE_NO_AUTHORITY)

        return transactionRepository.findAllNotDeletedForPageable(pageable).map {
            TransactionResponse.toResponse(it)
        }

    }

}