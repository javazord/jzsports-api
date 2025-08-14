package br.com.jzsports.tournament_control.model.e;

public enum ETypePhase {
    GROUP, ROUND_OF_16, QUARTER_FINAL, SEMI_FINAL, FINAL;

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
