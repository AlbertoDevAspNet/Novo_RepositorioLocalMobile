package com.example.agenda_contatos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadora.R

/**
 * Adaptador para gerenciar a exibição dos contatos em um RecyclerView.
 *
 * @param onEditClick Função de callback chamada ao clicar no botão de editar.
 * @param onDeleteClick Função de callback chamada ao clicar no botão de excluir.
 */
class ContactAdapter(
    private var contactList: List<Contact>,
    private val onEditClick: (Contact) -> Unit,
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    /**
     * ViewHolder que contém as referências dos componentes de interface para cada item da lista.
     */
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    /**
     * Cria novos ViewHolders (infla o layout item_contact.xml).
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    /**
     * Associa os dados do contato aos componentes do ViewHolder na posição especificada.
     */
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]

        // Preenche os textos com os dados do contato
        holder.textViewName.text = contact.name
        holder.textViewPhone.text = contact.phone
        holder.textViewEmail.text = contact.email

        // Configura o clique no botão de editar
        holder.buttonEdit.setOnClickListener {
            onEditClick(contact)
        }

        // Configura o clique no botão de excluir
        holder.buttonDelete.setOnClickListener {
            onDeleteClick(contact)
        }
    }

    /**
     * Retorna a quantidade total de itens na lista.
     */
    override fun getItemCount(): Int = contactList.size

    /**
     * Atualiza a lista de contatos do adaptador e notifica o RecyclerView sobre a mudança.
     */
    fun updateData(newList: List<Contact>) {
        this.contactList = newList
        notifyDataSetChanged()
    }
}
