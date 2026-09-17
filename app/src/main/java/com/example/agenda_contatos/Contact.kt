package com.example.agenda_contatos

/**
 * Classe de modelo de dados para representar um Contato.
 *
 * @property id Identificador único do contato no banco de dados (auto-incremento).
 * @property name Nome completo do contato.
 * @property phone Número de telefone do contato.
 * @property email Endereço de e-mail do contato.
 */
data class Contact(
    val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String
)
