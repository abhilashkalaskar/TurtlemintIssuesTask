package com.abhilash.githubissues.data.error

import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

