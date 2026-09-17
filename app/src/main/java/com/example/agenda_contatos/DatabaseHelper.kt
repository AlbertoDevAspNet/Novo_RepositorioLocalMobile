package com.example.agenda_contatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Gerenciador do Banco de Dados SQLite para a aplicação de Agenda de Contatos.
 * Responsável por criar a tabela e realizar as operações de CRUD (Create, Read, Update, Delete).
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Nome e versão do banco de dados
        private const val DATABASE_NAME = "agenda_contatos.db"
        private const val DATABASE_VERSION = 1

        // Nome da tabela e colunas
        private const val TABLE_NAME = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
    }

    /**
     * Função chamada quando o banco de dados é criado pela primeira vez.
     * Aqui criamos a estrutura da tabela de contatos.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_NAME TEXT NOT NULL,"
                + "$COLUMN_PHONE TEXT NOT NULL,"
                + "$COLUMN_EMAIL TEXT)")
        // Executa o comando SQL para criar a tabela
        db?.execSQL(createTableQuery)
    }

    /**
     * Função chamada quando o banco de dados precisa ser atualizado (ex: mudança de versão).
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Remove a tabela antiga se existir e cria uma nova
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // ==========================================
    // OPERAÇÕES DO CRUD
    // ==========================================

    /**
     * C - CADASTRAR (Inserir um novo contato no banco de dados)
     * @param contact Objeto contendo os dados do contato a ser inserido.
     * @return O ID da linha inserida, ou -1 se ocorrer um erro.
     */
    fun insertContact(contact: Contact): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        // Insere os dados na tabela e fecha a conexão
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    /**
     * R - SELECIONAR / LER (Buscar todos os contatos cadastrados)
     * @return Uma lista contendo todos os contatos ordenados por nome.
     */
    fun getAllContacts(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val db = this.readableDatabase
        // Query para selecionar todos os registros ordenados alfabeticamente pelo nome
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME ASC"
        val cursor = db.rawQuery(selectQuery, null)

        // Percorre o cursor para ler os dados de cada linha retornado do banco
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))

                val contact = Contact(id, name, phone, email)
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        // Fecha o cursor e o banco de dados
        cursor.close()
        db.close()
        return contactList
    }

    /**
     * U - ATUALIZAR (Modificar os dados de um contato existente)
     * @param contact Objeto do contato contendo o ID correto e as novas informações.
     * @return O número de linhas afetadas.
     */
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        // Atualiza a linha correspondente ao ID do contato
        val success = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(contact.id.toString()))
        db.close()
        return success
    }

    /**
     * D - EXCLUIR (Remover um contato do banco de dados)
     * @param id ID do contato que será deletado.
     * @return O número de linhas deletadas.
     */
    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase
        // Deleta o registro onde o ID coincide
        val success = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return success
    }
}
