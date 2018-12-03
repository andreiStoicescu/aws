package com.aws.jsp;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class OntologyRepository {

    private Logger LOG = LoggerFactory.getLogger(OntologyRepository.class);

    public OntModel ontologyModel;

    public void loadOntology(String ontologyPath){
        try {
            File file = new File(ontologyPath);
            FileReader fileReader = new FileReader(file);

            ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            ontologyModel.read(fileReader, null);

        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }
}
