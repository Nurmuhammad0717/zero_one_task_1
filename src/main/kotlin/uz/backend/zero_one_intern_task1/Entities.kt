package uz.backend.zero_one_intern_task1

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.util.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbsLongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @CreatedDate @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null,

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var modifiedDate: Date? = null,

    @CreatedBy
    var createdBy: Long? = null,

    @LastModifiedBy
    var lastModifiedBy: Long? = null,

    @Column(nullable = false) @ColumnDefault(value = "false")
    var deleted: Boolean = false
)

@Entity(name = "users")
class User(

    @Column(nullable =false )
    var fullName : String,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: RoleEnum,

    var balance : BigDecimal,

) : AbsLongEntity()

@Entity
class Category(

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var categoryOrder: Long,

    var description : String,

) : AbsLongEntity()

@Entity
class Product(

    @Column(nullable = false)
    var name: String,

    var count : Long,

    @ManyToOne
    var category : Category,

): AbsLongEntity()

@Entity
class Transaction(

    @ManyToOne
    val user : User,

    val totalAmount : BigDecimal,

    val date : Date,

): AbsLongEntity()

@Entity
class TransactionItem(

    @ManyToOne
    var product : Product,

    val amount : Long,

    val price : BigDecimal,

    val totalAmount : BigDecimal,

    @ManyToOne
    val transaction: Transaction,

): AbsLongEntity()

@Entity
class UserPaymentTransaction(

    @ManyToOne
    var user: User,

    val amount: BigDecimal,

    val date : Date,

): AbsLongEntity()



