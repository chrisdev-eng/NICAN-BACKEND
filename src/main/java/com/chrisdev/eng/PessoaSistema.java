package com.chrisdev.eng;

    //classe abstrata para definir o que cada tipo de usuario (admin, usuario comum possa executar de acordo
    //com suas permissões
    //classe usuaria a ser implementada futuramente

public abstract class PessoaSistema {
    public abstract String acessarSistema();
    public abstract String visualizarRelatorio();
}
