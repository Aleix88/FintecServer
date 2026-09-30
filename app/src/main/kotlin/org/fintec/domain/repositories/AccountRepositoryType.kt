package org.fintec.domain.repositories

import org.fintec.data.db.CacheDB
import org.fintec.domain.models.Account

data class AccountRepositoryCreateParams(val account: Account)
data class AccountRepositoryGetParams(val userId: String)
data class AccountRepositoryGetOneParams(val accountId: String)
data class AccountRepositoryUpdateParams(val account: Account)
data class AccountRepositoryCheckIfExistsParams(val accountId: String)

interface AccountRepositoryType {
    fun createAccount(params: AccountRepositoryCreateParams)
    fun updateAccount(params: AccountRepositoryUpdateParams)
    fun getAccounts(params: AccountRepositoryGetParams): List<Account>
    fun getOneAccount(params: AccountRepositoryGetOneParams): Account?
    fun checkIfExists(params: AccountRepositoryCheckIfExistsParams): Boolean
}