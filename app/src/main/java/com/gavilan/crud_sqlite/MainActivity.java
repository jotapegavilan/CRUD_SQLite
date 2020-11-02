package com.gavilan.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gavilan.crud_sqlite.baseDatos.Utilidades;
import com.gavilan.crud_sqlite.baseDatos.conexionSQLiteHelper;
import com.gavilan.crud_sqlite.baseDatos.entidades.Cliente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // widgets
    private EditText txtID, txtNombre, txtUserName, txtPassword;
    private Button btnCrear, btnActualizar, btnEliminar;
    private ListView listViewClientes;
    //Manipulación de bd
    conexionSQLiteHelper conn = new conexionSQLiteHelper(MainActivity.this,null,null,1);
    ArrayList<Cliente> arrayClientes;
    Cliente cliente;
    ArrayAdapter<Cliente> adaptadorListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Referencia UI
        txtID = findViewById(R.id.txtID);
        txtNombre = findViewById(R.id.txtNombre);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnCrear = findViewById(R.id.btnCrear);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listViewClientes = findViewById(R.id.listviewClientes);

        // Inicializando objetos
        arrayClientes = new ArrayList<>();
        adaptadorListView =
                new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayClientes);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Capturar los datos escritos por el usuario
                int identidicador = Integer.parseInt(txtID.getText().toString());
                String nombre = txtNombre.getText().toString();
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                insertarCliente(identidicador,nombre,userName,password);
            }
        });


        obtenerClientes();

    }

    public void insertarCliente(int id, String nombre, String userName, String password) {
        SQLiteDatabase database = conn.getWritableDatabase();
        // Crear un Contenedor de valores a insertar
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, id);
        values.put(Utilidades.CAMPO_NOMBRE, nombre);
        values.put(Utilidades.CAMPO_USERNAME, userName);
        values.put(Utilidades.CAMPO_PASSWORD, password);
        database.insert(Utilidades.NOMBRE_TABLA, Utilidades.CAMPO_ID, values);
        Toast.makeText(MainActivity.this, "Cliente guardado", Toast.LENGTH_LONG).show();
        database.close();
        obtenerClientes();
    }

    public void obtenerClientes(){
        adaptadorListView.clear();
        SQLiteDatabase database = conn.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Utilidades.NOMBRE_TABLA,null);
        while (cursor.moveToNext()){
            cliente = new Cliente(
                    cursor.getInt(0), // ID del cliente que trae la consulta
                    cursor.getString(1), // Nombre del cliente que trae la consulta
                    cursor.getString(2), // UserName del cliente que trae la consulta
                    cursor.getString(3) // pASSWORD del cliente que trae la consulta
            );
            arrayClientes.add(cliente);
        }
        adaptadorListView = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayClientes);
        listViewClientes.setAdapter(adaptadorListView);
        cursor.close();
        database.close();

    }
}