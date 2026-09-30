package org.fintec.data.repositories

import org.fintec.data.db.CacheDB
import org.fintec.domain.models.Account
import org.fintec.domain.repositories.AccountRepositoryCheckIfExistsParams
import org.fintec.domain.repositories.AccountRepositoryCreateParams
import org.fintec.domain.repositories.AccountRepositoryGetOneParams
import org.fintec.domain.repositories.AccountRepositoryGetParams
import org.fintec.domain.repositories.AccountRepositoryType
import org.fintec.domain.repositories.AccountRepositoryUpdateParams

class AccountRepository: AccountRepositoryType {

    override fun createAccount(params: AccountRepositoryCreateParams) {
        CacheDB.accounts.add(params.account)
    }

    override fun updateAccount(params: AccountRepositoryUpdateParams) {
        CacheDB.accounts.replaceAll { if (it.id == params.account.id) params.account else it }
    }

    override fun getAccounts(params: AccountRepositoryGetParams): List<Account> {
        val accounts = CacheDB.accounts.filter { account: Account -> account.userId == params.userId }
        return accounts
    }

    override fun getOneAccount(params: AccountRepositoryGetOneParams): Account? {
        return CacheDB.accounts.find { it.id == params.accountId }
    }

    override fun checkIfExists(params: AccountRepositoryCheckIfExistsParams): Boolean {
        return CacheDB.accounts.filter { it.id == params.accountId }.isNotEmpty()
    }
}