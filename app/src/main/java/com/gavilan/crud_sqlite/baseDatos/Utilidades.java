package com.gavilan.crud_sqlite.baseDatos;

public class Utilidades {
    public static final String CREAR_TABLA_CLIENTE =
            "CREATE TABLE clientes(id INTEGER, nombre TEXT, userName TEXT, password TEXT)";

    public static final String NOMBRE_TABLA = "clientes";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_USERNAME = "userName";
    public static final String CAMPO_PASSWORD = "password";
}
