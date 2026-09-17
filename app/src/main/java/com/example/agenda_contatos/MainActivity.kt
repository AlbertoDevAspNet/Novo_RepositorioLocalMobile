package com.example.agenda_contatos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadora.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Atividade principal da Agenda de Contatos.
 * Gerencia a exibição da lista de contatos (Selecionar) e coordena a inserção (Cadastrar),
 * atualização (Atualizar) e remoção (Excluir) por meio de janelas de diálogo (Dialogs).
 */
class MainActivity : AppCompatActivity() {

    // Instâncias das classes de banco de dados e do adaptador
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var contactAdapter: ContactAdapter

    // Referências aos componentes visuais da tela principal
    private lateinit var recyclerViewContacts: RecyclerView
    private lateinit var textViewEmptyState: TextView
    private lateinit var fabAddContact: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita o design de tela cheia ponta a ponta (edge-to-edge)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Aplica padding automático para compensar as barras de sistema (Status e Navegação)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o banco de dados local SQLite
        databaseHelper = DatabaseHelper(this)

        // Inicializa e vincula as referências dos componentes visuais
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts)
        textViewEmptyState = findViewById(R.id.textViewEmptyState)
        fabAddContact = findViewById(R.id.fabAddContact)

        // Configura o RecyclerView com um gerenciador de layout linear vertical
        recyclerViewContacts.layoutManager = LinearLayoutManager(this)

        // Configura o adaptador com listas vazias e os callbacks para cliques de Editar e Excluir
        contactAdapter = ContactAdapter(
            contactList = emptyList(),
            onEditClick = { contact -> showContactDialog(contact) },   // Abre o diálogo em modo de edição
            onDeleteClick = { contact -> showDeleteConfirmation(contact) } // Abre o diálogo de confirmação de exclusão
        )
        recyclerViewContacts.adapter = contactAdapter

        // Configura a ação de clique no botão flutuante para cadastrar um novo contato
        fabAddContact.setOnClickListener {
            showContactDialog(null) // Passa null para indicar um novo cadastro
        }

        // Carrega e exibe os contatos salvos inicialmente
        refreshContactList()
    }

    /**
     * R - SELECIONAR: Busca todos os contatos do banco e atualiza a interface gráfica.
     * Caso a lista esteja vazia, exibe uma mensagem informativa (Empty State).
     */
    private fun refreshContactList() {
        val contacts = databaseHelper.getAllContacts()

        // Se a lista estiver vazia, exibe o texto de estado vazio e oculta o RecyclerView
        if (contacts.isEmpty()) {
            textViewEmptyState.visibility = TextView.VISIBLE
            recyclerViewContacts.visibility = RecyclerView.GONE
        } else {
            textViewEmptyState.visibility = TextView.GONE
            recyclerViewContacts.visibility = RecyclerView.VISIBLE
        }

        // Atualiza os dados dentro do adaptador
        contactAdapter.updateData(contacts)
    }

    /**
     * C e U - CADASTRAR / ATUALIZAR: Exibe um AlertDialog contendo o formulário de contato.
     * @param contact Se for passado um objeto, o diálogo preencherá os campos e atuará como ATUALIZAÇÃO.
     *                Se for passado null, os campos ficarão limpos para um novo CADASTRO.
     */
    private fun showContactDialog(contact: Contact?) {
        // Infla o layout personalizado dialog_contact.xml
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null)

        // Obtém as referências dos campos de entrada de texto
        val textViewDialogTitle = dialogView.findViewById<TextView>(R.id.textViewDialogTitle)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)

        val isEditMode = contact != null

        // Se estiver editando, altera o título e preenche os campos com os dados existentes do contato
        if (isEditMode && contact != null) {
            textViewDialogTitle.text = "Atualizar Contato"
            editTextName.setText(contact.name)
            editTextPhone.setText(contact.phone)
            editTextEmail.setText(contact.email)
        } else {
            textViewDialogTitle.text = "Cadastrar Novo Contato"
        }

        // Constrói e exibe o AlertDialog
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .setPositiveButton(if (isEditMode) "Atualizar" else "Salvar", null) // Definido como null primeiro para customizar validação
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create().apply {
                // Sobrescreve o clique do botão positivo para evitar fechar o diálogo se houver erro de validação
                setOnShowListener {
                    val button = getButton(AlertDialog.BUTTON_POSITIVE)
                    button.setOnClickListener {
                        val name = editTextName.text.toString().trim()
                        val phone = editTextPhone.text.toString().trim()
                        val email = editTextEmail.text.toString().trim()

                        // Validação de campos obrigatórios
                        if (name.isEmpty()) {
                            editTextName.error = "O nome é obrigatório"
                            return@setOnClickListener
                        }
                        if (phone.isEmpty()) {
                            editTextPhone.error = "O telefone é obrigatório"
                            return@setOnClickListener
                        }

                        if (isEditMode && contact != null) {
                            // U - ATUALIZAR: Cria um objeto contendo o ID antigo e os novos valores
                            val updatedContact = Contact(id = contact.id, name = name, phone = phone, email = email)
                            val rowsAffected = databaseHelper.updateContact(updatedContact)
                            if (rowsAffected > 0) {
                                Toast.makeText(this@MainActivity, "Contato atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // C - CADASTRAR: Cria um novo contato e insere no banco
                            val newContact = Contact(name = name, phone = phone, email = email)
                            val insertedId = databaseHelper.insertContact(newContact)
                            if (insertedId > -1) {
                                Toast.makeText(this@MainActivity, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        // Atualiza a lista na tela e fecha o diálogo
                        refreshContactList()
                        dismiss()
                    }
                }
                show()
            }
    }

    /**
     * D - EXCLUIR: Mostra uma caixa de diálogo para confirmar a exclusão do contato selecionado.
     * @param contact O contato que se deseja excluir do sistema.
     */
    private fun showDeleteConfirmation(contact: Contact) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Contato")
            .setMessage("Tem certeza de que deseja excluir o contato \"${contact.name}\"?")
            .setPositiveButton("Sim, Excluir") { dialog, _ ->
                // Executa a remoção no banco de dados através do ID
                val rowsDeleted = databaseHelper.deleteContact(contact.id)
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Contato excluído!", Toast.LENGTH_SHORT).show()
                    // Recarrega os contatos atualizados na tela
                    refreshContactList()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
