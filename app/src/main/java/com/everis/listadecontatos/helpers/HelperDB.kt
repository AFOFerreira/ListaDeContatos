package com.everis.listadecontatos.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
    context: Context

) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {
    companion object {
        private val NOME_BANCO = "ContatoDB"
        private val VERSAO_ATUAL = 2
    }

    val TABLE_NAME = "contato"
    val COLUNM_ID = "id"
    val COLUNM_NOME = "nome"
    val COLUNM_TELEFONE = "TELEFONE"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            " $COLUNM_ID INTEGER NOT NULL," +
            " $COLUNM_NOME TEXT NOT NULL," +
            " $COLUNM_TELEFONE TEXT NOT NULL," +
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

    fun buscarContatos(busca: String, isBuscaPorId: Boolean = false): List<ContatosVO> {
    //salvarContato(ContatosVO(0, "teste2", "1234"))
        val db = readableDatabase ?: return mutableListOf()
        var where: String? = null
        var args: Array<String> = arrayOf()
        if(isBuscaPorId){
            where = "$COLUNM_ID = ?"
           args = arrayOf("$busca")
        }
        else{
            where = "$COLUNM_NOME LIKE ?"
            args = arrayOf("%$busca%")
        }
        var lista = mutableListOf<ContatosVO>()

        var cursor = db.query(TABLE_NAME,null,where,args, null,null, null)
        if(cursor == null){
            db.close()
            return mutableListOf()
        }
        while (cursor.moveToNext()) {
            var contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUNM_ID)),
                cursor.getString(cursor.getColumnIndex(COLUNM_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUNM_TELEFONE))
            )
            lista.add(contato)
        }
        return lista
    }

    fun salvarContato(contato: ContatosVO){
        /*Primeira maneira
        val db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUNM_NOME, $COLUNM_TELEFONE) VALUES ('Abner', '1234')"
        db.execSQL(sql)
        db.close()*/

        //Segunda maneira
        val db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUNM_NOME, $COLUNM_TELEFONE) VALUES (?,?)"
        var array = arrayOf(contato.nome, contato.telefone)
        db.execSQL(sql, array)
        db.close()


        /*Terceira maneira
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUNM_NOME, contato.nome)
        content.put(COLUNM_TELEFONE, contato.telefone)
        db.insert(TABLE_NAME, null, content)
        db.close()
        */

    }

    fun deletarContato(id:Int){
        /*Primeira maneira
        val db = writableDatabase ?: return
        val where = "id = ?"
        var arg = arrayOf("$id")
        db.delete(TABLE_NAME,where, arg)
        db.close()
        * */

        //Segunda maneira
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUNM_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql, arg)
        db.close()

    }

    fun updateContato(contato : ContatosVO){
        val db = writableDatabase ?: return
        val content = ContentValues()
        content.put(COLUNM_NOME, contato.nome)
        content.put(COLUNM_TELEFONE, contato.telefone)
        val where = "id = ?"
        var args = arrayOf("${contato.id}")
        db.update(TABLE_NAME,content, where, args)
        db.close()
    }
}