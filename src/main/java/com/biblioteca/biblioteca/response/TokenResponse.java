package com.biblioteca.biblioteca.response;

public class TokenResponse {

    private String token;
    private String idUsuarioLogado;

    public TokenResponse(String token) {
        this.token = token;
    }

    public TokenResponse(String token, String idUsuarioLogado) {
        this.token = token;
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(String idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }
}
