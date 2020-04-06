package pkg2.projeto_tc;
// OS PARAMETROS DESSA CLASSE PRECISAM SER ALTERADOS DE ACORDO COM O NOVO PROJETO
// ESSA CLASSE GERA UM ARQUIVO XML PARA UM AUTOMATO FINITO DETERMINISTICO E NÃO UMA GRÁMATICA REGULAR
// SERÁ NECESSÁRIO MUDAR TODOS OS PARAMETROS DE GRAVAÇÃO DO ARQUIVO
// AQUI É SÓ UM BASE

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class GerarXML {

//    public boolean estadoFinal(int id, List<EstadoFinal> lista) {
//        for (int i = 0; i < lista.size(); i++) {
//            if (id == lista.get(i).getId()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean estadoInicial(int id, EstadoInicial lista) {
//        return id == lista.getId();
//    }

    public boolean gerar(Grammar Gramatica,boolean gravar){

        Grammar GramaticaRegular  = new Grammar();
        GramaticaRegular.setLeft(Gramatica.getLeft());
        GramaticaRegular.setRight(Gramatica.getRight());

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentXML = documentBuilder.newDocument();
            Element structure = documentXML.createElement("structure");

            documentXML.appendChild(structure);
            Element type = documentXML.createElement("type");
            type.appendChild(documentXML.createTextNode("grammar"));

            structure.appendChild(type);

            for (int i = 0; i < GramaticaRegular.getRight().size(); i++) {
                
                Element production = documentXML.createElement("production");

                 Element left = documentXML.createElement("left");
                left.appendChild(documentXML.createTextNode(GramaticaRegular.getLeft().get(i)));
                production.appendChild(left);   
                Element right = documentXML.createElement("right");
                right.appendChild(documentXML.createTextNode(GramaticaRegular.getRight().get(i)));
                production.appendChild(right); 
                
                type.appendChild(production);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            if (gravar) {

                DOMSource documentoFonte = new DOMSource(documentXML);
                //Caminha de onde será salvo o arquivo XML
                StreamResult documentoFinal = new StreamResult(new File("Gramatica "));
                transformer.transform(documentoFonte, documentoFinal);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GerarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(GerarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(GerarXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
}
