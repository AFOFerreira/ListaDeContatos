package com.everis.listadecontatos.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(
    context: Context?

) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {
    companion object {
        private val NOME_BANCO = "ContatoDB"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val COLUNM_ID = "id"
    val COLUNM_NOME = "nome"
    val COLUNM_TELEFONE = "TELEFONE"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "$COLUNM_ID INTEGER NOT NULL" +
            "$COLUNM_NOME TEXT NOT NULL" +
            "$COLUNM_TELEFONE TEXT NOT NULL" +
            "PRIMARY KEY($COLUNM_ID AUTOINCREMENT)" +
            ")"


    //aqui cria
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    //aqui atualizamos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            //atualizar tabelas ou criar uma nova tabela
            db?.execSQL(DROP_TABLE)
            onCreate(db)
        }
        TODO("Not yet implemented")
    }
}