package br.com.jzsports.tournament_control.model.e;

public enum ETypePhase {
    GROUP, //
    ROUND_OF_16, //oitavas
    QUARTER_FINAL, // 8 times
    SEMI_FINAL, // 2x2
    FINAL; //1x1

    public ETypePhase getNext(){
        return switch (this){
            case  GROUP -> ROUND_OF_16;
            case ROUND_OF_16 -> QUARTER_FINAL;
            case QUARTER_FINAL -> SEMI_FINAL;
            case SEMI_FINAL -> FINAL;
            case FINAL -> null;
            default -> null;
        };
    }

}
