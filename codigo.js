//nao usar acrobat reader pro
//usa a pdf-lib

const fs = require('fs');
const {
    PDFDocument,
    PDFName,
    PDFDict,
    PDFString
} = require('pdf-lib');

async function main() {

    // carrega pdf existente
    const existingPdfBytes = fs.readFileSync('doc_teste_codigo.pdf');

    const pdfDoc = await PDFDocument.load(existingPdfBytes);

    const context = pdfDoc.context;

    // cria ação javascript
    const jsAction = context.obj({
        Type: 'Action',
        S: 'JavaScript',
        JS: PDFString.of(
            'app.alert("PDF aberto com sucesso!");'
        )
    });

    // adiciona OpenAction ao 
    //é um campo interno da estrutura de qualquer pdf
    //define oq tem que acontecer quando o pdf abrir
    pdfDoc.catalog.set(
        PDFName.of('OpenAction'),
        jsAction
    );

    // salva novo pdf
    const pdfBytes = await pdfDoc.save();

    fs.writeFileSync('saida.pdf', pdfBytes);

    console.log('PDF gerado com JavaScript!');
}

main();