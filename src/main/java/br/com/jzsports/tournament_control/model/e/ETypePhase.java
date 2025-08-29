package br.com.jzsports.tournament_control.model.e;

public enum ETypePhase {
    GROUP, //
    ROUND_OF_16, //oitavas
    QUARTER_FINAL, // 8 times
    SEMI_FINAL, // 2x2
    FINAL; //1x1

    /**
     * Define a próxima fase com base na quantidade de times classificados.
     * Regras:
     * - 2 times    → FINAL
     * - 4 times    → SEMI_FINAL
     * - 8 times    → QUARTER_FINAL
     * - 16 times   → ROUND_OF_16
     * - >16 times  → GROUP
     */
    public static ETypePhase fromTeamCount(int teamCount) {
        return switch (teamCount) {
            case 2 -> FINAL;
            case 4 -> SEMI_FINAL;
            case 8 -> QUARTER_FINAL;
            case 16 -> ROUND_OF_16;
            default -> GROUP;
        };
    }

    /**
     * Continua permitindo buscar a próxima fase na ordem padrão,
     * independente da quantidade de times.
     */
    public ETypePhase getNext() {
        return switch (this) {
            case GROUP -> ROUND_OF_16;
            case ROUND_OF_16 -> QUARTER_FINAL;
            case QUARTER_FINAL -> SEMI_FINAL;
            case SEMI_FINAL -> FINAL;
            case FINAL -> null;
        };
    }

}
