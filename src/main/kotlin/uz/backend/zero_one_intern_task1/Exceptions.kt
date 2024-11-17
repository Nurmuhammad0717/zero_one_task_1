package uz.backend.zero_one_intern_task1

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

sealed class Task1ExceptionHandler : RuntimeException(){

    abstract fun errorCode(): ErrorCodes

    open fun getAllArguments(): Array<Any?>? = null

    fun getErrorMessage(resourceBundle: ResourceBundleMessageSource): BaseMessage {

        val message = try {
            resourceBundle.getMessage(
                errorCode().name, getAllArguments(), LocaleContextHolder.getLocale()
            )
        } catch (e: Exception) {
            e.message
        }
        return BaseMessage(errorCode().code, message)

    }

}

class UserAlreadyExistsExceptionHandler() : Task1ExceptionHandler() {
    override fun errorCode(): ErrorCodes = ErrorCodes.USER_ALREADY_EXISTS
}

class UserNotFoundExceptionHandler : Task1ExceptionHandler() {
    override fun errorCode() = ErrorCodes.USER_NOT_FOUND
}

class SomethingWentWrongExceptionHandler() : Task1ExceptionHandler() {
    override fun errorCode() = ErrorCodes.SOMETHING_WENT_WRONG
}

class CategoryNotFoundExceptionHandler() : Task1ExceptionHandler() {
    override fun errorCode(): ErrorCodes = ErrorCodes.CATEGORY_NOT_FOUND
}

class Task1UniversalExceptionHandler(errorCodes: ErrorCodes) : Task1ExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return errorCode()
    }

}