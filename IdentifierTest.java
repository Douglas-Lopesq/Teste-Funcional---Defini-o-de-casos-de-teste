package testIdentifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;

class IdentifierTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("casosDeTeste")
    @DisplayName("Validação de identificadores conforme classes de equivalência e limites")
    void testValidateIdentifier(
            String cenario,
            String entrada,
            boolean esperado) {
        // Setup
        Identifier id = new Identifier();

        // Invocation
        boolean resultado;
        if (entrada == null) {
            resultado = assertDoesNotThrow(() -> id.validateIdentifier(entrada));
        } else {
            resultado = id.validateIdentifier(entrada);
        }

        // Assessment
        if (esperado) {
            assertTrue(resultado, cenario);
        } else {
            assertFalse(resultado, cenario);
        }
    }

    private static Stream<Arguments> casosDeTeste() {
        return Stream.of(
                Arguments.of("CT01 - Entrada nula", null, false),
                Arguments.of("CT02 - Comprimento mínimo inválido (0)", "", false),
                Arguments.of("CT03 - Comprimento mínimo válido (1)", "A", true),
                Arguments.of("CT04 - Comprimento máximo válido (6)", "z9B2c7", true),
                Arguments.of("CT05 - Acima do comprimento máximo (7)", "A123456", false),
                Arguments.of("CT06 - Primeiro caractere numérico", "1abc", false),
                Arguments.of("CT07 - Primeiro caractere símbolo", "_abc", false),
                Arguments.of("CT08 - Símbolo nos caracteres restantes", "Ab#1", false),
                Arguments.of("CT09 - Caractere não ASCII nos caracteres restantes", "Aé", false),
                Arguments.of("CT10 - Caractere não ASCII na primeira posição", "éA", false),
                Arguments.of("CT11 - Letras e dígitos ASCII válidos", "aBc09", true));
    }
}
