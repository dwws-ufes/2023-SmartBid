package br.com.ufes.labes.smartbid.admin.domain.enumerate;

public enum TipoIdentificacao {
    FISICA, JURIDICA;

    public static TipoIdentificacao fromString(String text) {
        for (TipoIdentificacao b : TipoIdentificacao.values()) {
            if (b.toString().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
