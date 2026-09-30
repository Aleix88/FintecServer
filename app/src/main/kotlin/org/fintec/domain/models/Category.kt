package org.fintec.domain.models

// Categories belong to the user, not the account. This means that categories and budgets can be share through
// multiple accounts.
data class Category(
    var id: String,
    val name: String,
    val userId: String,
    val hexColor: String
)