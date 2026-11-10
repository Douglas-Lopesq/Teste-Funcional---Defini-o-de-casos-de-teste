# Teste funcional da classe `Identifier`

## Regras e variáveis de entrada

`validateIdentifier(String s)` recebe uma única string. Para a análise funcional, suas características são particionadas nestas variáveis:

| Variável | Regra |
|---|---|
| Comprimento | De 1 a 6 caracteres, inclusive. |
| Primeiro caractere | Deve ser uma letra ASCII (`A-Z` ou `a-z`). |
| Caracteres restantes | Cada caractere deve ser uma letra ASCII (`A-Z` ou `a-z`) ou um dígito ASCII (`0-9`). Se não houver caracteres restantes, esta condição é satisfeita. |
| Valor nulo | `null` é entrada inválida e não deve provocar exceção. |

## Classes de equivalência

| ID | Tipo | Variável | Classe |
|---|---|---|---|
| CEV1 | Válida | Comprimento | De 1 a 6 caracteres. |
| CEV2 | Válida | Primeiro caractere | Letra ASCII maiúscula ou minúscula. |
| CEV3 | Válida | Caracteres restantes | Zero ou mais letras/dígitos ASCII. |
| CEI1 | Inválida | Valor da entrada | `null`. |
| CEI2 | Inválida | Comprimento | String vazia (comprimento 0). |
| CEI3 | Inválida | Comprimento | Mais de 6 caracteres. |
| CEI4 | Inválida | Primeiro caractere | Dígito (`0-9`). |
| CEI5 | Inválida | Primeiro caractere | Símbolo ASCII (não é letra nem dígito), por exemplo `_`. |
| CEI6 | Inválida | Caracteres restantes | Símbolo ASCII (não é letra nem dígito), por exemplo `#`. |
| CEI7 | Inválida | Primeiro caractere | Caractere não ASCII, por exemplo `é`. |
| CEI8 | Inválida | Caracteres restantes | Caractere não ASCII, por exemplo `é`. |

As classes de caractere são separadas pela posição e pelo tipo: dígito inicial (CEI4), símbolo ASCII inicial (CEI5) e caractere não ASCII inicial (CEI7) são alternativas distintas; o mesmo vale para símbolos ASCII (CEI6) e caracteres não ASCII (CEI8) nas posições restantes. As classes válidas precisam ser satisfeitas simultaneamente. A presença de uma classe inválida torna o identificador inválido, mesmo quando as demais condições são válidas.

## Análise de Valor Limite (AVL)

| Comprimento | Posição em relação aos limites | Resultado esperado | Caso |
|---:|---|---|---|
| 0 | Abaixo do mínimo permitido (1) | `false` | CT02 |
| 1 | Limite mínimo permitido | `true` para entrada com primeiro caractere válido | CT03 |
| 6 | Limite máximo permitido | `true` para entrada com caracteres válidos | CT04 |
| 7 | Acima do máximo permitido (6) | `false` | CT05 |

## Matriz de casos de teste derivados

| ID do Caso de Teste | Entrada (`s`) | Descrição do cenário | Classes cobertas | Resultado esperado |
|---|---|---|---|---|
| CT01 | `null` | Entrada nula é rejeitada sem lançar exceção. | CEI1 | `false` |
| CT02 | `""` | String vazia; comprimento abaixo do mínimo. | CEI2; AVL 0 | `false` |
| CT03 | `"A"` | Letra válida com comprimento mínimo; sem caracteres restantes. | CEV1, CEV2, CEV3; AVL 1 | `true` |
| CT04 | `"z9B2c7"` | Letras e dígitos válidos no comprimento máximo. | CEV1, CEV2, CEV3; AVL 6 | `true` |
| CT05 | `"A123456"` | Caracteres permitidos, mas comprimento acima do máximo. | CEI3; AVL 7 | `false` |
| CT06 | `"1abc"` | Primeiro caractere é dígito. | CEI4 | `false` |
| CT07 | `"_abc"` | Primeiro caractere é símbolo. | CEI5 | `false` |
| CT08 | `"Ab#1"` | Primeiro caractere válido, mas há símbolo nos caracteres restantes. | CEV2, CEI6 | `false` |
| CT09 | `"Aé"` | Primeiro caractere válido, mas há letra não ASCII nos caracteres restantes. | CEV2, CEI8 | `false` |
| CT10 | `"éA"` | Caractere não ASCII na primeira posição. | CEI7 | `false` |
| CT11 | `"aBc09"` | Combinação válida de letras maiúsculas/minúsculas e dígitos. | CEV1, CEV2, CEV3 | `true` |

## Automação JUnit 5

A classe `IdentifierTest.java` executa os onze casos da matriz por meio de um teste parametrizado. Cada execução instancia `Identifier` (Setup), chama `validateIdentifier` (Invocation) e compara o resultado esperado (Assessment). Para CT01, `assertDoesNotThrow` também verifica explicitamente que `null` não causa exceção.

O arquivo de teste usa `org.junit.jupiter` e pressupõe que JUnit 5 esteja disponível no classpath do projeto. O nome geral da função de teste é fornecido por `@DisplayName`; `@ParameterizedTest(name = "{0}")` usa a descrição em português de cada caso como nome da execução individual no relatório do JUnit.
