package com.aws.jsp;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class OntologyRestController {

    @Autowired
    private OntologyRepository ontologyRepository;

    private void loadOntology() {
        if(ontologyRepository.ontologyModel == null){
            ontologyRepository.loadOntology("ontology/disease.owl");
            ontologyRepository.ontologyModel.write(System.out, "RDF/JSON");
        }
    }

    @RequestMapping(value = "/classes",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getClasses() {
        loadOntology();
        List<String> list=new ArrayList();
        OntModel model = ontologyRepository.ontologyModel;
            Iterator ontologiesIter = model.listClasses();
            while (ontologiesIter.hasNext()) {
                OntClass ontClass = (OntClass) ontologiesIter.next();
                list.add(ontClass.getLabel(""));

            }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/rootClasses",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getRootClasses() {
        loadOntology();
        List<String> list=new ArrayList();
        OntModel model = ontologyRepository.ontologyModel;
        Iterator ontologiesIter = model.listHierarchyRootClasses();
        while (ontologiesIter.hasNext()) {
            OntClass ontClass = (OntClass) ontologiesIter.next();
            list.add(ontClass.getLabel(""));

        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/individuals",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getIndividuals() {
        loadOntology();
        List<String> list=new ArrayList();
        OntModel model = ontologyRepository.ontologyModel;
        Iterator ontologiesIter = model.listIndividuals();
        while (ontologiesIter.hasNext()) {
            Individual individual = (Individual) ontologiesIter.next();
            list.add(individual.getLabel(""));

        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/objectProperty",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getObjectProperties() {
        loadOntology();
        List<String> list=new ArrayList();
        OntModel model = ontologyRepository.ontologyModel;
        Iterator ontologiesIter = model.listObjectProperties();
        while (ontologiesIter.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) ontologiesIter.next();
            list.add(objectProperty.getLabel(""));

        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/hasSymtom",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDisease() {
        loadOntology();
        List<String> list=new ArrayList();
        OntModel model = ontologyRepository.ontologyModel;
        Property property = model.getOntProperty("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");
        Iterator resourceIter = model.listSubjectsWithProperty(property,"Actinomycotic madura foot (disorder)");
        while (resourceIter.hasNext()) {
            Resource resource = (Resource) resourceIter.next();
            StmtIterator stmts = resource.listProperties(property);
            while ( stmts.hasNext() ) {
                Statement stmt = stmts.next();
                list.add(stmt.getString());
            }

        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }
}
