package com.chico.kiritoprojects

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var txtPreviewContent: TextView
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando as views
        editText = findViewById(R.id.editText)
        btnAdicionar = findViewById(R.id.btnAdicionar)
        txtPreviewContent = findViewById(R.id.txtPreviewContent)
        btnSalvar = findViewById(R.id.btnSalvar)

        // Definindo o listener para o botão Adicionar
        btnAdicionar.setOnClickListener {
            val inputText = editText.text.toString()

            // Verificando se o campo de entrada está vazio
            if (inputText.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, insira um texto.", Toast.LENGTH_SHORT).show()
            } else {
                // Adicionando o texto à pré-visualização do relatório
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                formatter.timeZone = TimeZone.getTimeZone("GMT-03:00") // Fuso horário de Brasília
                val currentTime = formatter.format(Date())
                txtPreviewContent.append("$currentTime - $inputText\n")
                editText.text.clear()
            }
        }

        // Definindo o listener para o botão Salvar
        btnSalvar.setOnClickListener {
            // Criando o arquivo PDF do relatório
            val fileName = "Relatorio_${SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(Date())}.pdf"
            val filePath = this.getExternalFilesDir(null)?.absolutePath + "/" + fileName
            val pdfFile = FileOutputStream(filePath)

            val document = Document()
            PdfWriter.getInstance(document, pdfFile)

            document.open()
            document.add(Paragraph("Relatório gerado em ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())}"))

            // Adicionando o conteúdo do relatório ao PDF
            document.add(Paragraph(txtPreviewContent.text.toString()))

            document.close()

            // Exibindo uma mensagem de sucesso e o caminho do arquivo salvo
            Toast.makeText(this, "Relatório salvo em $filePath", Toast.LENGTH_SHORT).show()
        }
    }
}
