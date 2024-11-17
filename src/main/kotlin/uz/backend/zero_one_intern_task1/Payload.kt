package uz.backend.zero_one_intern_task1

import java.math.BigDecimal
import java.util.Date

data class BaseMessage(val code: Int, val message: String?)

data class UserCreateRequest(
    val fullName: String,
    val username: String,
    val password: String,
    val balance : BigDecimal,
){
    fun toEntity(): User {
        return User(fullName, username, password, RoleEnum.USER, balance)
    }
}


data class UserUpdateRequest(
    val fullName: String,
    val username: String,
    val password: String,
    val balance: BigDecimal,
)

data class UserResponse(
    val id: Long,
    val fullName: String,
    val username: String,
    val password: String,
    val role: RoleEnum,
    val balance : BigDecimal,
) {
    companion object {
        fun toResponse(user: User): UserResponse {
             user.run {
                 return UserResponse(id!!, fullName, username, password, role, balance)
             }
        }
    }
}

data class ProductCreateRequest(
    val name: String,
    val count: Long,
    val categoryId: Long,
){
    fun toEntity(category: Category): Product {
        return Product(name, count, category)
    }
}

data class ProductUpdateRequest(
    val name: String,
    val count: Long,
    val categoryId: Long,
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val count: Long,
    val category: String,
) {
    companion object {
        fun toResponse(product: Product): ProductResponse {
            product.run {
                return ProductResponse(id!!,name,count,category.name)
            }
        }
    }
}


data class CategoryCreateRequest(
    val name: String,
    val order: Long,
    val description: String,
){
    fun toEntity(): Category {
        return Category(name, order, description)
    }
}

data class CategoryResponse(
    val id: Long,
    val name: String,
    val order: Long,
    val description: String,
){
    companion object {
        fun toResponse(category:Category): CategoryResponse {
           category.run {
               return CategoryResponse(id!!,name,categoryOrder, description)
           }
        }
    }
}

data class CategoryUpdateRequest(
    val name: String,
    val order: Long,
    val description: String,
)

data class TransactionCreateRequest(
    val userId: Long,
    val totalAmount: BigDecimal,
    val date: Date
){
    fun toEntity(user: User): Transaction {
        return Transaction(user,totalAmount,date)
    }
}

data class TransactionResponse(
    val id: Long,
    val user: UserResponse,
    val totalAmount: BigDecimal,
    val date: Date,
) {
    companion object {
        fun toResponse(transaction : Transaction): TransactionResponse {

            transaction.run {
                return TransactionResponse(id!!, UserResponse.toResponse(user),totalAmount,date)
            }

        }
    }
}

data class TransactionItemCreateRequest(
    val productId: Long,
    val quantity: Long,
    val price: BigDecimal,
    val totalAmount: BigDecimal,
    val transactionId: Long,
){
    fun toEntity(product: Product,transaction: Transaction): TransactionItem {

        return TransactionItem(product,quantity,price,totalAmount,transaction)

    }
}


data class TransactionItemResponse(
    val id: Long,
    val product: ProductResponse,
    val quantity: Long,
    val price: BigDecimal,
    val totalAmount: BigDecimal,
    val transactionId: TransactionResponse,
) {
    companion object {
        fun toResponse(transactionItem: TransactionItem): TransactionItemResponse {
             transactionItem.run {
                 return TransactionItemResponse(id!!,ProductResponse
                     .toResponse(product),amount,price,totalAmount,TransactionResponse
                         .toResponse(transaction))
             }
        }
    }
}

data class UserPaymentTransactionCreateRequest(
    val userId: Long,
    val totalAmount: BigDecimal,
    val date: Date,
) {
    fun toEntity(user: User): UserPaymentTransaction {
        return UserPaymentTransaction(user,totalAmount,date)
    }
}

data class UserPaymentTransactionResponse(
    val id: Long,
    val user: UserResponse,
    val totalAmount: BigDecimal,
    val date: Date,
) {
    companion object {
        fun toResponse(userPaymentTransaction: UserPaymentTransaction): UserPaymentTransactionResponse {

            userPaymentTransaction.run {
                return UserPaymentTransactionResponse(id!!,UserResponse.toResponse(user),amount,date)
            }

        }
    }
}