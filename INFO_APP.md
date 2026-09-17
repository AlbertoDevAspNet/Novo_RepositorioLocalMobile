# Agenda de Contatos - Aplicativo Android

Este é um aplicativo de **Agenda de Contatos** completo, desenvolvido nativamente para Android utilizando a linguagem **Kotlin** para a lógica de negócios e **XML** para a estruturação de layouts de interface.

---

## 🚀 Funcionalidades (Operações CRUD)

O aplicativo implementa o ciclo completo de gerenciamento de dados local (CRUD):
1. **Cadastrar (Create):** Permite adicionar um novo contato informando Nome Completo, Telefone/Celular e E-mail através de um formulário interativo de diálogo.
2. **Selecionar / Listar (Read):** Busca todos os contatos salvos no banco de dados SQLite interno e os exibe organizados em ordem alfabética dentro de uma lista otimizada com `RecyclerView`.
3. **Atualizar (Update):** Ao tocar no ícone de edição de qualquer contato na lista, o formulário se abre preenchido permitindo alterar qualquer dado do registro selecionado.
4. **Excluir (Delete):** Ao tocar no ícone de lixeira, uma caixa de confirmação é exibida perguntando se o usuário deseja mesmo apagar o registro, removendo-o do banco permanentemente após a aprovação.

---

## 🛠️ Tecnologias e Componentes Utilizados

- **Kotlin:** Linguagem oficial utilizada no desenvolvimento das regras de negócio, tratamento de eventos de cliques e validações de dados.
- **Layouts em XML:** Estruturas declarativas tradicionais que dão vida ao visual moderno da aplicação:
  - `activity_main.xml`: Tela principal estruturada com `CoordinatorLayout`, `Toolbar`, `RecyclerView` e `FloatingActionButton` (FAB).
  - `item_contact.xml`: Desenho visual individualizado de cada item da lista utilizando o componente `CardView`.
  - `dialog_contact.xml`: Formulário responsivo e envelopado por um `ScrollView` que atende tanto o cadastro quanto a atualização de dados.
- **SQLite Database:** Banco de dados relacional local, leve e embutido no Android. Utiliza a classe nativa `SQLiteOpenHelper` para criar tabelas e gerenciar conexões sem dependências externas pesadas.
- **RecyclerView:** Componente de alta performance para exibição eficiente de listas dinâmicas no Android de forma fluida.
- **Material Design 3:** Componentes visuais refinados, elevações e estilizações alinhadas com as diretrizes do Google Android.

---

## 📁 Estrutura de Arquivos Criados

- **`Contact.kt`**: Modelo de dados que representa a entidade `Contact` com os campos `id`, `name`, `phone` e `email`.
- **`DatabaseHelper.kt`**: Camada de persistência que faz toda a comunicação SQL direta, manipulação de `ContentValues` e leitura via `Cursor`.
- **`ContactAdapter.kt`**: Ponte reguladora entre a lista de contatos do banco e as views visuais renderizadas na tela.
- **`MainActivity.kt`**: Controlador central que inicializa os componentes, gerencia os fluxos de cliques e dispara os alertas de aviso/validação (`Toast` e `AlertDialog`).
- **`INFO_APP.md`**: Este arquivo de leitura com toda a documentação explicativa sobre o projeto.

---

## 📝 Observações de Código

Conforme solicitado, todo o código-fonte nas camadas de **Layout XML** e **Lógica em Kotlin** foi devidamente documentado com comentários linha a linha explicando o propósito de cada componente criado e a responsabilidade de cada função implementada, servindo como uma excelente base de aprendizado.
