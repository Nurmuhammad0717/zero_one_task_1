package uz.backend.zero_one_intern_task1

enum class ErrorCodes(val code: Int) {

    USER_NOT_FOUND(100),
    USER_ALREADY_EXISTS(101),
    PRODUCT_NOT_FOUND(200),
    PRODUCT_ALREADY_EXISTS(201),
    SOMETHING_WENT_WRONG(500),
    CATEGORY_NOT_FOUND(300),
    TRANSACTION_NOT_FOUND(400),
    YOU_HAVE_NO_AUTHORITY(3)

}


enum class RoleEnum {

    USER, ADMIN

}

