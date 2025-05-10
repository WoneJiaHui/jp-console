package com.jeeplus.flowable.service.converter.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Primary
@Service("flowModelService")
public class FlowModelService extends ModelServiceImpl {


    protected FlowBpmnJsonConverter bpmnJsonConverter = new FlowBpmnJsonConverter ( );

    protected BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter ( );

    @Override
    public byte[] getBpmnXML(BpmnModel bpmnModel) {
        for (Process process : bpmnModel.getProcesses ( )) {
            if ( StringUtils.isNotEmpty ( process.getId ( ) ) ) {
                char firstCharacter = process.getId ( ).charAt ( 0 );
                // no digit is allowed as first character
                if ( Character.isDigit ( firstCharacter ) ) {
                    process.setId ( "a" + process.getId ( ) );
                }
            }
        }

        byte[] xmlBytes = bpmnXMLConverter.convertToXML ( bpmnModel );
        return xmlBytes;
    }


    public ModelRepresentation importNewVersion(String modelId, String fileName, InputStream modelStream) {
        Model processModel = getModel ( modelId );

        if ( fileName != null && (fileName.endsWith ( ".bpmn" ) || fileName.endsWith ( ".bpmn20.xml" )) ) {
            try {
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory ( );
                InputStreamReader xmlIn = new InputStreamReader ( modelStream, "UTF-8" );
                XMLStreamReader xtr = xif.createXMLStreamReader ( xmlIn );

                BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel ( xtr );
                if ( CollectionUtils.isEmpty ( bpmnModel.getProcesses ( ) ) ) {
                    throw new BadRequestException ( "No process found in definition " + fileName );
                }

                if ( bpmnModel.getLocationMap ( ).size ( ) == 0 ) {
                    throw new BadRequestException ( "No required BPMN DI information found in definition " + fileName );
                }

                ObjectNode modelNode = bpmnJsonConverter.convertToJson ( bpmnModel );

                AbstractModel savedModel = saveModel ( modelId, processModel.getName ( ), processModel.getKey ( ),
                        processModel.getDescription ( ), modelNode.toString ( ), true, "Version import via REST service", SecurityUtils.getCurrentUserId ( ) );
                return new ModelRepresentation ( savedModel );

            } catch (BadRequestException e) {
                throw e;

            } catch (Exception e) {
                throw new BadRequestException ( "Import failed for " + fileName + ", error message " + e.getMessage ( ) );
            }
        } else {
            throw new BadRequestException ( "Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName );
        }
    }


}

