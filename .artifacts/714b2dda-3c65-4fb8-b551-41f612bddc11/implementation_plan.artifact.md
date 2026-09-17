# Plano de Implementação: Calculadora Simples em Jetpack Compose

Este plano descreve a criação de uma calculadora simples no pacote `com.example.calculadora`, utilizando Jetpack Compose para a interface e uma arquitetura limpa com ViewModel para a lógica.

## Mudanças Propostas

### UI e Lógica da Calculadora

#### [NEW] [CalculatorViewModel.kt](file:///C:/Users/Alberto/OneDrive/Documentos/Novo_RepositorioLocalMobile/app/src/main/java/com/example/calculadora/CalculatorViewModel.kt)
- Criar um ViewModel para gerenciar o estado da calculadora (número atual, operador, resultado).
- Implementar as funções básicas: adição, subtração, multiplicação e divisão.

#### [NEW] [CalculatorScreen.kt](file:///C:/Users/Alberto/OneDrive/Documentos/Novo_RepositorioLocalMobile/app/src/main/java/com/example/calculadora/CalculatorScreen.kt)
- Desenvolver a interface da calculadora usando Compose.
- `CalculatorDisplay`: Área para mostrar o número digitado e o resultado.
- `CalculatorButton`: Componente reutilizável para os botões numéricos e de operação.
- `CalculatorGrid`: Layout em grade para organizar os botões.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/Alberto/OneDrive/Documentos/Novo_RepositorioLocalMobile/app/src/main/java/com/example/calculadora/MainActivity.kt)
- Refatorar a `MainActivity` para herdar de `ComponentActivity`.
- Configurar o `setContent` para exibir a `CalculatorScreen`.
- Habilitar o modo Edge-to-Edge para uma aparência moderna.

#### [DELETE] [activity_main.xml](file:///C:/Users/Alberto/OneDrive/Documentos/Novo_RepositorioLocalMobile/app/src/main/res/layout/activity_main.xml)
- Remover o layout XML antigo, pois utilizaremos Compose.

## Plano de Verificação

### Testes Manuais
- Verificar se os números são exibidos corretamente ao clicar nos botões.
- Testar as quatro operações básicas (+, -, *, /).
- Verificar a função de limpar (C/AC).
- Validar o comportamento com números decimais.
- Verificar a interface em diferentes orientações (se aplicável).
