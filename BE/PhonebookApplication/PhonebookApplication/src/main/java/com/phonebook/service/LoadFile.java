package com.phonebook.service;

import com.phonebook.dao.PhonebookRepository;
import com.phonebook.modal.Phonebook;
import com.phonebook.parser.CSVParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.phonebook.constant.PhonebookConstants.RESTORE;

@Repository
@ComponentScan(basePackages = {"com.phonebook.*"})
@PropertySource("classpath:application.properties")
public class LoadFile {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Environment env;

    @Autowired
    private PhonebookRepository phonebookRepository;

    public LoadFile() {
        // TODO Auto-generated constructor stub
    }

    @PostConstruct
    private void init() {
        boolean isRestore;

        try {
            isRestore = Boolean.parseBoolean(env.getProperty(RESTORE.getValue()));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Restore value : "+isRestore);

            if(!isRestore) {
                return;
            }

            // Delete all DB entries
            phonebookRepository.deleteAll();

            final Resource resource = resourceLoader.getResource("classpath:"+"assets/csv/Phonebook.csv");
            final List<Phonebook> phonebooks = CSVParser.readCSVFile(Phonebook.class, resource.getFile().getAbsolutePath());

            phonebookRepository.saveAll(phonebooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
