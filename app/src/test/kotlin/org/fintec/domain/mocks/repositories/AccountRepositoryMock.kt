package org.fintec.domain.mocks.repositories

import org.fintec.domain.models.Account
import org.fintec.domain.repositories.AccountRepositoryCheckIfExistsParams
import org.fintec.domain.repositories.AccountRepositoryCreateParams
import org.fintec.domain.repositories.AccountRepositoryGetOneParams
import org.fintec.domain.repositories.AccountRepositoryGetParams
import org.fintec.domain.repositories.AccountRepositoryType
import org.fintec.domain.repositories.AccountRepositoryUpdateParams

class AccountRepositoryMock: AccountRepositoryType {
    val accountsResult = mutableListOf<Account>()
    var oneAccountResult: Account? = null
    var checkIfExistsResult = false
    val createAccountCalls = mutableListOf<AccountRepositoryCreateParams>()
    val updateAccountCalls = mutableListOf<AccountRepositoryUpdateParams>()
    val getOneAccountCalls = mutableListOf<AccountRepositoryGetOneParams>()
    val checkIfExistsCalls = mutableListOf<AccountRepositoryCheckIfExistsParams>()
    val getAccountsCalls = mutableListOf<AccountRepositoryGetParams>()

    override fun createAccount(params: AccountRepositoryCreateParams) {
        createAccountCalls.add(params)
    }

    override fun updateAccount(params: AccountRepositoryUpdateParams) {
        updateAccountCalls.add(params)
    }

    override fun getAccounts(params: AccountRepositoryGetParams): List<Account> {
        getAccountsCalls.add(params)
        return accountsResult
    }

    override fun getOneAccount(params: AccountRepositoryGetOneParams): Account? {
        getOneAccountCalls.add(params)
        return oneAccountResult
    }

    override fun checkIfExists(params: AccountRepositoryCheckIfExistsParams): Boolean {
        checkIfExistsCalls.add(params)
        return checkIfExistsResult
    }
}